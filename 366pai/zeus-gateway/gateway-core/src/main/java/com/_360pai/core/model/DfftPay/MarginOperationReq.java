package com._360pai.core.model.DfftPay;

/**
 * 描述：保证金操作请求接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 16:17
 */
public class MarginOperationReq extends PayCommonReq{

    private String recMemCode;//相关会员代码 保证金将用于转货款支付时，保证金锁定时 ，必填收款会员代码

    private String recMemName;//会员名称

    private String auditFlag;//是否审核 1 保证金释放，需要审核

    private String lockTag;//锁定状态

    public String getLockTag() {
        return lockTag;
    }

    public void setLockTag(String lockTag) {
        this.lockTag = lockTag;
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

    public String getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(String auditFlag) {
        this.auditFlag = auditFlag;
    }
}
