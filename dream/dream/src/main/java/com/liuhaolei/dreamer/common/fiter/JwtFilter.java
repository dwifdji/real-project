package com.liuhaolei.dreamer.common.fiter;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuhaolei.dreamer.common.exception.LoginException;
import com.liuhaolei.dreamer.common.res.ResponseModel;
import com.liuhaolei.dreamer.common.res.ResultStatus;
import com.liuhaolei.dreamer.conf.Audience;
import com.liuhaolei.dreamer.util.JwtTokenUtil;
import com.liuhaolei.dreamer.util.RedisUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * 
 * create by liuhaolei on 2018/11/8
 * jwt过滤器 对部分请求做过滤验证
 * 
 */
public class JwtFilter extends GenericFilterBean{
	
	@Autowired
	private Audience audience;
	@Autowired
	private RedisUtil redisUtil;
 
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		//系统安全性考虑该引用是不会改变的
		final HttpServletRequest request = (HttpServletRequest) req;
		
		final HttpServletResponse response = (HttpServletResponse) res;
		
		final String authHeader = request.getHeader("Authorization");
		
		if("OPTIONS".equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(req, res);
		}else {
		
			
			response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            ResponseModel responseModel = null;
            OutputStream out = null;
			//判断是否有该请求头，或者是以该请求头开头
			if (authHeader == null || !authHeader.startsWith("Bearer")) {
                throw new LoginException(ResultStatus.AUTH_ERROR.getMsg());
            }
			
			final String token = authHeader.substring(7);
			
			try {
				if (audience == null) {
					BeanFactory factory = WebApplicationContextUtils
							.getRequiredWebApplicationContext(request.getServletContext());
					audience = (Audience) factory.getBean("audience");
				}
				
				if (redisUtil == null) {
					BeanFactory factory = WebApplicationContextUtils
							.getRequiredWebApplicationContext(request.getServletContext());
					redisUtil = (RedisUtil) factory.getBean("redisUtil");
				}
				
				final Claims claims = JwtTokenUtil.parseJWT(token, audience.getBase64Secret());
				chain.doFilter(req, res);
			} catch (ExpiredJwtException e) {
				//token过期获取新的token并更新到redis中
				
				String userId = redisUtil.getStr(token);
				if(StringUtils.isNotBlank(userId)) {
					String newToken = redisUtil.refashToken(userId, token);
					responseModel = new ResponseModel("402", "刷新token", newToken, 
			    				System.currentTimeMillis());
				}else {
					responseModel = new ResponseModel("405", "token失效", null, 
		    				System.currentTimeMillis());
				}
				
				String userJson = convertObjectToJson(responseModel);
	            out = response.getOutputStream();
	            out.write(userJson.getBytes("UTF-8"));
	            out.flush(); 
	            
			} catch (SignatureException | MalformedJwtException e) {
				responseModel = new ResponseModel("401", "用户认证失败", null, 
	    				System.currentTimeMillis());
				
				String userJson = convertObjectToJson(responseModel);
	            out = response.getOutputStream();
	            out.write(userJson.getBytes("UTF-8"));
	            out.flush(); 
			}
		}
		
	}
	
	
	 public String convertObjectToJson(Object object) throws JsonProcessingException {
	        if (object == null) {
	            return null;
	        }
	        ObjectMapper mapper = new ObjectMapper();
	        return mapper.writeValueAsString(object);
	    }
 

}
