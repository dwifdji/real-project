
package com._360pai.core.condition.payment;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分19秒
 */
public class PlatformChannelPayActionCondition implements DaoCondition{

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