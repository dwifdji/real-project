
package com._360pai.core.model.payment;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分28秒
 */
public class PayAction implements java.io.Serializable{

	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private Long orderId;
	/**
	 * 
	 */
	private Boolean done;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	
	/**
	 * 
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 
	 */
	public void setType(String type){
		this.type = type;
	}
	
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
	public Long getOrderId(){
		return orderId;
	}
	
	/**
	 * 
	 */
	public void setOrderId(Long orderId){
		this.orderId = orderId;
	}
	
	/**
	 * 
	 */
	public Boolean getDone(){
		return done;
	}
	
	/**
	 * 
	 */
	public void setDone(Boolean done){
		this.done = done;
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

}
