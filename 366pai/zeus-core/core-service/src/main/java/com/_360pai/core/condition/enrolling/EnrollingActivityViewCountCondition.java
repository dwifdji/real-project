
package com._360pai.core.condition.enrolling;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分21秒
 */
public class EnrollingActivityViewCountCondition implements DaoCondition{

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
	private Integer viewCount;
	
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
	public Integer getViewCount(){
		return viewCount;
	}
	
	/**
	 * 
	 */
	public void setViewCount(Integer viewCount){
		this.viewCount = viewCount;
	}

}