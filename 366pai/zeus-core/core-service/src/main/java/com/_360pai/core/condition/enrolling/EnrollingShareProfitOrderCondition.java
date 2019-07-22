
package com._360pai.core.condition.enrolling;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年10月16日 13时57分17秒
 */
public class EnrollingShareProfitOrderCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Long id;
	/**
	 * 
	 */
	private java.lang.Integer activityId;
	/**
	 * 
	 */
	private java.math.BigDecimal amount;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private java.lang.String status;
	/**
	 * 
	 */
	private java.lang.Integer agencyId;
	/**
	 * 佣金订单id
	 */
	private java.lang.String commissionId;
	/**
	 * 
	 */
	private java.lang.String agencyName;
	/**
	 * 
	 */
	private java.lang.String agencyMemCode;
	/**
	 * 支付订单号
	 */
	private java.lang.String orderNum;
	/**
	 * 
	 */
	private java.util.Date updateTime;
	
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
	 * 
	 */
	public java.lang.Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 
	 */
	public void setActivityId(java.lang.Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getAmount(){
		return amount;
	}
	
	/**
	 * 
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreatedAt(){
		return createdAt;
	}
	
	/**
	 * 
	 */
	public void setCreatedAt(java.util.Date createdAt){
		this.createdAt = createdAt;
	}
	
	/**
	 * 
	 */
	public java.lang.String getStatus(){
		return status;
	}
	
	/**
	 * 
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getAgencyId(){
		return agencyId;
	}
	
	/**
	 * 
	 */
	public void setAgencyId(java.lang.Integer agencyId){
		this.agencyId = agencyId;
	}
	
	/**
	 * 佣金订单id
	 */
	public java.lang.String getCommissionId(){
		return commissionId;
	}
	
	/**
	 * 佣金订单id
	 */
	public void setCommissionId(java.lang.String commissionId){
		this.commissionId = commissionId;
	}
	
	/**
	 * 
	 */
	public java.lang.String getAgencyName(){
		return agencyName;
	}
	
	/**
	 * 
	 */
	public void setAgencyName(java.lang.String agencyName){
		this.agencyName = agencyName;
	}
	
	/**
	 * 
	 */
	public java.lang.String getAgencyMemCode(){
		return agencyMemCode;
	}
	
	/**
	 * 
	 */
	public void setAgencyMemCode(java.lang.String agencyMemCode){
		this.agencyMemCode = agencyMemCode;
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
	 * 
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}