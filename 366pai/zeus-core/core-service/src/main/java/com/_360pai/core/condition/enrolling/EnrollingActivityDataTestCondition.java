
package com._360pai.core.condition.enrolling;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年10月16日 13时57分16秒
 */
public class EnrollingActivityDataTestCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 
	 */
	private com.alibaba.fastjson.JSONObject content;
	/**
	 * 
	 */
	private java.lang.Integer activityId;
	
	/**
	 * 
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 
	 */
	public com.alibaba.fastjson.JSONObject getContent(){
		return content;
	}
	
	/**
	 * 
	 */
	public void setContent(com.alibaba.fastjson.JSONObject content){
		this.content = content;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 
	 */
	public void setActivityId(java.lang.Integer activityId){
		this.activityId = activityId;
	}

}