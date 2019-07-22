
package com._360pai.core.model.agreement;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分26秒
 */
public class DelegationAgreement implements java.io.Serializable{

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
	private Boolean signed;
	/**
	 * 
	 */
	private Boolean allSigned;
	/**
	 * 
	 */
	private String downloadUrl;
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
	private String contractId;
	/**
	 * 
	 */
	private java.util.Date createdAt;
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
