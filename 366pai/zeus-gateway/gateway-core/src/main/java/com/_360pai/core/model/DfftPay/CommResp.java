package com._360pai.core.model.DfftPay;

/**
 * 描述：支付返回
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:20
 */
public class CommResp {

    private String code;//系统返回码

    private String desc;//系统返回参数

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
}
