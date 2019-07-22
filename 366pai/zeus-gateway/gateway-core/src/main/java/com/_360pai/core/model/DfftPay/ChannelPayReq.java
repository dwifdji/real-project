package com._360pai.core.model.DfftPay;

/**
 * 描述：渠道支付的请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:19
 */
public class ChannelPayReq extends PayCommonReq{


    private String customFiels;//自定义字段

    private String bankCode;//银行code

    private String accName;//开户名称

    private String accNO;//开户银行账号

    private String instCash;//加急付款  T+0 付款标识，0 表示T+0 付款，空或其他为T+1 付款

    private String reorder;//翻单标识  1 翻单 0 或不填标识不翻单

    private String orderName;// 翻单名称，需要申请开通权限

    private String bankAccount;//付款银行账号

    private String bankUse;//付款银行用途

    private String bankDigest;//付款银行摘要



    public String getCustomFiels() {
        return customFiels;
    }

    public void setCustomFiels(String customFiels) {
        this.customFiels = customFiels;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccNO() {
        return accNO;
    }

    public void setAccNO(String accNO) {
        this.accNO = accNO;
    }

    public String getInstCash() {
        return instCash;
    }

    public void setInstCash(String instCash) {
        this.instCash = instCash;
    }

    public String getReorder() {
        return reorder;
    }

    public void setReorder(String reorder) {
        this.reorder = reorder;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankUse() {
        return bankUse;
    }

    public void setBankUse(String bankUse) {
        this.bankUse = bankUse;
    }

    public String getBankDigest() {
        return bankDigest;
    }

    public void setBankDigest(String bankDigest) {
        this.bankDigest = bankDigest;
    }
}
