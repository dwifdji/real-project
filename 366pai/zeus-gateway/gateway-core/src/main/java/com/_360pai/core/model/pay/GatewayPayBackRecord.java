
package com._360pai.core.model.pay;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月25日 21时54分48秒
 */
public class GatewayPayBackRecord implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private java.lang.Integer id;
	/**
	 * 支付订单号
	 */
	private java.lang.String orderNum;
	/**
	 * 内部订单号
	 */
	private java.lang.String tradeOrderId;
	/**
	 * 返回的支付金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 支付状态
	 */
	private java.lang.String payStatus;
	/**
	 * 支付回调的参数
	 */
	private java.lang.String reqParam;
	/**
	 * gateway处理转态
	 */
	private java.lang.String gatewayStatus;
	/**
	 * 业务方处理状态
	 */
	private java.lang.String coreStatus;
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
	 * 返回的支付金额
	 */
	public java.math.BigDecimal getAmount(){
		return amount;
	}
	
	/**
	 * 返回的支付金额
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
	 * 支付回调的参数
	 */
	public java.lang.String getReqParam(){
		return reqParam;
	}
	
	/**
	 * 支付回调的参数
	 */
	public void setReqParam(java.lang.String reqParam){
		this.reqParam = reqParam;
	}
	
	/**
	 * gateway处理转态
	 */
	public java.lang.String getGatewayStatus(){
		return gatewayStatus;
	}
	
	/**
	 * gateway处理转态
	 */
	public void setGatewayStatus(java.lang.String gatewayStatus){
		this.gatewayStatus = gatewayStatus;
	}
	
	/**
	 * 业务方处理状态
	 */
	public java.lang.String getCoreStatus(){
		return coreStatus;
	}
	
	/**
	 * 业务方处理状态
	 */
	public void setCoreStatus(java.lang.String coreStatus){
		this.coreStatus = coreStatus;
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
