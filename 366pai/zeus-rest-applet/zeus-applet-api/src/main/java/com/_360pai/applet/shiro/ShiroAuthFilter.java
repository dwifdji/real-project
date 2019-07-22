package com._360pai.applet.shiro;

import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class ShiroAuthFilter extends AuthorizationFilter {

    @Autowired
    private ShiroAuthService shiroAuthService;
    @Autowired
    private NumberJumpShiroAuthService numberJumpShiroAuthService;
    @Autowired
    private SystemProperties systemProperties;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        if (request != null && request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String uri = httpServletRequest.getRequestURI();
            if (uri.indexOf("numberJump") != -1) {
                String id = numberJumpShiroAuthService.getIdFromCookie(httpServletRequest);
                String ticket = numberJumpShiroAuthService.getTicketFromCookie(httpServletRequest);
                return numberJumpShiroAuthService.isLogin(id, ticket);
            } else {
                String id = shiroAuthService.getIdFromCookie(httpServletRequest);
                String ticket = shiroAuthService.getTicketFromCookie(httpServletRequest);
                return shiroAuthService.isLogin(id, ticket);
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        WebUtils.toHttp(response).sendError(401);
        return false;
    }

}