
package com._360pai.core.model.enrolling;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月18日 16时06分20秒
 */
public class EnrollingSubmitOrder implements java.io.Serializable{

	/**
	 * 
	 */
	private java.lang.Long id;
	/**
	 * 活动转态
	 */
	private java.lang.Integer activityId;
	/**
	 * 登录用户id
	 */
	private java.lang.Integer partyId;
	/**
	 * 支付的金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 支付状态
	 */
	private java.lang.String status;
	/**
	 * 创建时间
	 */
	private java.util.Date createdAt;
	/**
	 * 修改时间
	 */
	private java.util.Date updateAt;
	/**
	 * 预招商类型 1 抵押物预招商 2债权预招商 3物权预招商
	 */
	private java.lang.Integer type;
	/**
	 * 支付的订单状态
	 */
	private java.lang.String orderNum;
	
	/**
	 * 
	 */
	public java.lang.Long getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(java.lang.Long id){
		this.id = id;
	}
	
	/**
	 * 活动转态
	 */
	public java.lang.Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 活动转态
	 */
	public void setActivityId(java.lang.Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 登录用户id
	 */
	public java.lang.Integer getPartyId(){
		return partyId;
	}
	
	/**
	 * 登录用户id
	 */
	public void setPartyId(java.lang.Integer partyId){
		this.partyId = partyId;
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
	 * 支付状态
	 */
	public java.lang.String getStatus(){
		return status;
	}
	
	/**
	 * 支付状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreatedAt(){
		return createdAt;
	}
	
	/**
	 * 创建时间
	 */
	public void setCreatedAt(java.util.Date createdAt){
		this.createdAt = createdAt;
	}
	
	/**
	 * 修改时间
	 */
	public java.util.Date getUpdateAt(){
		return updateAt;
	}
	
	/**
	 * 修改时间
	 */
	public void setUpdateAt(java.util.Date updateAt){
		this.updateAt = updateAt;
	}
	
	/**
	 * 预招商类型 1 抵押物预招商 2债权预招商 3物权预招商
	 */
	public java.lang.Integer getType(){
		return type;
	}
	
	/**
	 * 预招商类型 1 抵押物预招商 2债权预招商 3物权预招商
	 */
	public void setType(java.lang.Integer type){
		this.type = type;
	}
	
	/**
	 * 支付的订单状态
	 */
	public java.lang.String getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 支付的订单状态
	 */
	public void setOrderNum(java.lang.String orderNum){
		this.orderNum = orderNum;
	}

}
