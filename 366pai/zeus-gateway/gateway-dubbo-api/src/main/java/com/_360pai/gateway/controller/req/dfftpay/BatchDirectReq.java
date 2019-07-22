package com._360pai.gateway.controller.req.dfftpay;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：批量支付请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:19
 */
public class BatchDirectReq implements Serializable {

    private String originalPayID;//保证金解锁时必填

    private String lockTag;// 锁定标识

    private BigDecimal amount;// 支付金额

    private String busId;//支付业务id

    private String payType;//支付类型

    private String payTo;//支付到平台还是支付到

    private String whoPay;//谁付款 PayEnums.WHO_PAY 枚举


    private String payMemCode;//付款方会员号

    private String payMemName;//付款会员名称

    private String recMemCode;//收款方会员号 当为预招商保证金锁定/释放时 不用传

    private String recMemName;//收款方会员名称 当为预招商保证金锁定/释放时 不用传


    public String getWhoPay() {
        return whoPay;
    }

    public void setWhoPay(String whoPay) {
        this.whoPay = whoPay;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
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

    public String getOriginalPayID() {
        return originalPayID;
    }

    public void setOriginalPayID(String originalPayID) {
        this.originalPayID = originalPayID;
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
}
