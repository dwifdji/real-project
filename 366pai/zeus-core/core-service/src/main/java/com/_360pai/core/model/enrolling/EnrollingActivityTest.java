
package com._360pai.core.model.enrolling;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年10月16日 13时57分16秒
 */
public class EnrollingActivityTest implements java.io.Serializable{

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
	private java.lang.String cityId;
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
	private java.util.Date beginAt;
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
	private java.lang.String code;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private java.lang.Integer participantNumber;
	/**
	 * 
	 */
	private java.math.BigDecimal commissionPercent;
	/**
	 * 
	 */
	private java.lang.String rejectReason;
	/**
	 * 
	 */
	private java.lang.Integer assetCategoryGroupId;
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
	private java.lang.Integer browseNumber;
	/**
	 * 
	 */
	private java.lang.Integer focusNumber;
	/**
	 * 
	 */
	private java.lang.Integer reminderNumber;
	/**
	 * 
	 */
	private java.lang.Integer applyNumber;
	/**
	 * 
	 */
	private java.lang.String cityName;
	/**
	 * 1 抵押物预招商 2 债权预招商 3物权预招商
	 */
	private java.lang.String type;
	/**
	 * 是否有抵押物 1 有抵押物 0没有抵押物
	 */
	private java.lang.Boolean guarantee;
	/**
	 * 资产亮点
	 */
	private java.lang.String brightSpot;
	
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

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
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
	
	/**
	 * 
	 */
	public java.lang.Integer getBrowseNumber(){
		return browseNumber;
	}
	
	/**
	 * 
	 */
	public void setBrowseNumber(java.lang.Integer browseNumber){
		this.browseNumber = browseNumber;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getFocusNumber(){
		return focusNumber;
	}
	
	/**
	 * 
	 */
	public void setFocusNumber(java.lang.Integer focusNumber){
		this.focusNumber = focusNumber;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getReminderNumber(){
		return reminderNumber;
	}
	
	/**
	 * 
	 */
	public void setReminderNumber(java.lang.Integer reminderNumber){
		this.reminderNumber = reminderNumber;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getApplyNumber(){
		return applyNumber;
	}
	
	/**
	 * 
	 */
	public void setApplyNumber(java.lang.Integer applyNumber){
		this.applyNumber = applyNumber;
	}
	
	/**
	 * 
	 */
	public java.lang.String getCityName(){
		return cityName;
	}
	
	/**
	 * 
	 */
	public void setCityName(java.lang.String cityName){
		this.cityName = cityName;
	}
	
	/**
	 * 1 抵押物预招商 2 债权预招商 3物权预招商
	 */
	public java.lang.String getType(){
		return type;
	}
	
	/**
	 * 1 抵押物预招商 2 债权预招商 3物权预招商
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	
	/**
	 * 是否有抵押物 1 有抵押物 0没有抵押物
	 */
	public java.lang.Boolean getGuarantee(){
		return guarantee;
	}
	
	/**
	 * 是否有抵押物 1 有抵押物 0没有抵押物
	 */
	public void setGuarantee(java.lang.Boolean guarantee){
		this.guarantee = guarantee;
	}
	
	/**
	 * 资产亮点
	 */
	public java.lang.String getBrightSpot(){
		return brightSpot;
	}
	
	/**
	 * 资产亮点
	 */
	public void setBrightSpot(java.lang.String brightSpot){
		this.brightSpot = brightSpot;
	}

}
