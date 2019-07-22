
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
public class UserCondition implements DaoCondition{

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
	private Integer defaultAgencyId;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String certificateNumber;
	/**
	 * 
	 */
	private java.util.Date certificateBegin;
	/**
	 * 
	 */
	private java.util.Date certificateEnd;
	/**
	 * 
	 */
	private Integer cityId;
	/**
	 * 
	 */
	private String address;
	/**
	 * 
	 */
	private String certificateFrontImg;
	/**
	 * 
	 */
	private String certificateBackImg;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private Boolean isValid;
	/**
	 * 
	 */
	private String mobile;
	/**
	 * 
	 */
	private String fadadaId;
	/**
	 * 
	 */
	private String memCode;
	/**
	 * 
	 */
	private Boolean payBind;
	/**
	 * 
	 */
	private String publicAccountId;
	
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
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 
	 */
	public String getCertificateNumber(){
		return certificateNumber;
	}
	
	/**
	 * 
	 */
	public void setCertificateNumber(String certificateNumber){
		this.certificateNumber = certificateNumber;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCertificateBegin(){
		return certificateBegin;
	}
	
	/**
	 * 
	 */
	public void setCertificateBegin(java.util.Date certificateBegin){
		this.certificateBegin = certificateBegin;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCertificateEnd(){
		return certificateEnd;
	}
	
	/**
	 * 
	 */
	public void setCertificateEnd(java.util.Date certificateEnd){
		this.certificateEnd = certificateEnd;
	}
	
	/**
	 * 
	 */
	public Integer getCityId(){
		return cityId;
	}
	
	/**
	 * 
	 */
	public void setCityId(Integer cityId){
		this.cityId = cityId;
	}
	
	/**
	 * 
	 */
	public String getAddress(){
		return address;
	}
	
	/**
	 * 
	 */
	public void setAddress(String address){
		this.address = address;
	}
	
	/**
	 * 
	 */
	public String getCertificateFrontImg(){
		return certificateFrontImg;
	}
	
	/**
	 * 
	 */
	public void setCertificateFrontImg(String certificateFrontImg){
		this.certificateFrontImg = certificateFrontImg;
	}
	
	/**
	 * 
	 */
	public String getCertificateBackImg(){
		return certificateBackImg;
	}
	
	/**
	 * 
	 */
	public void setCertificateBackImg(String certificateBackImg){
		this.certificateBackImg = certificateBackImg;
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
	public String getFadadaId(){
		return fadadaId;
	}
	
	/**
	 * 
	 */
	public void setFadadaId(String fadadaId){
		this.fadadaId = fadadaId;
	}
	
	/**
	 * 
	 */
	public String getMemCode(){
		return memCode;
	}
	
	/**
	 * 
	 */
	public void setMemCode(String memCode){
		this.memCode = memCode;
	}
	
	/**
	 * 
	 */
	public Boolean getPayBind(){
		return payBind;
	}
	
	/**
	 * 
	 */
	public void setPayBind(Boolean payBind){
		this.payBind = payBind;
	}
	
	/**
	 * 
	 */
	public String getPublicAccountId(){
		return publicAccountId;
	}
	
	/**
	 * 
	 */
	public void setPublicAccountId(String publicAccountId){
		this.publicAccountId = publicAccountId;
	}

}