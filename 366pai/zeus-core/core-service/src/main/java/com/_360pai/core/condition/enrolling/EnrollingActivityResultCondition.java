
package com._360pai.core.condition.enrolling;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分21秒
 */
public class EnrollingActivityResultCondition implements DaoCondition{

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