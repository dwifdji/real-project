package com._360pai.core.model.DfftPay;

/**
 * 描述：支付的通用请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:19
 */
public class PayCommonReq {

    private String payType;//支付的类型

    private String payID;//支付的号码

    private String originalPayID;//关联支付id 关闭支付时必填

    private String tradeOrder;//交易订单号

    private String payMemCode;//付款方会员号

    private String payMemName;//付款会员名称

    private String currency;//币种

    private String payAmt;//金额

    private String summary;//摘要

    private String notifyUrl;//异步回调地址

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayID() {
        return payID;
    }

    public void setPayID(String payID) {
        this.payID = payID;
    }

    public String getOriginalPayID() {
        return originalPayID;
    }

    public void setOriginalPayID(String originalPayID) {
        this.originalPayID = originalPayID;
    }

    public String getTradeOrder() {
        return tradeOrder;
    }

    public void setTradeOrder(String tradeOrder) {
        this.tradeOrder = tradeOrder;
    }

    public String getPayMemCode() {
        return payMemCode;
    }

    public void setPayMemCode(String payMemCode) {
        this.payMemCode = payMemCode;
    }

    public String getPayMemName() {
        return payMemName;
    }

    public void setPayMemName(String payMemName) {
        this.payMemName = payMemName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


}
