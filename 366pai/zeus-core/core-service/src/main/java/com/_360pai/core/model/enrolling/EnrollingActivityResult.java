
package com._360pai.core.model.enrolling;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分29秒
 */
public class EnrollingActivityResult implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer activityId;
	/**
	 * 
	 */
	private String buyerType;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String number;
	/**
	 * 
	 */
	private java.math.BigDecimal price;
	
	/**
	 * 
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 
	 */
	public Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 
	 */
	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 
	 */
	public String getBuyerType(){
		return buyerType;
	}
	
	/**
	 * 
	 */
	public void setBuyerType(String buyerType){
		this.buyerType = buyerType;
	}
	
	/**
	 * 
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 
	 */
	public String getNumber(){
		return number;
	}
	
	/**
	 * 
	 */
	public void setNumber(String number){
		this.number = number;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getPrice(){
		return price;
	}
	
	/**
	 * 
	 */
	public void setPrice(java.math.BigDecimal price){
		this.price = price;
	}

}
