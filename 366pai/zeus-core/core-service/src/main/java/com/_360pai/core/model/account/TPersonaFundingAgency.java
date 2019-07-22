
package com._360pai.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月03日 15时55分23秒
 */
public class TPersonaFundingAgency implements java.io.Serializable{

	/**
	 *
	 */
	private Integer id;
	/**
	 * t_persona表id
	 */
	private Integer personaId;
	/**
	 * 资金来源
	 */
	private String fundSource;
	/**
	 * 配资风控要求
	 */
	private String fundingRiskManagementDemand;
	/**
	 * 配资标的类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	private String assetPropertyType;
	/**
	 * 配资区域，城市逗号分隔
	 */
	private String businessArea;
	/**
	 * 配资级别（10 优先 20 劣后 30 夹层）
	 */
	private String fundingLevel;
	/**
	 * 配资级别优先比例
	 */
	private java.math.BigDecimal fundingLevelPriorRate;
	/**
	 * 配资级别劣后比例
	 */
	private java.math.BigDecimal fundingLevelInferiorRate;
	/**
	 * 配资级别夹层比例
	 */
	private java.math.BigDecimal fundingLevelInterbedRate;
	/**
	 * 配资比列
	 */
	private java.math.BigDecimal fundingPercent;
	/**
	 * 多快配到资（配资时间，单位天）
	 */
	private java.math.BigDecimal fundingTime;
	/**
	 * 配资期限（单位月）
	 */
	private java.math.BigDecimal fundingTerm;
	/**
	 * 配资期限开始（单位月）
	 */
	private java.math.BigDecimal fundingTermStart;
	/**
	 * 配资期限结束（单位月）
	 */
	private java.math.BigDecimal fundingTermEnd;
	/**
	 * 配资收益要求
	 */
	private java.math.BigDecimal fundingIncomeDemand;
	/**
	 * 配置成本开始
	 */
	private java.math.BigDecimal fundingRateStart;
	/**
	 * 配置成本结束
	 */
	private java.math.BigDecimal fundingRateEnd;
	/**
	 * 是否参与处置（0 否 1 是）
	 */
	private Boolean isPartakeDispose;
	/**
	 * 对需求方特殊要求
	 */
	private String specialRequirementOnDemandSide;
	/**
	 * 今年还可配资的体量
	 */
	private java.math.BigDecimal remainingFundingVolume;
	/**
	 * 其它收费
	 */
	private java.math.BigDecimal otherFee;
	/**
	 * 是否删除（0 否 1 是）
	 */
	private Boolean isDelete;
	/**
	 *
	 */
	private java.util.Date createTime;
	/**
	 *
	 */
	private java.util.Date updateTime;

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
	 * t_persona表id
	 */
	public Integer getPersonaId(){
		return personaId;
	}

	/**
	 * t_persona表id
	 */
	public void setPersonaId(Integer personaId){
		this.personaId = personaId;
	}

	/**
	 * 资金来源
	 */
	public String getFundSource(){
		return fundSource;
	}

	/**
	 * 资金来源
	 */
	public void setFundSource(String fundSource){
		this.fundSource = fundSource;
	}

	/**
	 * 配资风控要求
	 */
	public String getFundingRiskManagementDemand(){
		return fundingRiskManagementDemand;
	}

	/**
	 * 配资风控要求
	 */
	public void setFundingRiskManagementDemand(String fundingRiskManagementDemand){
		this.fundingRiskManagementDemand = fundingRiskManagementDemand;
	}

	/**
	 * 配资标的类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	public String getAssetPropertyType(){
		return assetPropertyType;
	}

	/**
	 * 配资标的类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	public void setAssetPropertyType(String assetPropertyType){
		this.assetPropertyType = assetPropertyType;
	}

	/**
	 * 配资区域，城市逗号分隔
	 */
	public String getBusinessArea(){
		return businessArea;
	}

	/**
	 * 配资区域，城市逗号分隔
	 */
	public void setBusinessArea(String businessArea){
		this.businessArea = businessArea;
	}

	/**
	 * 配资级别（10 优先 20 劣后 30 夹层）
	 */
	public String getFundingLevel(){
		return fundingLevel;
	}

	/**
	 * 配资级别（10 优先 20 劣后 30 夹层）
	 */
	public void setFundingLevel(String fundingLevel){
		this.fundingLevel = fundingLevel;
	}

	/**
	 * 配资级别优先比例
	 */
	public java.math.BigDecimal getFundingLevelPriorRate(){
		return fundingLevelPriorRate;
	}

	/**
	 * 配资级别优先比例
	 */
	public void setFundingLevelPriorRate(java.math.BigDecimal fundingLevelPriorRate){
		this.fundingLevelPriorRate = fundingLevelPriorRate;
	}

	/**
	 * 配资级别劣后比例
	 */
	public java.math.BigDecimal getFundingLevelInferiorRate(){
		return fundingLevelInferiorRate;
	}

	/**
	 * 配资级别劣后比例
	 */
	public void setFundingLevelInferiorRate(java.math.BigDecimal fundingLevelInferiorRate){
		this.fundingLevelInferiorRate = fundingLevelInferiorRate;
	}

	/**
	 * 配资级别夹层比例
	 */
	public java.math.BigDecimal getFundingLevelInterbedRate(){
		return fundingLevelInterbedRate;
	}

	/**
	 * 配资级别夹层比例
	 */
	public void setFundingLevelInterbedRate(java.math.BigDecimal fundingLevelInterbedRate){
		this.fundingLevelInterbedRate = fundingLevelInterbedRate;
	}

	/**
	 * 配资比列
	 */
	public java.math.BigDecimal getFundingPercent(){
		return fundingPercent;
	}

	/**
	 * 配资比列
	 */
	public void setFundingPercent(java.math.BigDecimal fundingPercent){
		this.fundingPercent = fundingPercent;
	}

	/**
	 * 多快配到资（配资时间，单位天）
	 */
	public java.math.BigDecimal getFundingTime(){
		return fundingTime;
	}

	/**
	 * 多快配到资（配资时间，单位天）
	 */
	public void setFundingTime(java.math.BigDecimal fundingTime){
		this.fundingTime = fundingTime;
	}

	/**
	 * 配资期限（单位月）
	 */
	public java.math.BigDecimal getFundingTerm(){
		return fundingTerm;
	}

	/**
	 * 配资期限（单位月）
	 */
	public void setFundingTerm(java.math.BigDecimal fundingTerm){
		this.fundingTerm = fundingTerm;
	}

	/**
	 * 配资期限开始（单位月）
	 */
	public java.math.BigDecimal getFundingTermStart(){
		return fundingTermStart;
	}

	/**
	 * 配资期限开始（单位月）
	 */
	public void setFundingTermStart(java.math.BigDecimal fundingTermStart){
		this.fundingTermStart = fundingTermStart;
	}

	/**
	 * 配资期限结束（单位月）
	 */
	public java.math.BigDecimal getFundingTermEnd(){
		return fundingTermEnd;
	}

	/**
	 * 配资期限结束（单位月）
	 */
	public void setFundingTermEnd(java.math.BigDecimal fundingTermEnd){
		this.fundingTermEnd = fundingTermEnd;
	}

	/**
	 * 配资收益要求
	 */
	public java.math.BigDecimal getFundingIncomeDemand(){
		return fundingIncomeDemand;
	}

	/**
	 * 配资收益要求
	 */
	public void setFundingIncomeDemand(java.math.BigDecimal fundingIncomeDemand){
		this.fundingIncomeDemand = fundingIncomeDemand;
	}

	/**
	 * 配置成本开始
	 */
	public java.math.BigDecimal getFundingRateStart(){
		return fundingRateStart;
	}

	/**
	 * 配置成本开始
	 */
	public void setFundingRateStart(java.math.BigDecimal fundingRateStart){
		this.fundingRateStart = fundingRateStart;
	}

	/**
	 * 配置成本结束
	 */
	public java.math.BigDecimal getFundingRateEnd(){
		return fundingRateEnd;
	}

	/**
	 * 配置成本结束
	 */
	public void setFundingRateEnd(java.math.BigDecimal fundingRateEnd){
		this.fundingRateEnd = fundingRateEnd;
	}

	/**
	 * 是否参与处置（0 否 1 是）
	 */
	public Boolean getIsPartakeDispose(){
		return isPartakeDispose;
	}

	/**
	 * 是否参与处置（0 否 1 是）
	 */
	public void setIsPartakeDispose(Boolean isPartakeDispose){
		this.isPartakeDispose = isPartakeDispose;
	}

	/**
	 * 对需求方特殊要求
	 */
	public String getSpecialRequirementOnDemandSide(){
		return specialRequirementOnDemandSide;
	}

	/**
	 * 对需求方特殊要求
	 */
	public void setSpecialRequirementOnDemandSide(String specialRequirementOnDemandSide){
		this.specialRequirementOnDemandSide = specialRequirementOnDemandSide;
	}

	/**
	 * 今年还可配资的体量
	 */
	public java.math.BigDecimal getRemainingFundingVolume(){
		return remainingFundingVolume;
	}

	/**
	 * 今年还可配资的体量
	 */
	public void setRemainingFundingVolume(java.math.BigDecimal remainingFundingVolume){
		this.remainingFundingVolume = remainingFundingVolume;
	}

	/**
	 * 其它收费
	 */
	public java.math.BigDecimal getOtherFee(){
		return otherFee;
	}

	/**
	 * 其它收费
	 */
	public void setOtherFee(java.math.BigDecimal otherFee){
		this.otherFee = otherFee;
	}

	/**
	 * 是否删除（0 否 1 是）
	 */
	public Boolean getIsDelete(){
		return isDelete;
	}

	/**
	 * 是否删除（0 否 1 是）
	 */
	public void setIsDelete(Boolean isDelete){
		this.isDelete = isDelete;
	}

	/**
	 *
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}

	/**
	 *
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	/**
	 *
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}

	/**
	 *
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}
