
package com._360pai.core.condition.activity;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
public class AgencyPortalActivityCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer agencyPortalId;
	/**
	 * 
	 */
	private Integer activityId;
	/**
	 * 
	 */
	private Integer agencyId;
	/**
	 * 
	 */
	private Integer viewCount;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	
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
	public Integer getAgencyPortalId(){
		return agencyPortalId;
	}
	
	/**
	 * 
	 */
	public void setAgencyPortalId(Integer agencyPortalId){
		this.agencyPortalId = agencyPortalId;
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
	public Integer getAgencyId(){
		return agencyId;
	}
	
	/**
	 * 
	 */
	public void setAgencyId(Integer agencyId){
		this.agencyId = agencyId;
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