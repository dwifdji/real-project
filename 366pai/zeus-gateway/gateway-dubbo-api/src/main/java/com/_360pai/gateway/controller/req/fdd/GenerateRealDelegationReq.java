package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;

/**
 * 描述 物权预招商模板请求
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/31 14:30
 */
public class GenerateRealDelegationReq implements Serializable {

    private String activityCode;//合同编号
    private String seller;//委托方
    private String web;//平台
    private String activityName;//标的名称
    private String reference;//参考价
     private String beginTime;//开拍时间
    private String endTime;//开拍结束时间
    private String cost;//收费金额
    private String sellerAddress;//委托方住址
    private String sellerAgency;//代理人
    private String sellerPhone;//联系电话

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getSellerAgency() {
        return sellerAgency;
    }

    public void setSellerAgency(String sellerAgency) {
        this.sellerAgency = sellerAgency;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }
}
