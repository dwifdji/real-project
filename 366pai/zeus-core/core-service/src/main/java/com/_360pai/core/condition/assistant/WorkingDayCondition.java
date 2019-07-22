
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分18秒
 */
public class WorkingDayCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private java.util.Date date;
	/**
	 * 
	 */
	private Boolean isWorking;
	/**
	 * 
	 */
	private String name;
	
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
	public java.util.Date getDate(){
		return date;
	}
	
	/**
	 * 
	 */
	public void setDate(java.util.Date date){
		this.date = date;
	}
	
	/**
	 * 
	 */
	public Boolean getIsWorking(){
		return isWorking;
	}
	
	/**
	 * 
	 */
	public void setIsWorking(Boolean isWorking){
		this.isWorking = isWorking;
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

}