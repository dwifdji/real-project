
package com._360pai.core.condition.pay;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月15日 18时40分38秒
 */
public class GatewayPayOrderCondition implements DaoCondition{

	/**
	 * 主键id
	 */
	private java.lang.Integer id;
	/**
	 * 支付订单号
	 */
	private java.lang.String orderNum;
	/**
	 * 原始支付订单号
	 */
	private java.lang.String originalOrderNum;
	/**
	 * 内部订单号
	 */
	private java.lang.String tradeOrderId;
	/**
	 * 平台用户id
	 */
	private java.lang.Long partyId;
	/**
	 * 业务关键字
	 */
	private java.lang.String busId;
	/**
	 * 支付类型
	 */
	private java.lang.String payType;
	/**
	 * 支付的业务码
	 */
	private java.lang.String payBusCode;
	/**
	 * 付款方会员号
	 */
	private java.lang.String payMemCode;
	/**
	 * 付款方会员名称
	 */
	private java.lang.String payMemName;
	/**
	 * 收款方会员号
	 */
	private java.lang.String recMemCode;
	/**
	 * 是否是跳转支付 1 页面跳转支付 2 直接支付
	 */
	private java.lang.String jumpPay;
	/**
	 * 锁定标识
	 */
	private java.lang.String lockTag;
	/**
	 * 收款方会员名称
	 */
	private java.lang.String recMemName;
	/**
	 * 支付金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 支付状态
	 */
	private java.lang.String payStatus;
	/**
	 * 支付提示
	 */
	private java.lang.String msg;
	/**
	 * 请求时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	
	/**
	 * 主键id
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 主键id
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 支付订单号
	 */
	public java.lang.String getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 支付订单号
	 */
	public void setOrderNum(java.lang.String orderNum){
		this.orderNum = orderNum;
	}
	
	/**
	 * 原始支付订单号
	 */
	public java.lang.String getOriginalOrderNum(){
		return originalOrderNum;
	}
	
	/**
	 * 原始支付订单号
	 */
	public void setOriginalOrderNum(java.lang.String originalOrderNum){
		this.originalOrderNum = originalOrderNum;
	}
	
	/**
	 * 内部订单号
	 */
	public java.lang.String getTradeOrderId(){
		return tradeOrderId;
	}
	
	/**
	 * 内部订单号
	 */
	public void setTradeOrderId(java.lang.String tradeOrderId){
		this.tradeOrderId = tradeOrderId;
	}
	
	/**
	 * 平台用户id
	 */
	public java.lang.Long getPartyId(){
		return partyId;
	}
	
	/**
	 * 平台用户id
	 */
	public void setPartyId(java.lang.Long partyId){
		this.partyId = partyId;
	}
	
	/**
	 * 业务关键字
	 */
	public java.lang.String getBusId(){
		return busId;
	}
	
	/**
	 * 业务关键字
	 */
	public void setBusId(java.lang.String busId){
		this.busId = busId;
	}
	
	/**
	 * 支付类型
	 */
	public java.lang.String getPayType(){
		return payType;
	}
	
	/**
	 * 支付类型
	 */
	public void setPayType(java.lang.String payType){
		this.payType = payType;
	}
	
	/**
	 * 支付的业务码
	 */
	public java.lang.String getPayBusCode(){
		return payBusCode;
	}
	
	/**
	 * 支付的业务码
	 */
	public void setPayBusCode(java.lang.String payBusCode){
		this.payBusCode = payBusCode;
	}
	
	/**
	 * 付款方会员号
	 */
	public java.lang.String getPayMemCode(){
		return payMemCode;
	}
	
	/**
	 * 付款方会员号
	 */
	public void setPayMemCode(java.lang.String payMemCode){
		this.payMemCode = payMemCode;
	}
	
	/**
	 * 付款方会员名称
	 */
	public java.lang.String getPayMemName(){
		return payMemName;
	}
	
	/**
	 * 付款方会员名称
	 */
	public void setPayMemName(java.lang.String payMemName){
		this.payMemName = payMemName;
	}
	
	/**
	 * 收款方会员号
	 */
	public java.lang.String getRecMemCode(){
		return recMemCode;
	}
	
	/**
	 * 收款方会员号
	 */
	public void setRecMemCode(java.lang.String recMemCode){
		this.recMemCode = recMemCode;
	}
	
	/**
	 * 是否是跳转支付 1 页面跳转支付 2 直接支付
	 */
	public java.lang.String getJumpPay(){
		return jumpPay;
	}
	
	/**
	 * 是否是跳转支付 1 页面跳转支付 2 直接支付
	 */
	public void setJumpPay(java.lang.String jumpPay){
		this.jumpPay = jumpPay;
	}
	
	/**
	 * 锁定标识
	 */
	public java.lang.String getLockTag(){
		return lockTag;
	}
	
	/**
	 * 锁定标识
	 */
	public void setLockTag(java.lang.String lockTag){
		this.lockTag = lockTag;
	}
	
	/**
	 * 收款方会员名称
	 */
	public java.lang.String getRecMemName(){
		return recMemName;
	}
	
	/**
	 * 收款方会员名称
	 */
	public void setRecMemName(java.lang.String recMemName){
		this.recMemName = recMemName;
	}
	
	/**
	 * 支付金额
	 */
	public java.math.BigDecimal getAmount(){
		return amount;
	}
	
	/**
	 * 支付金额
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}
	
	/**
	 * 支付状态
	 */
	public java.lang.String getPayStatus(){
		return payStatus;
	}
	
	/**
	 * 支付状态
	 */
	public void setPayStatus(java.lang.String payStatus){
		this.payStatus = payStatus;
	}
	
	/**
	 * 支付提示
	 */
	public java.lang.String getMsg(){
		return msg;
	}
	
	/**
	 * 支付提示
	 */
	public void setMsg(java.lang.String msg){
		this.msg = msg;
	}
	
	/**
	 * 请求时间
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 请求时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	
	/**
	 * 更新时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 更新时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}