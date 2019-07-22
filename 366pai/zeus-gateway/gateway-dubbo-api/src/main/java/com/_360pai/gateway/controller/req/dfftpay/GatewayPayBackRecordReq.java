
package com._360pai.gateway.controller.req.dfftpay;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月25日 21时54分48秒
 */
public class GatewayPayBackRecordReq implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 支付订单号
	 */
	private String orderNum;
	/**
	 * 内部订单号
	 */
	private String tradeOrderId;
	/**
	 * 返回的支付金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 支付状态
	 */
	private String payStatus;
	/**
	 * 支付回调的参数
	 */
	private String reqParam;
	/**
	 * gateway处理转态
	 */
	private String gatewayStatus;
	/**
	 * 业务方处理状态
	 */
	private String coreStatus;
	/**
	 * 支付提示
	 */
	private String msg;
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
	public Integer getId(){
		return id;
	}

	/**
	 * 主键id
	 */
	public void setId(Integer id){
		this.id = id;
	}

	/**
	 * 支付订单号
	 */
	public String getOrderNum(){
		return orderNum;
	}

	/**
	 * 支付订单号
	 */
	public void setOrderNum(String orderNum){
		this.orderNum = orderNum;
	}

	/**
	 * 内部订单号
	 */
	public String getTradeOrderId(){
		return tradeOrderId;
	}

	/**
	 * 内部订单号
	 */
	public void setTradeOrderId(String tradeOrderId){
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
	public String getPayStatus(){
		return payStatus;
	}

	/**
	 * 支付状态
	 */
	public void setPayStatus(String payStatus){
		this.payStatus = payStatus;
	}

	/**
	 * 支付回调的参数
	 */
	public String getReqParam(){
		return reqParam;
	}

	/**
	 * 支付回调的参数
	 */
	public void setReqParam(String reqParam){
		this.reqParam = reqParam;
	}

	/**
	 * gateway处理转态
	 */
	public String getGatewayStatus(){
		return gatewayStatus;
	}

	/**
	 * gateway处理转态
	 */
	public void setGatewayStatus(String gatewayStatus){
		this.gatewayStatus = gatewayStatus;
	}

	/**
	 * 业务方处理状态
	 */
	public String getCoreStatus(){
		return coreStatus;
	}

	/**
	 * 业务方处理状态
	 */
	public void setCoreStatus(String coreStatus){
		this.coreStatus = coreStatus;
	}

	/**
	 * 支付提示
	 */
	public String getMsg(){
		return msg;
	}

	/**
	 * 支付提示
	 */
	public void setMsg(String msg){
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
