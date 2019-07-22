package com.winback.admin.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xdrodger
 * @Title: CustomCredentialsMatcher
 * @ProjectName example
 * @Description:
 * @date 2018/9/26 19:25
 */
public class StatelessCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        StatelessToken token = (StatelessToken) authcToken;
        String clientDigest = token.getClientDigest();
        String serverDigest = String.valueOf(info.getCredentials());
        return clientDigest.equals(serverDigest);
    }
}
