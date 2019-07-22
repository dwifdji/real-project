package com._360pai.gateway.controller.req.dfftpay;

import java.io.Serializable;

/**
 * 描述：东方付通异步支付返回请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/28 19:40
 */
public class DfftPayCallBackReq implements Serializable {

    private String  payStatus;

    private String  payMessage;

    private String  payID;

    private String  signature;

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayMessage() {
        return payMessage;
    }

    public void setPayMessage(String payMessage) {
        this.payMessage = payMessage;
    }

    public String getPayID() {
        return payID;
    }

    public void setPayID(String payID) {
        this.payID = payID;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
