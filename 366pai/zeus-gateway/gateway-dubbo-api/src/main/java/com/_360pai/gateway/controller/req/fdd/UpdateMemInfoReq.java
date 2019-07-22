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
public class UpdateMemInfoReq implements Serializable {


    private String customer_id;//法大大编号

    private String email;//邮件

    private String mobile;//手机号码

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
