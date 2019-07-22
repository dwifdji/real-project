
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月03日 18时23分49秒
 */
public class TJobFocusReminderCountRecordCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 活动id
	 */
	private Integer activityId;
	/**
	 * 类型 1，拍卖 2，招商
	 */
	private Integer activityType;
	/**
	 * 类型 1，focus 2，reminder
	 */
	private Integer recodeType;
	/**
	 * 
	 */
	private String createDate;
	/**
	 * 
	 */
	private java.util.Date createAt;
	/**
	 * 增加的量
	 */
	private Integer countNum;
	
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
	 * 活动id
	 */
	public Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 活动id
	 */
	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 类型 1，拍卖 2，招商
	 */
	public Integer getActivityType(){
		return activityType;
	}
	
	/**
	 * 类型 1，拍卖 2，招商
	 */
	public void setActivityType(Integer activityType){
		this.activityType = activityType;
	}
	
	/**
	 * 类型 1，focus 2，reminder
	 */
	public Integer getRecodeType(){
		return recodeType;
	}
	
	/**
	 * 类型 1，focus 2，reminder
	 */
	public void setRecodeType(Integer recodeType){
		this.recodeType = recodeType;
	}
	
	/**
	 * 
	 */
	public String getCreateDate(){
		return createDate;
	}
	
	/**
	 * 
	 */
	public void setCreateDate(String createDate){
		this.createDate = createDate;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreateAt(){
		return createAt;
	}
	
	/**
	 * 
	 */
	public void setCreateAt(java.util.Date createAt){
		this.createAt = createAt;
	}
	
	/**
	 * 增加的量
	 */
	public Integer getCountNum(){
		return countNum;
	}
	
	/**
	 * 增加的量
	 */
	public void setCountNum(Integer countNum){
		this.countNum = countNum;
	}

}