
package com._360pai.core.model.enrolling;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分29秒
 */
public class EnrollingActivityContract implements java.io.Serializable{

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
	private Boolean allSigned;
	/**
	 * 
	 */
	private String contractId;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private String downloadUrl;
	/**
	 * 
	 */
	private Boolean signed;
	/**
	 * 
	 */
	private Integer templateId;
	/**
	 * 
	 */
	private String viewpdfUrl;
	/**
	 * 
	 */
	private java.util.Date allSignedAt;
	
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
	public Boolean getAllSigned(){
		return allSigned;
	}
	
	/**
	 * 
	 */
	public void setAllSigned(Boolean allSigned){
		this.allSigned = allSigned;
	}
	
	/**
	 * 
	 */
	public String getContractId(){
		return contractId;
	}
	
	/**
	 * 
	 */
	public void setContractId(String contractId){
		this.contractId = contractId;
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
	public String getDownloadUrl(){
		return downloadUrl;
	}
	
	/**
	 * 
	 */
	public void setDownloadUrl(String downloadUrl){
		this.downloadUrl = downloadUrl;
	}
	
	/**
	 * 
	 */
	public Boolean getSigned(){
		return signed;
	}
	
	/**
	 * 
	 */
	public void setSigned(Boolean signed){
		this.signed = signed;
	}
	
	/**
	 * 
	 */
	public Integer getTemplateId(){
		return templateId;
	}
	
	/**
	 * 
	 */
	public void setTemplateId(Integer templateId){
		this.templateId = templateId;
	}
	
	/**
	 * 
	 */
	public String getViewpdfUrl(){
		return viewpdfUrl;
	}
	
	/**
	 * 
	 */
	public void setViewpdfUrl(String viewpdfUrl){
		this.viewpdfUrl = viewpdfUrl;
	}
	
	/**
	 * 
	 */
	public java.util.Date getAllSignedAt(){
		return allSignedAt;
	}
	
	/**
	 * 
	 */
	public void setAllSignedAt(java.util.Date allSignedAt){
		this.allSignedAt = allSignedAt;
	}

}
