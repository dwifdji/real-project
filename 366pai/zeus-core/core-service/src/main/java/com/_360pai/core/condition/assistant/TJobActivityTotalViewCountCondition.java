
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年12月04日 13时50分38秒
 */
public class TJobActivityTotalViewCountCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 类型 1，拍卖 2，招商
	 */
	private Integer activityType;
	/**
	 * 活动id
	 */
	private Integer activityId;
	/**
	 * 
	 */
	private java.util.Date createAt;
	/**
	 * 峰值
	 */
	private Integer totalViewCount;
	
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
	 * 峰值
	 */
	public Integer getTotalViewCount(){
		return totalViewCount;
	}
	
	/**
	 * 峰值
	 */
	public void setTotalViewCount(Integer totalViewCount){
		this.totalViewCount = totalViewCount;
	}

}