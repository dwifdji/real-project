
package com._360pai.core.condition.payment;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分19秒
 */
public class PayActionCondition implements DaoCondition{

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