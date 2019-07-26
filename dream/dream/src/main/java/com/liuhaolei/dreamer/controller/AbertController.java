package com.liuhaolei.dreamer.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.liuhaolei.dreamer.conf.Audience;
import com.liuhaolei.dreamer.model.User;
import com.liuhaolei.dreamer.util.JwtTokenUtil;

import io.jsonwebtoken.Claims;

@Controller
public class AbertController {
	
	@Autowired
	private HttpServletRequest curRequest;
	@Autowired
	private Audience audience;
	
	
	protected final User getUserInfo() {
		
		final String authHeader = curRequest.getHeader("authorization");
		String token = authHeader.substring(7);
		
		final Claims claims = JwtTokenUtil.parseJWT(token, audience.getBase64Secret());
		User user = new User();
		user.setId(Integer.parseInt(claims.get("userid").toString()));
		user.setUserName(claims.get("unique_name").toString());
	
		return user;
	}
	

}
