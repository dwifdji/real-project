package com.winback.gateway.resp.wxpay;

import java.io.Serializable;

/**
 * 描述：微信支付返回参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/28 10:44
 */
public class ScanPayResp implements Serializable {

    private String code;

    private String desc;

    private String code_url;//返回的扫码字符串 前端根据字符串生成二维码

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

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }

    public static ScanPayResp failure(String code ,String desc){

        ScanPayResp resp = new ScanPayResp();

        resp.setCode(code);
        resp.setDesc(desc);
        return resp;
    }
}
