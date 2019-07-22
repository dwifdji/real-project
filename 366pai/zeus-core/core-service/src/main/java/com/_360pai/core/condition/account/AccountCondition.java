
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分12秒
 */
public class AccountCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String mobile;
	/**
	 * 
	 */
	private Boolean isValid;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private java.util.Date updatedAt;
	/**
	 * 
	 */
	private String registerSource;
	/**
	 * 
	 */
	private Integer defaultAgencyId;
	/**
	 * 
	 */
	private Integer agencyId;
	/**
	 * 
	 */
	private String passwordHash;
	/**
	 * 
	 */
	private Integer currentPartyId;
	/**
	 * 
	 */
	private Boolean agencyBind;
	/**
	 * 
	 */
	private Boolean isAgencyAdmin;
	/**
	 * 
	 */
	private String source;
	
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
	public String getMobile(){
		return mobile;
	}
	
	/**
	 * 
	 */
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	
	/**
	 * 
	 */
	public Boolean getIsValid(){
		return isValid;
	}
	
	/**
	 * 
	 */
	public void setIsValid(Boolean isValid){
		this.isValid = isValid;
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
	public java.util.Date getUpdatedAt(){
		return updatedAt;
	}
	
	/**
	 * 
	 */
	public void setUpdatedAt(java.util.Date updatedAt){
		this.updatedAt = updatedAt;
	}
	
	/**
	 * 
	 */
	public String getRegisterSource(){
		return registerSource;
	}
	
	/**
	 * 
	 */
	public void setRegisterSource(String registerSource){
		this.registerSource = registerSource;
	}
	
	/**
	 * 
	 */
	public Integer getDefaultAgencyId(){
		return defaultAgencyId;
	}
	
	/**
	 * 
	 */
	public void setDefaultAgencyId(Integer defaultAgencyId){
		this.defaultAgencyId = defaultAgencyId;
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
	public String getPasswordHash(){
		return passwordHash;
	}
	
	/**
	 * 
	 */
	public void setPasswordHash(String passwordHash){
		this.passwordHash = passwordHash;
	}
	
	/**
	 * 
	 */
	public Integer getCurrentPartyId(){
		return currentPartyId;
	}
	
	/**
	 * 
	 */
	public void setCurrentPartyId(Integer currentPartyId){
		this.currentPartyId = currentPartyId;
	}
	
	/**
	 * 
	 */
	public Boolean getAgencyBind(){
		return agencyBind;
	}
	
	/**
	 * 
	 */
	public void setAgencyBind(Boolean agencyBind){
		this.agencyBind = agencyBind;
	}
	
	/**
	 * 
	 */
	public Boolean getIsAgencyAdmin(){
		return isAgencyAdmin;
	}
	
	/**
	 * 
	 */
	public void setIsAgencyAdmin(Boolean isAgencyAdmin){
		this.isAgencyAdmin = isAgencyAdmin;
	}
	
	/**
	 * 
	 */
	public String getSource(){
		return source;
	}
	
	/**
	 * 
	 */
	public void setSource(String source){
		this.source = source;
	}

}