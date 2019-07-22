
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年10月16日 14时02分33秒
 */
public class AgencyCopyCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String accountLicense;
	/**
	 * 
	 */
	private String address;
	/**
	 * 
	 */
	private String authorizationImg;
	/**
	 * 
	 */
	private java.util.Date businessBegin;
	/**
	 * 
	 */
	private java.util.Date businessEnd;
	/**
	 * 
	 */
	private Integer cityId;
	/**
	 * 
	 */
	private String code;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private String email;
	/**
	 * 
	 */
	private String fadadaEmail;
	/**
	 * 
	 */
	private String fadadaId;
	/**
	 * 
	 */
	private String idCard;
	/**
	 * 
	 */
	private String idCardBackImg;
	/**
	 * 
	 */
	private String idCardFrontImg;
	/**
	 * 
	 */
	private String introduction;
	/**
	 * 
	 */
	private String legalPerson;
	/**
	 * 
	 */
	private Boolean isActive;
	/**
	 * 
	 */
	private String license;
	/**
	 * 
	 */
	private String licenseImg;
	/**
	 * 
	 */
	private String logoUrl;
	/**
	 * 
	 */
	private String memCode;
	/**
	 * 
	 */
	private String mobile;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String qualificationImg;
	/**
	 * 
	 */
	private String qualificationNumber;
	/**
	 * 
	 */
	private java.util.Date qualifiedBegin;
	/**
	 * 
	 */
	private java.util.Date qualifiedEnd;
	/**
	 * 
	 */
	private Boolean payBind;
	/**
	 * 
	 */
	private String registerAddress;
	/**
	 * 
	 */
	private Integer registerCityId;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private java.math.BigDecimal serveBuyerPercent;
	/**
	 * 
	 */
	private java.math.BigDecimal serveSellerPercent;
	/**
	 * 
	 */
	private String shortName;
	/**
	 * 
	 */
	private String selfIntroduction;
	/**
	 * 
	 */
	private Boolean isChannelAgent;
	
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
	public String getAccountLicense(){
		return accountLicense;
	}
	
	/**
	 * 
	 */
	public void setAccountLicense(String accountLicense){
		this.accountLicense = accountLicense;
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
	public String getAuthorizationImg(){
		return authorizationImg;
	}
	
	/**
	 * 
	 */
	public void setAuthorizationImg(String authorizationImg){
		this.authorizationImg = authorizationImg;
	}
	
	/**
	 * 
	 */
	public java.util.Date getBusinessBegin(){
		return businessBegin;
	}
	
	/**
	 * 
	 */
	public void setBusinessBegin(java.util.Date businessBegin){
		this.businessBegin = businessBegin;
	}
	
	/**
	 * 
	 */
	public java.util.Date getBusinessEnd(){
		return businessEnd;
	}
	
	/**
	 * 
	 */
	public void setBusinessEnd(java.util.Date businessEnd){
		this.businessEnd = businessEnd;
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
	public String getCode(){
		return code;
	}
	
	/**
	 * 
	 */
	public void setCode(String code){
		this.code = code;
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
	public String getFadadaEmail(){
		return fadadaEmail;
	}
	
	/**
	 * 
	 */
	public void setFadadaEmail(String fadadaEmail){
		this.fadadaEmail = fadadaEmail;
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
	public String getIdCard(){
		return idCard;
	}
	
	/**
	 * 
	 */
	public void setIdCard(String idCard){
		this.idCard = idCard;
	}
	
	/**
	 * 
	 */
	public String getIdCardBackImg(){
		return idCardBackImg;
	}
	
	/**
	 * 
	 */
	public void setIdCardBackImg(String idCardBackImg){
		this.idCardBackImg = idCardBackImg;
	}
	
	/**
	 * 
	 */
	public String getIdCardFrontImg(){
		return idCardFrontImg;
	}
	
	/**
	 * 
	 */
	public void setIdCardFrontImg(String idCardFrontImg){
		this.idCardFrontImg = idCardFrontImg;
	}
	
	/**
	 * 
	 */
	public String getIntroduction(){
		return introduction;
	}
	
	/**
	 * 
	 */
	public void setIntroduction(String introduction){
		this.introduction = introduction;
	}
	
	/**
	 * 
	 */
	public String getLegalPerson(){
		return legalPerson;
	}
	
	/**
	 * 
	 */
	public void setLegalPerson(String legalPerson){
		this.legalPerson = legalPerson;
	}
	
	/**
	 * 
	 */
	public Boolean getIsActive(){
		return isActive;
	}
	
	/**
	 * 
	 */
	public void setIsActive(Boolean isActive){
		this.isActive = isActive;
	}
	
	/**
	 * 
	 */
	public String getLicense(){
		return license;
	}
	
	/**
	 * 
	 */
	public void setLicense(String license){
		this.license = license;
	}
	
	/**
	 * 
	 */
	public String getLicenseImg(){
		return licenseImg;
	}
	
	/**
	 * 
	 */
	public void setLicenseImg(String licenseImg){
		this.licenseImg = licenseImg;
	}
	
	/**
	 * 
	 */
	public String getLogoUrl(){
		return logoUrl;
	}
	
	/**
	 * 
	 */
	public void setLogoUrl(String logoUrl){
		this.logoUrl = logoUrl;
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
	public String getQualificationImg(){
		return qualificationImg;
	}
	
	/**
	 * 
	 */
	public void setQualificationImg(String qualificationImg){
		this.qualificationImg = qualificationImg;
	}
	
	/**
	 * 
	 */
	public String getQualificationNumber(){
		return qualificationNumber;
	}
	
	/**
	 * 
	 */
	public void setQualificationNumber(String qualificationNumber){
		this.qualificationNumber = qualificationNumber;
	}
	
	/**
	 * 
	 */
	public java.util.Date getQualifiedBegin(){
		return qualifiedBegin;
	}
	
	/**
	 * 
	 */
	public void setQualifiedBegin(java.util.Date qualifiedBegin){
		this.qualifiedBegin = qualifiedBegin;
	}
	
	/**
	 * 
	 */
	public java.util.Date getQualifiedEnd(){
		return qualifiedEnd;
	}
	
	/**
	 * 
	 */
	public void setQualifiedEnd(java.util.Date qualifiedEnd){
		this.qualifiedEnd = qualifiedEnd;
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
	public String getRegisterAddress(){
		return registerAddress;
	}
	
	/**
	 * 
	 */
	public void setRegisterAddress(String registerAddress){
		this.registerAddress = registerAddress;
	}
	
	/**
	 * 
	 */
	public Integer getRegisterCityId(){
		return registerCityId;
	}
	
	/**
	 * 
	 */
	public void setRegisterCityId(Integer registerCityId){
		this.registerCityId = registerCityId;
	}
	
	/**
	 * 
	 */
	public String getRemark(){
		return remark;
	}
	
	/**
	 * 
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getServeBuyerPercent(){
		return serveBuyerPercent;
	}
	
	/**
	 * 
	 */
	public void setServeBuyerPercent(java.math.BigDecimal serveBuyerPercent){
		this.serveBuyerPercent = serveBuyerPercent;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getServeSellerPercent(){
		return serveSellerPercent;
	}
	
	/**
	 * 
	 */
	public void setServeSellerPercent(java.math.BigDecimal serveSellerPercent){
		this.serveSellerPercent = serveSellerPercent;
	}
	
	/**
	 * 
	 */
	public String getShortName(){
		return shortName;
	}
	
	/**
	 * 
	 */
	public void setShortName(String shortName){
		this.shortName = shortName;
	}
	
	/**
	 * 
	 */
	public String getSelfIntroduction(){
		return selfIntroduction;
	}
	
	/**
	 * 
	 */
	public void setSelfIntroduction(String selfIntroduction){
		this.selfIntroduction = selfIntroduction;
	}
	
	/**
	 * 
	 */
	public Boolean getIsChannelAgent(){
		return isChannelAgent;
	}
	
	/**
	 * 
	 */
	public void setIsChannelAgent(Boolean isChannelAgent){
		this.isChannelAgent = isChannelAgent;
	}

}