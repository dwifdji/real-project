package com._360pai.core.facade.assistant.resp;


import java.io.Serializable;


/**
 * 描述:
 * @author :
 * @date : 2018/8/16 16:14
 */
public class TokenResp implements Serializable {
    private String  upToken;

    private String  domain;

    private long  expires;

    public String getUpToken() {
        return upToken;
    }

    public void setUpToken(String upToken) {
        this.upToken = upToken;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }
}
