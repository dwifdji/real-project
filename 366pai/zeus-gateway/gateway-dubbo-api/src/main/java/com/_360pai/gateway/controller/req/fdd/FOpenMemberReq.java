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
public class FOpenMemberReq implements Serializable {


    private String customer_name;//客户名称

    private String email;//邮件（非必填）

    /*0-身份证（默认值）；
    1-护照；
    2-军人身份证；
    6-社会保障卡；
    A-武装警察身份证件；
    B-港澳通行证；
    C-台湾居民来往大陆通行证；
    E-户口簿*/
    private String ident_type;//证件类型

    private String mobile;//用户电话号码

    private String id_card;//证件号码

    private String customer_type;//客户类型 1 个人 2 公司

    private String party_id;//平台会员号码

    public String getParty_id() {
        return party_id;
    }

    public void setParty_id(String party_id) {
        this.party_id = party_id;
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdent_type() {
        return ident_type;
    }

    public void setIdent_type(String ident_type) {
        this.ident_type = ident_type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }
}

