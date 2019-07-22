package com.winback.admin.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
public class ShiroAuthFilter extends AuthorizationFilter {

    @Autowired
    private ShiroAuthService shiroAuthService;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        //if (request != null && request instanceof HttpServletRequest) {
        //    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //    String id = shiroAuthService.getIdFromCookie(httpServletRequest);
        //    String ticket = shiroAuthService.getTicketFromCookie(httpServletRequest);
        //    return shiroAuthService.isLogin(id, ticket);
        //}
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String id = shiroAuthService.getIdFromCookie(httpServletRequest);
        String ticket = shiroAuthService.getTicketFromCookie(httpServletRequest);
        StatelessToken token = new StatelessToken(id, ticket);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (Exception e) {
            log.warn("登陆失败，id={},,ticket={}", id, ticket, e.getMessage());
            WebUtils.toHttp(response).sendError(401);
            return false;
        }
        return true;
    }

}