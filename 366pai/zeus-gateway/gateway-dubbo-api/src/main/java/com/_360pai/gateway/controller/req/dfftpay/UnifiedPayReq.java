package com._360pai.gateway.controller.req.dfftpay;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：统一支付请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/9/3 18:32
 */
public class UnifiedPayReq implements Serializable {

    private Integer partyId;//登录用户的id

    private BigDecimal amount;//支付的金额 单位元

    private String payType; //支付方式 PayEnums.PAY_TYPE  枚举

    private String payBusCode;//支付业务码 PayEnums.PAY_BUS_CODE 枚举

    private String busId;//平台的业务id

    private String payTo;//付款到用户还是付款到平台 PayEnums.PAY_TO 枚举

    private String whoPay;//谁付款 PayEnums.WHO_PAY 枚举

    private String jumpPay;//是否是页面跳转支付 PayEnums.JUMP_PAY_TYPE 主要针对直接支付类型

    private String lockTag;//锁定标志 用于是否直接支付 或者支付锁定 PayEnums.LOCK_TAG 枚举

    private String backTag;//锁定金额退回标志  PayEnums.BACK_TAG 枚举

    private Object payParam;//支付的其他信息 根据不同的payType 和 payBusCode传不同的值

    private String payMemType;//付款账号会员类型

    public String getBackTag() {
        return backTag;
    }

    public void setBackTag(String backTag) {
        this.backTag = backTag;
    }

    public String getWhoPay() {
        return whoPay;
    }

    public void setWhoPay(String whoPay) {
        this.whoPay = whoPay;
    }

    public String getPayMemType() {
        return payMemType;
    }

    public void setPayMemType(String payMemType) {
        this.payMemType = payMemType;
    }

    public String getJumpPay() {
        return jumpPay;
    }

    public void setJumpPay(String jumpPay) {
        this.jumpPay = jumpPay;
    }

    public String getPayTo() {
        return payTo;
    }

    public void setPayTo(String payTo) {
        this.payTo = payTo;
    }

    public String getLockTag() {
        return lockTag;
    }

    public void setLockTag(String lockTag) {
        this.lockTag = lockTag;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayBusCode() {
        return payBusCode;
    }

    public void setPayBusCode(String payBusCode) {
        this.payBusCode = payBusCode;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public Object getPayParam() {
        return payParam;
    }

    public void setPayParam(Object payParam) {
        this.payParam = payParam;
    }
}
