
package com._360pai.core.model.assistant;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年12月04日 13时50分38秒
 */
public class TJobActivityViewCountRecode implements java.io.Serializable{

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
	private String createDate;
	/**
	 * 
	 */
	private java.util.Date createAt;
	/**
	 * 增加的浏览量
	 */
	private Integer viewCountNum;

	private Integer agencyId;

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

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
	 * 增加的浏览量
	 */
	public Integer getViewCountNum(){
		return viewCountNum;
	}
	
	/**
	 * 增加的浏览量
	 */
	public void setViewCountNum(Integer viewCountNum){
		this.viewCountNum = viewCountNum;
	}

}
