package com.winback.gateway.resp;

import java.io.Serializable;

/**
 * 描述：东方付通支付返回相应
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:16
 */
public class BindMemberResp implements Serializable {


    private String  code;
    private String  desc;//返回描述
    private String  url; //跳转的url
    private String  order;//跳转的请求参数


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
