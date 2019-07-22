
package com._360pai.core.model.payment;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分28秒
 */
public class ReleasePayCommissionAction implements java.io.Serializable{

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

}
