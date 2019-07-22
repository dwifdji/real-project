package com._360pai.core.model.DfftPay;

/**
 * 描述：直接或者锁定支付请求类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:14
 */
public class DirectOrLockPayReq extends PayCommonReq{

    private String busId;//业务号码


    private String recMemCode;//收款会员号

    private String recMemName;//收款会员名称

    private String customFiels;//自定义字段

    private String locktag;//锁定字段 1 锁定到收款方

    private String jumpPay;//是否是页面跳转支付 PayEnums.JUMP_PAY_TYPE 主要针对直接支付类型

    private String bankAccount;//银行账号

    private String bankUse;//银行用途

    private String bankDigest;//银行摘要

    private String payMemType;//会员类型 1 企业支付 2 个人支付

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getJumpPay() {
        return jumpPay;
    }

    public void setJumpPay(String jumpPay) {
        this.jumpPay = jumpPay;
    }

    public String getRecMemCode() {
        return recMemCode;
    }

    public void setRecMemCode(String recMemCode) {
        this.recMemCode = recMemCode;
    }

    public String getRecMemName() {
        return recMemName;
    }

    public void setRecMemName(String recMemName) {
        this.recMemName = recMemName;
    }



    public String getCustomFiels() {
        return customFiels;
    }

    public void setCustomFiels(String customFiels) {
        this.customFiels = customFiels;
    }

    public String getLocktag() {
        return locktag;
    }

    public void setLocktag(String locktag) {
        this.locktag = locktag;
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

    public String getPayMemType() {
        return payMemType;
    }

    public void setPayMemType(String payMemType) {
        this.payMemType = payMemType;
    }
}
