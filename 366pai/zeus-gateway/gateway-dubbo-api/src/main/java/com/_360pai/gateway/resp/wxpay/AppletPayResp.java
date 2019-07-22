package com._360pai.gateway.resp.wxpay;

import java.io.Serializable;

/**
 * 描述：微信支付返回参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/28 10:44
 */
public class AppletPayResp implements Serializable {

    private String code;

    private String desc;

    private String prepayId;//预支付订单id

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

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public static AppletPayResp failure(String code , String desc){

        AppletPayResp resp = new AppletPayResp();

        resp.setCode(code);
        resp.setDesc(desc);
        return resp;
    }
}
