package com._360pai.web.controller.account.req;

import com._360pai.arch.common.RequestModel;

/**
 * Created by RuQ on 2018/8/17 9:32
 */
public class AccountRequest extends RequestModel {
    private String mobile;
    private String default_agency_id;
    private String agencyCode;
    private String smsCode;
    //验证码类型，0:注册,1:登录,2:忘记密码
    private String smsType;
    private String password;
    private String register_source;
    private String source;
    private String type;
    private String partyId;
    private Integer spvId;
    private String captcha;
    // 快速通道，注册完成后自动登录
    private boolean fastway;

    public Integer getSpvId() {
        return spvId;
    }

    public void setSpvId(Integer spvId) {
        this.spvId = spvId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDefault_agency_id() {
        return default_agency_id;
    }

    public void setDefault_agency_id(String default_agency_id) {
        this.default_agency_id = default_agency_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegister_source() {
        return register_source;
    }

    public void setRegister_source(String register_source) {
        this.register_source = register_source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public boolean isFastway() {
        return fastway;
    }

    public void setFastway(boolean fastway) {
        this.fastway = fastway;
    }
}
