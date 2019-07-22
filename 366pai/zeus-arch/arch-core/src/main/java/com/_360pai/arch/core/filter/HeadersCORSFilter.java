package com._360pai.arch.core.filter;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(filterName="headersCORSFilter",urlPatterns="/*")
public class HeadersCORSFilter implements Filter {

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
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Content-Type", "application/json;text/html;charset=UTF-8");
        HttpServletRequest httpRequest= (HttpServletRequest) request;
        if("OPTIONS".equals(httpRequest.getMethod())){
            response.setStatus(204);
        }
        chain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
