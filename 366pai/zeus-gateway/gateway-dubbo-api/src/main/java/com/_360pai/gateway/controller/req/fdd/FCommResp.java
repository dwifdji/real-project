package com._360pai.gateway.controller.req.fdd;


import java.io.Serializable;

/**
 * 描述：法大大会员开户请求参数
 *
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 12:55
 */
public class FCommResp implements Serializable {
    private String code; //000 成功

    private String desc;//返回描述


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
