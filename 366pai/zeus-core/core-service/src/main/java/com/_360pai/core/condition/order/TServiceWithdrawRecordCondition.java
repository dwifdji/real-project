
package com._360pai.core.condition.order;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 *
 * @author Generator
 * @date 2018年10月09日 18时17分51秒
 */
public class TServiceWithdrawRecordCondition implements DaoCondition {

    /**
     * 主键
     */
    private Integer              id;
    /**
     * 提现订单号
     */
    private String               withdrawNo;
    /**
     * 提现银行id
     */
    private Integer              bankId;
    /**
     * 银行卡号
     */
    private String               bankNo;
    /**
     * 用户id
     */
    private Integer              accountId;
    /**
     * 用户类型  "user","company"
     */
    private String               accountType;
    /**
     * 用户名称
     */
    private String               accountName;
    /**
     * 提现金额
     */
    private java.math.BigDecimal withdrawAmount;
    /**
     * 审核时间
     */
    private java.util.Date       verifyTime;
    /**
     * 审核状态 10:待转账 20：已拒绝 30：已提现
     */
    private String               verifyStatus;
    /**
     * 审核人id
     */
    private Integer              verifyOperator;
    /**
     * 审核原因
     */
    private String               verifyContent;
    /**
     * 额外信息
     */
    private String               additional;
    /**
     * 创建时间
     */
    private java.util.Date       createTime;
    /**
     * 修改时间
     */
    private java.util.Date       updateTime;
    /**
     * 删除标识 0:未删除 1：删除
     */
    private Boolean              delFlag;

    /**
     * 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 提现订单号
     */
    public String getWithdrawNo() {
        return withdrawNo;
    }

    /**
     * 提现订单号
     */
    public void setWithdrawNo(String withdrawNo) {
        this.withdrawNo = withdrawNo;
    }

    /**
     * 提现银行id
     */
    public Integer getBankId() {
        return bankId;
    }

    /**
     * 提现银行id
     */
    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    /**
     * 银行卡号
     */
    public String getBankNo() {
        return bankNo;
    }

    /**
     * 银行卡号
     */
    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 用户类型  "user","company"
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * 用户类型  "user","company"
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * 用户名称
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 用户名称
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 提现金额
     */
    public java.math.BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    /**
     * 提现金额
     */
    public void setWithdrawAmount(java.math.BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    /**
     * 审核时间
     */
    public java.util.Date getVerifyTime() {
        return verifyTime;
    }

    /**
     * 审核时间
     */
    public void setVerifyTime(java.util.Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    /**
     * 审核状态 10:待转账 20：已拒绝 30：已提现
     */
    public String getVerifyStatus() {
        return verifyStatus;
    }

    /**
     * 审核状态 10:待转账 20：已拒绝 30：已提现
     */
    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    /**
     * 审核人id
     */
    public Integer getVerifyOperator() {
        return verifyOperator;
    }

    /**
     * 审核人id
     */
    public void setVerifyOperator(Integer verifyOperator) {
        this.verifyOperator = verifyOperator;
    }

    /**
     * 审核原因
     */
    public String getVerifyContent() {
        return verifyContent;
    }

    /**
     * 审核原因
     */
    public void setVerifyContent(String verifyContent) {
        this.verifyContent = verifyContent;
    }

    /**
     * 额外信息
     */
    public String getAdditional() {
        return additional;
    }

    /**
     * 额外信息
     */
    public void setAdditional(String additional) {
        this.additional = additional;
    }

    /**
     * 创建时间
     */
    public java.util.Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改时间
     */
    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     */
    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 删除标识 0:未删除 1：删除
     */
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * 删除标识 0:未删除 1：删除
     */
    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

}