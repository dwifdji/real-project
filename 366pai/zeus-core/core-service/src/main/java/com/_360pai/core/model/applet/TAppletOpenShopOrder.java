
package com._360pai.core.model.applet;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月28日 13时30分57秒
 */
public class TAppletOpenShopOrder implements java.io.Serializable{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 支付订单id
	 */
	private java.lang.String orderId;
	/**
	 * 微信预付款id
	 */
	private java.lang.String prepayId;
	/**
	 * 支付的金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 支付的状态0 初始状态  1 支付成功 2 支付失败
	 */
	private java.lang.Integer payStatus;
	/**
	 * 
	 */
	private java.lang.Integer partyId;
	/**
	 * 微信openid
	 */
	private java.lang.String openId;
	/**
	 * 订单类型
	 */
	private java.lang.Integer type;
	/**
	 * 是否删除（0 否 1 是）
	 */
	private java.lang.Boolean isDelete;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	
	/**
	 * 
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 支付订单id
	 */
	public java.lang.String getOrderId(){
		return orderId;
	}
	
	/**
	 * 支付订单id
	 */
	public void setOrderId(java.lang.String orderId){
		this.orderId = orderId;
	}
	
	/**
	 * 微信预付款id
	 */
	public java.lang.String getPrepayId(){
		return prepayId;
	}
	
	/**
	 * 微信预付款id
	 */
	public void setPrepayId(java.lang.String prepayId){
		this.prepayId = prepayId;
	}
	
	/**
	 * 支付的金额
	 */
	public java.math.BigDecimal getAmount(){
		return amount;
	}
	
	/**
	 * 支付的金额
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}
	
	/**
	 * 支付的状态0 初始状态  1 支付成功 2 支付失败
	 */
	public java.lang.Integer getPayStatus(){
		return payStatus;
	}
	
	/**
	 * 支付的状态0 初始状态  1 支付成功 2 支付失败
	 */
	public void setPayStatus(java.lang.Integer payStatus){
		this.payStatus = payStatus;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getPartyId(){
		return partyId;
	}
	
	/**
	 * 
	 */
	public void setPartyId(java.lang.Integer partyId){
		this.partyId = partyId;
	}
	
	/**
	 * 微信openid
	 */
	public java.lang.String getOpenId(){
		return openId;
	}
	
	/**
	 * 微信openid
	 */
	public void setOpenId(java.lang.String openId){
		this.openId = openId;
	}
	
	/**
	 * 订单类型
	 */
	public java.lang.Integer getType(){
		return type;
	}
	
	/**
	 * 订单类型
	 */
	public void setType(java.lang.Integer type){
		this.type = type;
	}
	
	/**
	 * 是否删除（0 否 1 是）
	 */
	public java.lang.Boolean getIsDelete(){
		return isDelete;
	}
	
	/**
	 * 是否删除（0 否 1 是）
	 */
	public void setIsDelete(java.lang.Boolean isDelete){
		this.isDelete = isDelete;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 创建时间
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
