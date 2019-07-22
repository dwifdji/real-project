
package com._360pai.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月03日 13时51分46秒
 */
public class TPersonaDisposalParty implements java.io.Serializable{

	/**
	 *
	 */
	private Integer id;
	/**
	 * t_persona表id
	 */
	private Integer personaId;
	/**
	 * 擅长业务类型（10 评估 20 尽职调查 30 司法处置 40 执行处置 50 清房 60 催收 70 查找资产线索）
	 */
	private String businessType;
	/**
	 * 擅长业务区域，城市逗号分隔
	 */
	private String businessArea;
	/**
	 * 熟悉的法院或执行庭
	 */
	private String familiarCourt;
	/**
	 * 已完成处置的规模
	 */
	private java.math.BigDecimal completeDisposalVolume;
	/**
	 * 正在处置的规模
	 */
	private java.math.BigDecimal ongoingDisposalVolume;
	/**
	 * 处置费用区间开始
	 */
	private java.math.BigDecimal disposalCostRangeStart;
	/**
	 * 处置费用区间结束
	 */
	private java.math.BigDecimal disposalCostRangeEnd;
	/**
	 * 尽调费用区间开始
	 */
	private java.math.BigDecimal dueCostRangeStart;
	/**
	 * 尽调费用区间结束
	 */
	private java.math.BigDecimal dueCostRangeEnd;
	/**
	 * 尽职调查周期（单位月）
	 */
	private java.math.BigDecimal responsibleInvestigationPeriod;
	/**
	 * 团队人数
	 */
	private Integer teamNumber;
	/**
	 * 处置周期
	 */
	private java.math.BigDecimal disposalPeriod;
	/**
	 * 平均处置周期区间开始（单位月）
	 */
	private java.math.BigDecimal averageDisposalPeriodStart;
	/**
	 * 平均处置周期区间结束（单位月）
	 */
	private java.math.BigDecimal averageDisposalPeriodEnd;
	/**
	 * 是否会参与处置标的的投资
	 */
	private Boolean isGoingToInvestDisposalAsset;
	/**
	 * 是否做全风险代理
	 */
	private Boolean isDoFullyRiskAgency;
	/**
	 * 标的类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	private String assetPropertyType;
	/**
	 * 口估
	 */
	private Boolean oralEvaluate;
	/**
	 * 口估费用
	 */
	private java.math.BigDecimal oralEvaluateFee;
	/**
	 * 评估报告
	 */
	private Boolean evaluateReport;
	/**
	 * 评估报告费用
	 */
	private java.math.BigDecimal evaluateReportFee;
	/**
	 * 评估周期开始
	 */
	private java.math.BigDecimal evaluateTermStart;
	/**
	 * 评估周期结束
	 */
	private java.math.BigDecimal evaluateTermEnd;
	/**
	 * 评估机构等级
	 */
	private String evaluateAgencyLevel;
	/**
	 * 催收方式
	 */
	private String collectionMode;
	/**
	 * 催收费用开始
	 */
	private java.math.BigDecimal collectionFeeStart;
	/**
	 * 催收费用结束
	 */
	private java.math.BigDecimal collectionFeeEnd;
	/**
	 * 催收周期
	 */
	private java.math.BigDecimal collectionPeriod;
	/**
	 * 资产线索
	 */
	private String assetClues;
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
	 * 擅长业务类型（10 评估 20 尽职调查 30 司法处置 40 执行处置 50 清房 60 催收 70 查找资产线索）
	 */
	public String getBusinessType(){
		return businessType;
	}

	/**
	 * 擅长业务类型（10 评估 20 尽职调查 30 司法处置 40 执行处置 50 清房 60 催收 70 查找资产线索）
	 */
	public void setBusinessType(String businessType){
		this.businessType = businessType;
	}

	/**
	 * 擅长业务区域，城市逗号分隔
	 */
	public String getBusinessArea(){
		return businessArea;
	}

	/**
	 * 擅长业务区域，城市逗号分隔
	 */
	public void setBusinessArea(String businessArea){
		this.businessArea = businessArea;
	}

	/**
	 * 熟悉的法院或执行庭
	 */
	public String getFamiliarCourt(){
		return familiarCourt;
	}

	/**
	 * 熟悉的法院或执行庭
	 */
	public void setFamiliarCourt(String familiarCourt){
		this.familiarCourt = familiarCourt;
	}

	/**
	 * 已完成处置的规模
	 */
	public java.math.BigDecimal getCompleteDisposalVolume(){
		return completeDisposalVolume;
	}

	/**
	 * 已完成处置的规模
	 */
	public void setCompleteDisposalVolume(java.math.BigDecimal completeDisposalVolume){
		this.completeDisposalVolume = completeDisposalVolume;
	}

	/**
	 * 正在处置的规模
	 */
	public java.math.BigDecimal getOngoingDisposalVolume(){
		return ongoingDisposalVolume;
	}

	/**
	 * 正在处置的规模
	 */
	public void setOngoingDisposalVolume(java.math.BigDecimal ongoingDisposalVolume){
		this.ongoingDisposalVolume = ongoingDisposalVolume;
	}

	/**
	 * 处置费用区间开始
	 */
	public java.math.BigDecimal getDisposalCostRangeStart(){
		return disposalCostRangeStart;
	}

	/**
	 * 处置费用区间开始
	 */
	public void setDisposalCostRangeStart(java.math.BigDecimal disposalCostRangeStart){
		this.disposalCostRangeStart = disposalCostRangeStart;
	}

	/**
	 * 处置费用区间结束
	 */
	public java.math.BigDecimal getDisposalCostRangeEnd(){
		return disposalCostRangeEnd;
	}

	/**
	 * 处置费用区间结束
	 */
	public void setDisposalCostRangeEnd(java.math.BigDecimal disposalCostRangeEnd){
		this.disposalCostRangeEnd = disposalCostRangeEnd;
	}

	/**
	 * 尽调费用区间开始
	 */
	public java.math.BigDecimal getDueCostRangeStart(){
		return dueCostRangeStart;
	}

	/**
	 * 尽调费用区间开始
	 */
	public void setDueCostRangeStart(java.math.BigDecimal dueCostRangeStart){
		this.dueCostRangeStart = dueCostRangeStart;
	}

	/**
	 * 尽调费用区间结束
	 */
	public java.math.BigDecimal getDueCostRangeEnd(){
		return dueCostRangeEnd;
	}

	/**
	 * 尽调费用区间结束
	 */
	public void setDueCostRangeEnd(java.math.BigDecimal dueCostRangeEnd){
		this.dueCostRangeEnd = dueCostRangeEnd;
	}

	/**
	 * 尽职调查周期（单位月）
	 */
	public java.math.BigDecimal getResponsibleInvestigationPeriod(){
		return responsibleInvestigationPeriod;
	}

	/**
	 * 尽职调查周期（单位月）
	 */
	public void setResponsibleInvestigationPeriod(java.math.BigDecimal responsibleInvestigationPeriod){
		this.responsibleInvestigationPeriod = responsibleInvestigationPeriod;
	}

	/**
	 * 团队人数
	 */
	public Integer getTeamNumber(){
		return teamNumber;
	}

	/**
	 * 团队人数
	 */
	public void setTeamNumber(Integer teamNumber){
		this.teamNumber = teamNumber;
	}

	/**
	 * 处置周期
	 */
	public java.math.BigDecimal getDisposalPeriod(){
		return disposalPeriod;
	}

	/**
	 * 处置周期
	 */
	public void setDisposalPeriod(java.math.BigDecimal disposalPeriod){
		this.disposalPeriod = disposalPeriod;
	}

	/**
	 * 平均处置周期区间开始（单位月）
	 */
	public java.math.BigDecimal getAverageDisposalPeriodStart(){
		return averageDisposalPeriodStart;
	}

	/**
	 * 平均处置周期区间开始（单位月）
	 */
	public void setAverageDisposalPeriodStart(java.math.BigDecimal averageDisposalPeriodStart){
		this.averageDisposalPeriodStart = averageDisposalPeriodStart;
	}

	/**
	 * 平均处置周期区间结束（单位月）
	 */
	public java.math.BigDecimal getAverageDisposalPeriodEnd(){
		return averageDisposalPeriodEnd;
	}

	/**
	 * 平均处置周期区间结束（单位月）
	 */
	public void setAverageDisposalPeriodEnd(java.math.BigDecimal averageDisposalPeriodEnd){
		this.averageDisposalPeriodEnd = averageDisposalPeriodEnd;
	}

	/**
	 * 是否会参与处置标的的投资
	 */
	public Boolean getIsGoingToInvestDisposalAsset(){
		return isGoingToInvestDisposalAsset;
	}

	/**
	 * 是否会参与处置标的的投资
	 */
	public void setIsGoingToInvestDisposalAsset(Boolean isGoingToInvestDisposalAsset){
		this.isGoingToInvestDisposalAsset = isGoingToInvestDisposalAsset;
	}

	/**
	 * 是否做全风险代理
	 */
	public Boolean getIsDoFullyRiskAgency(){
		return isDoFullyRiskAgency;
	}

	/**
	 * 是否做全风险代理
	 */
	public void setIsDoFullyRiskAgency(Boolean isDoFullyRiskAgency){
		this.isDoFullyRiskAgency = isDoFullyRiskAgency;
	}

	/**
	 * 标的类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	public String getAssetPropertyType(){
		return assetPropertyType;
	}

	/**
	 * 标的类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	public void setAssetPropertyType(String assetPropertyType){
		this.assetPropertyType = assetPropertyType;
	}

	/**
	 * 口估
	 */
	public Boolean getOralEvaluate(){
		return oralEvaluate;
	}

	/**
	 * 口估
	 */
	public void setOralEvaluate(Boolean oralEvaluate){
		this.oralEvaluate = oralEvaluate;
	}

	/**
	 * 口估费用
	 */
	public java.math.BigDecimal getOralEvaluateFee(){
		return oralEvaluateFee;
	}

	/**
	 * 口估费用
	 */
	public void setOralEvaluateFee(java.math.BigDecimal oralEvaluateFee){
		this.oralEvaluateFee = oralEvaluateFee;
	}

	/**
	 * 评估报告
	 */
	public Boolean getEvaluateReport(){
		return evaluateReport;
	}

	/**
	 * 评估报告
	 */
	public void setEvaluateReport(Boolean evaluateReport){
		this.evaluateReport = evaluateReport;
	}

	/**
	 * 评估报告费用
	 */
	public java.math.BigDecimal getEvaluateReportFee(){
		return evaluateReportFee;
	}

	/**
	 * 评估报告费用
	 */
	public void setEvaluateReportFee(java.math.BigDecimal evaluateReportFee){
		this.evaluateReportFee = evaluateReportFee;
	}

	/**
	 * 评估周期开始
	 */
	public java.math.BigDecimal getEvaluateTermStart(){
		return evaluateTermStart;
	}

	/**
	 * 评估周期开始
	 */
	public void setEvaluateTermStart(java.math.BigDecimal evaluateTermStart){
		this.evaluateTermStart = evaluateTermStart;
	}

	/**
	 * 评估周期结束
	 */
	public java.math.BigDecimal getEvaluateTermEnd(){
		return evaluateTermEnd;
	}

	/**
	 * 评估周期结束
	 */
	public void setEvaluateTermEnd(java.math.BigDecimal evaluateTermEnd){
		this.evaluateTermEnd = evaluateTermEnd;
	}

	/**
	 * 评估机构等级
	 */
	public String getEvaluateAgencyLevel(){
		return evaluateAgencyLevel;
	}

	/**
	 * 评估机构等级
	 */
	public void setEvaluateAgencyLevel(String evaluateAgencyLevel){
		this.evaluateAgencyLevel = evaluateAgencyLevel;
	}

	/**
	 * 催收方式
	 */
	public String getCollectionMode(){
		return collectionMode;
	}

	/**
	 * 催收方式
	 */
	public void setCollectionMode(String collectionMode){
		this.collectionMode = collectionMode;
	}

	/**
	 * 催收费用开始
	 */
	public java.math.BigDecimal getCollectionFeeStart(){
		return collectionFeeStart;
	}

	/**
	 * 催收费用开始
	 */
	public void setCollectionFeeStart(java.math.BigDecimal collectionFeeStart){
		this.collectionFeeStart = collectionFeeStart;
	}

	/**
	 * 催收费用结束
	 */
	public java.math.BigDecimal getCollectionFeeEnd(){
		return collectionFeeEnd;
	}

	/**
	 * 催收费用结束
	 */
	public void setCollectionFeeEnd(java.math.BigDecimal collectionFeeEnd){
		this.collectionFeeEnd = collectionFeeEnd;
	}

	/**
	 * 催收周期
	 */
	public java.math.BigDecimal getCollectionPeriod(){
		return collectionPeriod;
	}

	/**
	 * 催收周期
	 */
	public void setCollectionPeriod(java.math.BigDecimal collectionPeriod){
		this.collectionPeriod = collectionPeriod;
	}

	/**
	 * 资产线索
	 */
	public String getAssetClues(){
		return assetClues;
	}

	/**
	 * 资产线索
	 */
	public void setAssetClues(String assetClues){
		this.assetClues = assetClues;
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
