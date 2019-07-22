package com._360pai.admin.controller.account.resp;

/**
 * @author xdrodger
 * @Title: LoginInfo
 * @ProjectName zeus
 * @Description:
 * @date 17/09/2018 12:01
 */
public class LoginInfo {
    private Integer id;
    private String name;
    private String mobile;
    private String qq;
    private Boolean isAdmin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        isAdmin = isAdmin;
    }
}
