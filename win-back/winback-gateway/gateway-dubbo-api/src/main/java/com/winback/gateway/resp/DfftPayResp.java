package com.winback.gateway.resp;

import java.io.Serializable;

/**
 * 描述：东方付通支付返回相应
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:16
 */
public class DfftPayResp implements Serializable {

    private String  code;
    private String  desc;
    private String  payId;


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

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }
}
