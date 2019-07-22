package com._360pai.partner.shiro;

import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class ShiroAuthFilter extends AuthorizationFilter {

    @Autowired
    private ShiroAuthService agencyShiroAuthenService;

    @Autowired
    private SystemProperties systemProperties;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        if (request != null && request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            //String servletPath = httpServletRequest.getServletPath();
            //List<String> whiteList = Arrays.asList(systemProperties.getAgencyWhiteList().split(","));
            //if (whiteList.contains(servletPath)) {
            //    return true;
            //}
            String id = agencyShiroAuthenService.getIdFromCookie(httpServletRequest);
            String type = agencyShiroAuthenService.getTypeFromCookie(httpServletRequest);
            String ticket = agencyShiroAuthenService.getTicketFromCookie(httpServletRequest);
            return agencyShiroAuthenService.isLogin(id, type, ticket);
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        WebUtils.toHttp(response).sendError(401);
        return false;
    }

}