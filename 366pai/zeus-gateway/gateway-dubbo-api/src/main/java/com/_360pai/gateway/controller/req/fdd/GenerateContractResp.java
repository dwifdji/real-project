package com._360pai.gateway.controller.req.fdd;

import com._360pai.arch.common.enums.ApiCallResult;

import java.io.Serializable;

/**
 * 描述：合同生成返回参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/31 14:51
 */
public class GenerateContractResp implements Serializable {

    private String code;

    private String desc;

    private String signType;//签章的类型

    private String activityId;//活动的id

    private String partyId;//活动的id

    private String contractId;//生成的合同id

    private String downloadUrl;//合同下载地址

    private String viewPdfUrl;//合同查看地址

    private String fddId;//签章的法大大Id

    private String transactionId ;//签章的交易号

    private String signRole ;//签章的角色

    public String getSignRole() {
        return signRole;
    }

    public void setSignRole(String signRole) {
        this.signRole = signRole;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFddId() {
        return fddId;
    }

    public void setFddId(String fddId) {
        this.fddId = fddId;
    }

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

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getViewPdfUrl() {
        return viewPdfUrl;
    }

    public void setViewPdfUrl(String viewPdfUrl) {
        this.viewPdfUrl = viewPdfUrl;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }


    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }


    public static GenerateContractResp fail(String msg){

        GenerateContractResp resp = new GenerateContractResp();
        resp.setCode(ApiCallResult.FAILURE.getCode());
        resp.setDesc(msg);

        return resp;

    }
}
