
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
public class CompanyCondition implements DaoCondition{

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
	private String legalPerson;
	/**
	 * 
	 */
	private String license;
	/**
	 * 
	 */
	private Integer cityId;
	/**
	 * 
	 */
	private String registerAddress;
	/**
	 * 
	 */
	private Integer defaultAgencyId;
	/**
	 * 
	 */
	private Integer adminId;
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
	private String fadadaId;
	/**
	 * 
	 */
	private String licenseImg;
	/**
	 * 
	 */
	private Integer registerCityId;
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
	private String memCode;
	/**
	 * 
	 */
	private Boolean payBind;
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
	private String publicAccountId;
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
	private Integer agencyId;
	/**
	 * 
	 */
	private String bankAccountName;
	/**
	 * 
	 */
	private String bankAccountNumber;
	/**
	 * 
	 */
	private Integer bankId;
	/**
	 * 
	 */
	private Boolean channelPay;
	
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
	public Integer getAdminId(){
		return adminId;
	}
	
	/**
	 * 
	 */
	public void setAdminId(Integer adminId){
		this.adminId = adminId;
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
	public String getPublicAccountId(){
		return publicAccountId;
	}
	
	/**
	 * 
	 */
	public void setPublicAccountId(String publicAccountId){
		this.publicAccountId = publicAccountId;
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
	public String getBankAccountName(){
		return bankAccountName;
	}
	
	/**
	 * 
	 */
	public void setBankAccountName(String bankAccountName){
		this.bankAccountName = bankAccountName;
	}
	
	/**
	 * 
	 */
	public String getBankAccountNumber(){
		return bankAccountNumber;
	}
	
	/**
	 * 
	 */
	public void setBankAccountNumber(String bankAccountNumber){
		this.bankAccountNumber = bankAccountNumber;
	}
	
	/**
	 * 
	 */
	public Integer getBankId(){
		return bankId;
	}
	
	/**
	 * 
	 */
	public void setBankId(Integer bankId){
		this.bankId = bankId;
	}
	
	/**
	 * 
	 */
	public Boolean getChannelPay(){
		return channelPay;
	}
	
	/**
	 * 
	 */
	public void setChannelPay(Boolean channelPay){
		this.channelPay = channelPay;
	}

}