package com._360pai.admin.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

public class StatelessToken implements AuthenticationToken {
    private String username;
    private Map<String, ?> params;
    private String clientDigest;

   public  StatelessToken(String username, String clientDigest) {
       this.username = username;
       this.clientDigest = clientDigest;
   }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, ?> getParams() {
        return params;
    }

    public void setParams(Map<String, ?> params) {
        this.params = params;
    }

    public String getClientDigest() {
        return clientDigest;
    }

    public void setClientDigest(String clientDigest) {
        this.clientDigest = clientDigest;
    }

    //省略部分代码
    public Object getPrincipal() {  return username;}
    public Object getCredentials() {  return clientDigest;}
}