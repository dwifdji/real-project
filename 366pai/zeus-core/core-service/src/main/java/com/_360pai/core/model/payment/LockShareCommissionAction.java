
package com._360pai.core.model.payment;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分28秒
 */
public class LockShareCommissionAction implements java.io.Serializable{

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private Boolean payByBuyer;
	/**
	 * 
	 */
	private java.math.BigDecimal amount;
	/**
	 * 
	 */
	private Integer to;
	
	/**
	 * 
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(String id){
		this.id = id;
	}
	
	/**
	 * 
	 */
	public Boolean getPayByBuyer(){
		return payByBuyer;
	}
	
	/**
	 * 
	 */
	public void setPayByBuyer(Boolean payByBuyer){
		this.payByBuyer = payByBuyer;
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
	public Integer getTo(){
		return to;
	}
	
	/**
	 * 
	 */
	public void setTo(Integer to){
		this.to = to;
	}

}
