
package com._360pai.core.model.activity;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分25秒
 */
public class AgencyPortalActivity implements java.io.Serializable{

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
	private Integer depositCount;
	/**
	 *
	 */
	private String logoUrl;
	/**
	 *
	 */
	private String cityName;
	/**
	 *
	 */
		private String agencyName;
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

	public Integer getDepositCount() {
		return depositCount;
	}

	public void setDepositCount(Integer depositCount) {
		this.depositCount = depositCount;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
