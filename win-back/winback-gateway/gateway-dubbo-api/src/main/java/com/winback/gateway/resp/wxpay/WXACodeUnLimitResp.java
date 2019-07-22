package com.winback.gateway.resp.wxpay;

import java.io.Serializable;

/**
 * 描述：微信获取小程序二维码返回
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/23 10:42
 */
public class WXACodeUnLimitResp implements Serializable {

    private String imgUrl;//二维码图片地址

    private String code;//返回码

    private String desc;//返回描述

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

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


    public static WXACodeUnLimitResp failure(String code ,String desc){

        WXACodeUnLimitResp resp = new WXACodeUnLimitResp();

        resp.setCode(code);
        resp.setDesc(desc);
        return resp;
    }
}
