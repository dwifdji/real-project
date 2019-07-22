package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/9/1 21:15
 */
public class FddSignInfo implements Serializable {

    private String fdd_id;//签章的法大大id

    private String is_auto;//是否自动签章标志 1 自动签章参数 2 手动签章参数

    private String sign_role;//签章的角色 1 委托人 2 竞买人 3 送拍机构 4 连拍机构 5 平台

    private String party_id;//平台的用户id

    private String mem_role;//1 个人用户 2 企业用户

    public String getMem_role() {
        return mem_role;
    }

    public void setMem_role(String mem_role) {
        this.mem_role = mem_role;
    }

    public String getParty_id() {
        return party_id;
    }

    public void setParty_id(String party_id) {
        this.party_id = party_id;
    }

    public String getFdd_id() {
        return fdd_id;
    }

    public void setFdd_id(String fdd_id) {
        this.fdd_id = fdd_id;
    }

    public String getIs_auto() {
        return is_auto;
    }

    public void setIs_auto(String is_auto) {
        this.is_auto = is_auto;
    }

    public String getSign_role() {
        return sign_role;
    }

    public void setSign_role(String sign_role) {
        this.sign_role = sign_role;
    }
}
