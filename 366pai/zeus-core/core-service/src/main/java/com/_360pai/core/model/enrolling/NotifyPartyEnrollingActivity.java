
package com._360pai.core.model.enrolling;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分29秒
 */
public class NotifyPartyEnrollingActivity implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer accountId;
	/**
	 * 
	 */
	private Integer partyId;
	/**
	 * 
	 */
	private Integer activityId;
	/**
	 * 
	 */
	private Boolean beginNotified;
	/**
	 * 
	 */
	private Boolean endNotified;
	/**
	 * 
	 */
	private java.util.Date createdAt;

	private Short pathType;

	public Short getPathType() {
		return pathType;
	}

	public void setPathType(Short pathType) {
		this.pathType = pathType;
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
	 * 
	 */
	public Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 
	 */
	public void setAccountId(Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 
	 */
	public Integer getPartyId(){
		return partyId;
	}
	
	/**
	 * 
	 */
	public void setPartyId(Integer partyId){
		this.partyId = partyId;
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
	public Boolean getBeginNotified(){
		return beginNotified;
	}
	
	/**
	 * 
	 */
	public void setBeginNotified(Boolean beginNotified){
		this.beginNotified = beginNotified;
	}
	
	/**
	 * 
	 */
	public Boolean getEndNotified(){
		return endNotified;
	}
	
	/**
	 * 
	 */
	public void setEndNotified(Boolean endNotified){
		this.endNotified = endNotified;
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

}
