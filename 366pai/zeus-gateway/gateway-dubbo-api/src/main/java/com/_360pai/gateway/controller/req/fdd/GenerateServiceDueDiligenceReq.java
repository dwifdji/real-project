package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;

/**
 * 描述 尽职调查授权委托书 请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/06 14:30
 */
public class GenerateServiceDueDiligenceReq implements Serializable {

    private String seller;//甲方
    private String beginTime;//开始时间
    private String endTime;//结束时间
    private String activityName;//标的名称
    private String entrustBeginTime;//尽调开始时间
    private String entrustEndTime;//尽调结束时间
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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getEntrustBeginTime() {
        return entrustBeginTime;
    }

    public void setEntrustBeginTime(String entrustBeginTime) {
        this.entrustBeginTime = entrustBeginTime;
    }

    public String getEntrustEndTime() {
        return entrustEndTime;
    }

    public void setEntrustEndTime(String entrustEndTime) {
        this.entrustEndTime = entrustEndTime;
    }
}
