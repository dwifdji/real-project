
package com._360pai.core.condition.agreement;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
public class DelegationAgreementCondition implements DaoCondition{

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