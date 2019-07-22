package com._360pai.gateway.controller.req.dfftpay;

import java.io.Serializable;

/**
 * 描述：锁定或者释放或者直接支付请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:19
 */
public class LockOrReleaseOrDirectReq implements Serializable {

    private String originalPayID;//保证金解锁时必填

    private String lockTag;// 1 正常保证金支付 2 银行类保证金支付 保证金支付时传

    private String payMemCode;//付款方会员号

    private String payMemName;//付款会员名称

    private String recMemCode;//收款方会员号 当为预招商保证金锁定/释放时 不用传

    private String recMemName;//收款方会员名称 当为预招商保证金锁定/释放时 不用传

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
