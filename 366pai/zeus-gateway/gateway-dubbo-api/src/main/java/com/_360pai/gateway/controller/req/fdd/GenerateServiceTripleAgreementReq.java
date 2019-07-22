package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;

/**
 * 描述 三方协议 请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/06 14:30
 */
public class GenerateServiceTripleAgreementReq implements Serializable {

    private String seller;//甲方
    
    private String entrustSignTime;//委托拍卖签订的时间

    private String entrustCode;//委托合同编号

    private String dueSignTime;//合作协议签订时间

    private String dueCode;//协议合同编号

    private String activityName;//名称

    private String lawFirm;//律师事务所名称

    public String getLawFirm() {
        return lawFirm;
    }

    public void setLawFirm(String lawFirm) {
        this.lawFirm = lawFirm;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getEntrustSignTime() {
        return entrustSignTime;
    }

    public void setEntrustSignTime(String entrustSignTime) {
        this.entrustSignTime = entrustSignTime;
    }

    public String getEntrustCode() {
        return entrustCode;
    }

    public void setEntrustCode(String entrustCode) {
        this.entrustCode = entrustCode;
    }

    public String getDueSignTime() {
        return dueSignTime;
    }

    public void setDueSignTime(String dueSignTime) {
        this.dueSignTime = dueSignTime;
    }

    public String getDueCode() {
        return dueCode;
    }

    public void setDueCode(String dueCode) {
        this.dueCode = dueCode;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
