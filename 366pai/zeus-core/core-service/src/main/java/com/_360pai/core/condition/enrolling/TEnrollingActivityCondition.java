
package com._360pai.core.condition.enrolling;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年10月16日 13时57分16秒
 */
public class TEnrollingActivityCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 
	 */
	private java.lang.String status;
	/**
	 * 
	 */
	private java.lang.String name;
	/**
	 * 
	 */
	private java.lang.Integer cityId;
	/**
	 * 
	 */
	private java.lang.Integer agencyId;
	/**
	 * 
	 */
	private java.lang.Integer propertyId;
	/**
	 * 
	 */
	private java.lang.Integer categoryId;
	/**
	 * 
	 */
	private java.math.BigDecimal refPrice;
	/**
	 * 
	 */
	private java.math.BigDecimal deposit;
	/**
	 * 
	 */
	private java.lang.String code;
	/**
	 * 
	 */
	private java.util.Date beginAt;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private java.util.Date endAt;
	/**
	 * 
	 */
	private java.lang.Integer partyId;
	/**
	 * 
	 */
	private java.lang.String contactName;
	/**
	 * 
	 */
	private java.lang.String contactPhone;
	/**
	 * 
	 */
	private java.lang.String contactQq;
	/**
	 * 
	 */
	private java.util.Date expireAt;
	/**
	 * 
	 */
	private com.alibaba.fastjson.JSONObject extra;
	/**
	 * 
	 */
	private java.lang.String remark;
	/**
	 * 
	 */
	private java.lang.String descriptionDoc;
	/**
	 * 
	 */
	private java.lang.String detail;
	/**
	 * 
	 */
	private java.lang.Integer participantNumber;
	/**
	 * 
	 */
	private java.lang.String rejectReason;
	/**
	 * 
	 */
	private java.math.BigDecimal commissionPercent;
	/**
	 * 
	 */
	private java.lang.Integer assetCategoryGroupId;
	/**
	 * 
	 */
	private java.lang.String options;
	/**
	 * 
	 */
	private java.lang.Boolean endNotified;
	/**
	 * 
	 */
	private java.lang.String visibilityLevel;
	
	/**
	 * 
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 
	 */
	public java.lang.String getStatus(){
		return status;
	}
	
	/**
	 * 
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	
	/**
	 * 
	 */
	public java.lang.String getName(){
		return name;
	}
	
	/**
	 * 
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getCityId(){
		return cityId;
	}
	
	/**
	 * 
	 */
	public void setCityId(java.lang.Integer cityId){
		this.cityId = cityId;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getAgencyId(){
		return agencyId;
	}
	
	/**
	 * 
	 */
	public void setAgencyId(java.lang.Integer agencyId){
		this.agencyId = agencyId;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getPropertyId(){
		return propertyId;
	}
	
	/**
	 * 
	 */
	public void setPropertyId(java.lang.Integer propertyId){
		this.propertyId = propertyId;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getCategoryId(){
		return categoryId;
	}
	
	/**
	 * 
	 */
	public void setCategoryId(java.lang.Integer categoryId){
		this.categoryId = categoryId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getRefPrice(){
		return refPrice;
	}
	
	/**
	 * 
	 */
	public void setRefPrice(java.math.BigDecimal refPrice){
		this.refPrice = refPrice;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getDeposit(){
		return deposit;
	}
	
	/**
	 * 
	 */
	public void setDeposit(java.math.BigDecimal deposit){
		this.deposit = deposit;
	}
	
	/**
	 * 
	 */
	public java.lang.String getCode(){
		return code;
	}
	
	/**
	 * 
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	
	/**
	 * 
	 */
	public java.util.Date getBeginAt(){
		return beginAt;
	}
	
	/**
	 * 
	 */
	public void setBeginAt(java.util.Date beginAt){
		this.beginAt = beginAt;
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
	public java.util.Date getEndAt(){
		return endAt;
	}
	
	/**
	 * 
	 */
	public void setEndAt(java.util.Date endAt){
		this.endAt = endAt;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getPartyId(){
		return partyId;
	}
	
	/**
	 * 
	 */
	public void setPartyId(java.lang.Integer partyId){
		this.partyId = partyId;
	}
	
	/**
	 * 
	 */
	public java.lang.String getContactName(){
		return contactName;
	}
	
	/**
	 * 
	 */
	public void setContactName(java.lang.String contactName){
		this.contactName = contactName;
	}
	
	/**
	 * 
	 */
	public java.lang.String getContactPhone(){
		return contactPhone;
	}
	
	/**
	 * 
	 */
	public void setContactPhone(java.lang.String contactPhone){
		this.contactPhone = contactPhone;
	}
	
	/**
	 * 
	 */
	public java.lang.String getContactQq(){
		return contactQq;
	}
	
	/**
	 * 
	 */
	public void setContactQq(java.lang.String contactQq){
		this.contactQq = contactQq;
	}
	
	/**
	 * 
	 */
	public java.util.Date getExpireAt(){
		return expireAt;
	}
	
	/**
	 * 
	 */
	public void setExpireAt(java.util.Date expireAt){
		this.expireAt = expireAt;
	}
	
	/**
	 * 
	 */
	public com.alibaba.fastjson.JSONObject getExtra(){
		return extra;
	}
	
	/**
	 * 
	 */
	public void setExtra(com.alibaba.fastjson.JSONObject extra){
		this.extra = extra;
	}
	
	/**
	 * 
	 */
	public java.lang.String getRemark(){
		return remark;
	}
	
	/**
	 * 
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	
	/**
	 * 
	 */
	public java.lang.String getDescriptionDoc(){
		return descriptionDoc;
	}
	
	/**
	 * 
	 */
	public void setDescriptionDoc(java.lang.String descriptionDoc){
		this.descriptionDoc = descriptionDoc;
	}
	
	/**
	 * 
	 */
	public java.lang.String getDetail(){
		return detail;
	}
	
	/**
	 * 
	 */
	public void setDetail(java.lang.String detail){
		this.detail = detail;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getParticipantNumber(){
		return participantNumber;
	}
	
	/**
	 * 
	 */
	public void setParticipantNumber(java.lang.Integer participantNumber){
		this.participantNumber = participantNumber;
	}
	
	/**
	 * 
	 */
	public java.lang.String getRejectReason(){
		return rejectReason;
	}
	
	/**
	 * 
	 */
	public void setRejectReason(java.lang.String rejectReason){
		this.rejectReason = rejectReason;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getCommissionPercent(){
		return commissionPercent;
	}
	
	/**
	 * 
	 */
	public void setCommissionPercent(java.math.BigDecimal commissionPercent){
		this.commissionPercent = commissionPercent;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getAssetCategoryGroupId(){
		return assetCategoryGroupId;
	}
	
	/**
	 * 
	 */
	public void setAssetCategoryGroupId(java.lang.Integer assetCategoryGroupId){
		this.assetCategoryGroupId = assetCategoryGroupId;
	}
	
	/**
	 * 
	 */
	public java.lang.String getOptions(){
		return options;
	}
	
	/**
	 * 
	 */
	public void setOptions(java.lang.String options){
		this.options = options;
	}
	
	/**
	 * 
	 */
	public java.lang.Boolean getEndNotified(){
		return endNotified;
	}
	
	/**
	 * 
	 */
	public void setEndNotified(java.lang.Boolean endNotified){
		this.endNotified = endNotified;
	}
	
	/**
	 * 
	 */
	public java.lang.String getVisibilityLevel(){
		return visibilityLevel;
	}
	
	/**
	 * 
	 */
	public void setVisibilityLevel(java.lang.String visibilityLevel){
		this.visibilityLevel = visibilityLevel;
	}

}