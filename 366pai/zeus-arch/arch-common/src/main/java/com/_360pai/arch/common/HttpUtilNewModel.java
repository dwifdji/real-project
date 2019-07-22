package com._360pai.arch.common;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

import java.util.List;

public class HttpUtilNewModel {


    private int statusCode;

    private String location;

    private byte[] bytes;

    private String html;

    private List<Cookie> cookies;

    private Header[] headers;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public String getCookieStringFromSetCookie()
    {
        String ret = "";

        for(Header h: this.getHeaders()) {
            if(h.getName().equalsIgnoreCase("Set-Cookie")) {

                ret += h.getValue()+";";
            }
        }

        return ret;
    }

}