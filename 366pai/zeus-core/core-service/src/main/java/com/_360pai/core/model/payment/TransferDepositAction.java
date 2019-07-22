
package com._360pai.core.model.payment;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分28秒
 */
public class TransferDepositAction implements java.io.Serializable{

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
	private Boolean forSendBack;
	
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
	public Boolean getForSendBack(){
		return forSendBack;
	}
	
	/**
	 * 
	 */
	public void setForSendBack(Boolean forSendBack){
		this.forSendBack = forSendBack;
	}

}
