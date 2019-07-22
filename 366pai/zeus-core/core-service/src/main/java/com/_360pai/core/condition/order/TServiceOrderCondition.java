
package com._360pai.core.condition.order;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 *
 * @author Generator
 * @date 2018年09月06日 18时43分20秒
 */
public class TServiceOrderCondition implements DaoCondition {

    /**
     * 主键id
     */
    private java.lang.Integer    id;
    /**
     * 支付订单号
     */
    private java.lang.String     orderNum;
    /**
     * 订单类型 10 资产大买办 20 配资乐 30 处置服务
     */
    private java.lang.Integer    orderType;
    /**
     * 用户id
     */
    private java.lang.Long       accountId;
    /**
     * 业务关键字
     */
    private java.lang.String     busId;
    /**
     * 支付类型
     */
    private java.lang.String     payType;
    /**
     * 支付的业务码
     */
    private java.lang.String     payBusCode;
    /**
     * 支付金额
     */
    private java.math.BigDecimal amount;
    /**
     * 支付状态
     */
    private java.lang.String     payStatus;
    /**
     * 支付提示
     */
    private java.lang.String     msg;
    /**
     * 请求时间
     */
    private java.util.Date       createTime;
    /**
     * 更新时间
     */
    private java.util.Date       updateTime;

    private String payUrl;

    private Integer partyId;

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    /**
     * 主键id
     */
    public java.lang.Integer getId() {
        return id;
    }

    /**
     * 主键id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * 支付订单号
     */
    public java.lang.String getOrderNum() {
        return orderNum;
    }

    /**
     * 支付订单号
     */
    public void setOrderNum(java.lang.String orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 订单类型 10 资产大买办 20 配资乐 30 处置服务
     */
    public java.lang.Integer getOrderType() {
        return orderType;
    }

    /**
     * 订单类型 10 资产大买办 20 配资乐 30 处置服务
     */
    public void setOrderType(java.lang.Integer orderType) {
        this.orderType = orderType;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 业务关键字
     */
    public java.lang.String getBusId() {
        return busId;
    }

    /**
     * 业务关键字
     */
    public void setBusId(java.lang.String busId) {
        this.busId = busId;
    }

    /**
     * 支付类型
     */
    public java.lang.String getPayType() {
        return payType;
    }

    /**
     * 支付类型
     */
    public void setPayType(java.lang.String payType) {
        this.payType = payType;
    }

    /**
     * 支付的业务码
     */
    public java.lang.String getPayBusCode() {
        return payBusCode;
    }

    /**
     * 支付的业务码
     */
    public void setPayBusCode(java.lang.String payBusCode) {
        this.payBusCode = payBusCode;
    }

    /**
     * 支付金额
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }

    /**
     * 支付金额
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 支付状态
     */
    public java.lang.String getPayStatus() {
        return payStatus;
    }

    /**
     * 支付状态
     */
    public void setPayStatus(java.lang.String payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 支付提示
     */
    public java.lang.String getMsg() {
        return msg;
    }

    /**
     * 支付提示
     */
    public void setMsg(java.lang.String msg) {
        this.msg = msg;
    }

    /**
     * 请求时间
     */
    public java.util.Date getCreateTime() {
        return createTime;
    }

    /**
     * 请求时间
     */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     */
    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

}