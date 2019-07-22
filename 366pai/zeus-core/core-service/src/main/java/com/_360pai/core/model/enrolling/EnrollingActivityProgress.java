
package com._360pai.core.model.enrolling;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分29秒
 */
public class EnrollingActivityProgress implements java.io.Serializable{

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
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private String content;
	
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
	public java.util.Date getCreatedAt(){
		return createdAt;
	}
	
	/**
	 * 
	 */
	public void setCreatedAt(java.util.Date createdAt){
		this.createdAt = createdAt;
	}
	
	/**
	 * 
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * 
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * 
	 */
	public String getContent(){
		return content;
	}
	
	/**
	 * 
	 */
	public void setContent(String content){
		this.content = content;
	}

}
