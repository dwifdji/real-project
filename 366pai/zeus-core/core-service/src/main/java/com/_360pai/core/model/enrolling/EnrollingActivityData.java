
package com._360pai.core.model.enrolling;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分29秒
 */
public class EnrollingActivityData implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private com.alibaba.fastjson.JSONObject content;
	/**
	 * 
	 */
	private Integer activityId;
	
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
	public Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 
	 */
	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}

}
