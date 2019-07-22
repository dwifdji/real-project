package com.tzCloud.arch.core.filter;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HeadersCORSFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(HeadersCORSFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Content-Type", "application/json;text/html;charset=UTF-8");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if ("OPTIONS".equals(httpRequest.getMethod())) {
            response.setStatus(204);
        }
        HttpServletRequest    httpServletRequest = (HttpServletRequest) request;

        logger.info("CrosXssFilter.......orignal url:{},ParameterMap:{}", httpServletRequest.getRequestURI(), JSONObject.toJSONString(httpServletRequest.getParameterMap()));

        HttpServletRequest requestWrapper = null;
        if (httpServletRequest.getMethod().equals(HttpMethod.POST.name())) {
            requestWrapper = new PostParameterRequestWrapper(httpServletRequest);
        } else if (httpServletRequest.getMethod().equals(HttpMethod.GET.name())) {
            requestWrapper = new GetParameterRequestWrapper((HttpServletRequest) request);
        }
        chain.doFilter(requestWrapper, servletResponse);
//        logger.info("CrosXssFilter..........doFilter url:{},ParameterMap:{}", requestWrapper.getRequestURI(), JSONObject.toJSONString(requestWrapper.getParameterMap()));
    }

    @Override
    public void destroy() {

    }
}
