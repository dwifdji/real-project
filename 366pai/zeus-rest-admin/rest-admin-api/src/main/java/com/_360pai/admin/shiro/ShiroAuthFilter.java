package com._360pai.admin.shiro;

import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class ShiroAuthFilter extends AccessControlFilter {
    private static final Logger log = LoggerFactory.getLogger(ShiroAuthFilter.class);
    @Autowired
    private ShiroAuthService shiroAuthService;

    @Autowired
    private SystemProperties systemProperties;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        //if (request != null && request instanceof HttpServletRequest) {
        //    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //    String servletPath = httpServletRequest.getServletPath();
        //    List<String> whiteList = Arrays.asList(systemProperties.getAdminWhiteList().split(","));
        //    if (whiteList.contains(servletPath)) {
        //        return true;
        //    }
        //}
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String id = shiroAuthService.getIdFromCookie(httpServletRequest);
        String type = shiroAuthService.getTypeFromCookie(httpServletRequest);
        String ticket = shiroAuthService.getTicketFromCookie(httpServletRequest);
        StatelessToken token = new StatelessToken(type + "_" + id, ticket);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (Exception e) {
            log.warn("登陆失败，id={},type={},ticket={}", id, type, ticket, e.getMessage());
            WebUtils.toHttp(response).sendError(401);
            return false;
        }
        return true;
    }

}