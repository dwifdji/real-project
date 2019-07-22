package com.tzCloud.gateway.controller.req.pay;

import java.io.Serializable;

/**
 * 描述：扫码支付请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:19
 */
public class WxScanPayReq implements Serializable {


    private String body;//微信付款名目

    private String openId;//扫码支付时传

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
