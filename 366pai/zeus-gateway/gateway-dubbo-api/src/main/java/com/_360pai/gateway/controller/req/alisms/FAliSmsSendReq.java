package com._360pai.gateway.controller.req.alisms;

import java.io.Serializable;

/**
 * 描述：阿里短信发送请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/15 18:34
 */
public class FAliSmsSendReq implements Serializable {

    private String phoneNumber;//请求的手机号码

    private String templateCode;//短信的模板编号

    private String templateParam;//短信模板的参数

    private String partyId;//非必填 登录用户id

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }
}
