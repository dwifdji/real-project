
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;

import java.math.BigDecimal;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月29日 13时21分37秒
 */
public class TPersonaUnionAuctionAgencyCondition implements DaoCondition {

	/**
	 * 
	 */
	private Integer id;
	/**
	 * t_persona表id
	 */
	private Integer personaId;
	/**
	 * 擅长拍卖的类型（10 债权 20 物权 30 债权+物权）
	 */
	private String assetType;
	/**
	 * 擅长拍卖的标的类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	private String assetPropertyType;
	/**
	 * 擅长拍卖区域，城市逗号分隔
	 */
	private String businessArea;
	/**
	 * 投资人数量
	 */
	private Integer investorNumber;
	/**
	 * 竞买人数量
	 */
	private Integer bidderNumber;
	/**
	 * 委托人数量
	 */
	private Integer consignorNumber;
	/**
	 * 上年度不良资产处置规模
	 */
	private BigDecimal lastYearDisposalAssetVolume;
	/**
	 * 上年度总成交额
	 */
	private BigDecimal lastYearTotalDealVolume;
	/**
	 * 是否有线上拍卖经验（0 否 1 是）
	 */
	private Boolean hasOnlineAuctionExperience;
	/**
	 * 是否有长期合作服务商（0 否 1 是）
	 */
	private Boolean hasFixedCooperateServiceProvider;
	/**
	 * 其它业务需求
	 */
	private String otherDemand;
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
	 * 擅长拍卖的类型（10 债权 20 物权 30 债权+物权）
	 */
	public String getAssetType(){
		return assetType;
	}

	/**
	 * 擅长拍卖的类型（10 债权 20 物权 30 债权+物权）
	 */
	public void setAssetType(String assetType){
		this.assetType = assetType;
	}

	/**
	 * 擅长拍卖的标的类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	public String getAssetPropertyType(){
		return assetPropertyType;
	}

	/**
	 * 擅长拍卖的标的类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	public void setAssetPropertyType(String assetPropertyType){
		this.assetPropertyType = assetPropertyType;
	}

	/**
	 * 擅长拍卖区域，城市逗号分隔
	 */
	public String getBusinessArea(){
		return businessArea;
	}

	/**
	 * 擅长拍卖区域，城市逗号分隔
	 */
	public void setBusinessArea(String businessArea){
		this.businessArea = businessArea;
	}

	/**
	 * 投资人数量
	 */
	public Integer getInvestorNumber(){
		return investorNumber;
	}

	/**
	 * 投资人数量
	 */
	public void setInvestorNumber(Integer investorNumber){
		this.investorNumber = investorNumber;
	}

	public Integer getBidderNumber() {
		return bidderNumber;
	}

	public void setBidderNumber(Integer bidderNumber) {
		this.bidderNumber = bidderNumber;
	}

	public Integer getConsignorNumber() {
		return consignorNumber;
	}

	public void setConsignorNumber(Integer consignorNumber) {
		this.consignorNumber = consignorNumber;
	}

	/**
	 * 上年度不良资产处置规模
	 */
	public BigDecimal getLastYearDisposalAssetVolume(){
		return lastYearDisposalAssetVolume;
	}

	/**
	 * 上年度不良资产处置规模
	 */
	public void setLastYearDisposalAssetVolume(BigDecimal lastYearDisposalAssetVolume){
		this.lastYearDisposalAssetVolume = lastYearDisposalAssetVolume;
	}

	public BigDecimal getLastYearTotalDealVolume() {
		return lastYearTotalDealVolume;
	}

	public void setLastYearTotalDealVolume(BigDecimal lastYearTotalDealVolume) {
		this.lastYearTotalDealVolume = lastYearTotalDealVolume;
	}

	/**
	 * 是否有线上拍卖经验（0 否 1 是）
	 */
	public Boolean getHasOnlineAuctionExperience(){
		return hasOnlineAuctionExperience;
	}
	
	/**
	 * 是否有线上拍卖经验（0 否 1 是）
	 */
	public void setHasOnlineAuctionExperience(Boolean hasOnlineAuctionExperience){
		this.hasOnlineAuctionExperience = hasOnlineAuctionExperience;
	}
	
	/**
	 * 是否有长期合作服务商（0 否 1 是）
	 */
	public Boolean getHasFixedCooperateServiceProvider(){
		return hasFixedCooperateServiceProvider;
	}
	
	/**
	 * 是否有长期合作服务商（0 否 1 是）
	 */
	public void setHasFixedCooperateServiceProvider(Boolean hasFixedCooperateServiceProvider){
		this.hasFixedCooperateServiceProvider = hasFixedCooperateServiceProvider;
	}
	
	/**
	 * 其它业务需求
	 */
	public String getOtherDemand(){
		return otherDemand;
	}
	
	/**
	 * 其它业务需求
	 */
	public void setOtherDemand(String otherDemand){
		this.otherDemand = otherDemand;
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