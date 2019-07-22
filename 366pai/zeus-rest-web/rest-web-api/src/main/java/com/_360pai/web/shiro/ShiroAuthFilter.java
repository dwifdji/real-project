package com._360pai.web.shiro;

import com._360pai.arch.common.constant.SystemConstant;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ShiroAuthFilter extends AuthorizationFilter {

    @Autowired
    private ShiroAuthenService shiroAuthenService;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {

        if (request != null && request instanceof HttpServletRequest) {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String id = shiroAuthenService.getIdFromCookie(httpServletRequest);
            String type = shiroAuthenService.getTypeFromCookie(httpServletRequest);
            String ticket = shiroAuthenService.getTicketFromCookie(httpServletRequest);
            return shiroAuthenService.isLogin(id, type, ticket);
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        WebUtils.toHttp(response).sendError(401);
        return false;
    }

}