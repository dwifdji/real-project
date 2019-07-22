
package com._360pai.core.model.account;

import java.math.BigDecimal;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月29日 13时21分37秒
 */
public class TPersonaBidder implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * t_persona表id
	 */
	private Integer personaId;
	/**
	 * 资金来源（10 自有资金 20 第三方融资）
	 */
	private String fundSource;
	/**
	 * 是否需要配资（0 否 1 是）
	 */
	private Boolean isNeedFunding;
	/**
	 * 配资需求（10 优先 20 劣后 30 夹层）
	 */
	private String fundingLevel;
	/**
	 * 配资可接受的成本
	 */
	private BigDecimal acceptableFundingRate;
	/**
	 * 购买资产类型（10 债权 20 物权 30 债权+物权）
	 */
	private String assetType;
	/**
	 * 资产类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	private String assetPropertyType;
	/**
	 * 预期年化金收益
	 */
	private BigDecimal prospectiveAnnualIncome;
	/**
	 * 尽职调查（10 自行 20 第三方）
	 */
	private String responsibleInvestigation;
	/**
	 * 处置调查（10 自行 20 第三方）
	 */
	private String dispoalInvestigation;
	/**
	 * 回款周期（月）
	 */
	private BigDecimal returnPeriod;
	/**
	 * 今年还可投金额
	 */
	private BigDecimal remainInvestAmount;
	/**
	 * 目前持有的资产状况
	 */
	private String currentHoldAssetStatus;
	/**
	 * 今年主要的收购的计划
	 */
	private String annualPurchasePlan;
	/**
	 * 投资区域
	 */
	private String investmentArea;
	/**
	 * 是否删除（0 否 1 是）
	 */
	private Boolean isDelete = false;
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
	 * 资金来源（10 自有资金 20 第三方融资）
	 */
	public String getFundSource(){
		return fundSource;
	}

	/**
	 * 资金来源（10 自有资金 20 第三方融资）
	 */
	public void setFundSource(String fundSource){
		this.fundSource = fundSource;
	}

	/**
	 * 是否需要配资（0 否 1 是）
	 */
	public Boolean getIsNeedFunding(){
		return isNeedFunding;
	}

	/**
	 * 是否需要配资（0 否 1 是）
	 */
	public void setIsNeedFunding(Boolean isNeedFunding){
		this.isNeedFunding = isNeedFunding;
	}

	/**
	 * 配资需求（10 优先 20 劣后 30 夹层）
	 */
	public String getFundingLevel(){
		return fundingLevel;
	}

	/**
	 * 配资需求（10 优先 20 劣后 30 夹层）
	 */
	public void setFundingLevel(String fundingLevel){
		this.fundingLevel = fundingLevel;
	}

	/**
	 * 配资可接受的成本
	 */
	public BigDecimal getAcceptableFundingRate(){
		return acceptableFundingRate;
	}

	/**
	 * 配资可接受的成本
	 */
	public void setAcceptableFundingRate(BigDecimal acceptableFundingRate){
		this.acceptableFundingRate = acceptableFundingRate;
	}

	/**
	 * 购买资产类型（10 债权 20 物权 30 债权+物权）
	 */
	public String getAssetType(){
		return assetType;
	}

	/**
	 * 购买资产类型（10 债权 20 物权 30 债权+物权）
	 */
	public void setAssetType(String assetType){
		this.assetType = assetType;
	}

	/**
	 * 资产类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	public String getAssetPropertyType(){
		return assetPropertyType;
	}

	/**
	 * 资产类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	public void setAssetPropertyType(String assetPropertyType){
		this.assetPropertyType = assetPropertyType;
	}

	/**
	 * 预期年化金收益
	 */
	public BigDecimal getProspectiveAnnualIncome(){
		return prospectiveAnnualIncome;
	}

	/**
	 * 预期年化金收益
	 */
	public void setProspectiveAnnualIncome(BigDecimal prospectiveAnnualIncome){
		this.prospectiveAnnualIncome = prospectiveAnnualIncome;
	}

	/**
	 * 尽职调查（10 自行 20 第三方）
	 */
	public String getResponsibleInvestigation(){
		return responsibleInvestigation;
	}

	/**
	 * 尽职调查（10 自行 20 第三方）
	 */
	public void setResponsibleInvestigation(String responsibleInvestigation){
		this.responsibleInvestigation = responsibleInvestigation;
	}

	/**
	 * 处置调查（10 自行 20 第三方）
	 */
	public String getDispoalInvestigation(){
		return dispoalInvestigation;
	}

	/**
	 * 处置调查（10 自行 20 第三方）
	 */
	public void setDispoalInvestigation(String dispoalInvestigation){
		this.dispoalInvestigation = dispoalInvestigation;
	}

	/**
	 * 回款周期（月）
	 */
	public BigDecimal getReturnPeriod(){
		return returnPeriod;
	}

	/**
	 * 回款周期（月）
	 */
	public void setReturnPeriod(BigDecimal returnPeriod){
		this.returnPeriod = returnPeriod;
	}

	/**
	 * 今年还可投金额
	 */
	public BigDecimal getRemainInvestAmount(){
		return remainInvestAmount;
	}

	/**
	 * 今年还可投金额
	 */
	public void setRemainInvestAmount(BigDecimal remainInvestAmount){
		this.remainInvestAmount = remainInvestAmount;
	}
	
	/**
	 * 
	 */
	public String getCurrentHoldAssetStatus(){
		return currentHoldAssetStatus;
	}
	
	/**
	 * 
	 */
	public void setCurrentHoldAssetStatus(String currentHoldAssetStatus){
		this.currentHoldAssetStatus = currentHoldAssetStatus;
	}
	
	/**
	 * 主要的收购计划
	 */
	public String getAnnualPurchasePlan(){
		return annualPurchasePlan;
	}
	
	/**
	 * 主要的收购计划
	 */
	public void setAnnualPurchasePlan(String annualPurchasePlan){
		this.annualPurchasePlan = annualPurchasePlan;
	}

	public String getInvestmentArea() {
		return investmentArea;
	}

	public void setInvestmentArea(String investmentArea) {
		this.investmentArea = investmentArea;
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
