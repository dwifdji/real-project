
package com._360pai.core.model.payment;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分28秒
 */
public class PlatformChannelPayAction implements java.io.Serializable{

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private java.math.BigDecimal amount;
	/**
	 * 
	 */
	private Integer number;
	/**
	 * 
	 */
	private Integer count;
	/**
	 * 
	 */
	private String payOrderActionId;
	
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
	public Integer getNumber(){
		return number;
	}
	
	/**
	 * 
	 */
	public void setNumber(Integer number){
		this.number = number;
	}
	
	/**
	 * 
	 */
	public Integer getCount(){
		return count;
	}
	
	/**
	 * 
	 */
	public void setCount(Integer count){
		this.count = count;
	}
	
	/**
	 * 
	 */
	public String getPayOrderActionId(){
		return payOrderActionId;
	}
	
	/**
	 * 
	 */
	public void setPayOrderActionId(String payOrderActionId){
		this.payOrderActionId = payOrderActionId;
	}

}
