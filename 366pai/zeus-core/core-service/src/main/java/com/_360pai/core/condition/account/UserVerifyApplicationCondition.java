
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
public class UserVerifyApplicationCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
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
	private String mobile;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private Integer cityId;
	/**
	 * 
	 */
	private String status;
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
	private String address;
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
	private String rejectReason;
	/**
	 * 
	 */
	private Integer accountId;
	/**
	 * 
	 */
	private String email;
	/**
	 * 
	 */
	private Integer defaultAgencyId;
	/**
	 * 
	 */
	private java.util.Date operatorAt;
	/**
	 * 
	 */
	private Integer operatorId;
	
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
	public String getStatus(){
		return status;
	}
	
	/**
	 * 
	 */
	public void setStatus(String status){
		this.status = status;
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
	public String getRejectReason(){
		return rejectReason;
	}
	
	/**
	 * 
	 */
	public void setRejectReason(String rejectReason){
		this.rejectReason = rejectReason;
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
	public String getEmail(){
		return email;
	}
	
	/**
	 * 
	 */
	public void setEmail(String email){
		this.email = email;
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
	public java.util.Date getOperatorAt(){
		return operatorAt;
	}
	
	/**
	 * 
	 */
	public void setOperatorAt(java.util.Date operatorAt){
		this.operatorAt = operatorAt;
	}
	
	/**
	 * 
	 */
	public Integer getOperatorId(){
		return operatorId;
	}
	
	/**
	 * 
	 */
	public void setOperatorId(Integer operatorId){
		this.operatorId = operatorId;
	}

}