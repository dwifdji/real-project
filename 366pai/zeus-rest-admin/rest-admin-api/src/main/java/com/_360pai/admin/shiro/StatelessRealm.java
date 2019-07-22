package com._360pai.admin.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class StatelessRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(StatelessRealm.class);
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ShiroAuthService adminShiroAuthenService;
    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String loginId = (String) principals.getPrimaryPrincipal();
        Set<String> permissionCodeList = permissionService.getPermissionCodeList(loginId);
        //logger.info("permission的值为:" + permission);
        logger.info("本用户权限为:" + permissionCodeList);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permissionCodeList);
        return authorizationInfo;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        StatelessToken statelessToken = (StatelessToken) token;
        String username = statelessToken.getUsername();
        String serverDigest = adminShiroAuthenService.getTicket(statelessToken.getUsername());
        return new SimpleAuthenticationInfo(
                username,
                serverDigest,
                getName());
    }
}