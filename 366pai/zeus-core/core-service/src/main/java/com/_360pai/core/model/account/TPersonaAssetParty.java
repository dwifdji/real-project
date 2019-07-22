
package com._360pai.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月29日 13时21分37秒
 */
public class TPersonaAssetParty implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * t_persona表id
	 */
	private Integer personaId;
	/**
	 * 持有资产类型（10 债权 20 物权 30 债权+物权）
	 */
	private String assetType;
	/**
	 * 资产体量
	 */
	private java.math.BigDecimal assetVolume;
	/**
	 * 资产分布区域，城市名，逗号分隔
	 */
	private String assetDistributionArea;
	/**
	 * 上年度不良资产处置规模
	 */
	private java.math.BigDecimal lastYearDisposalAssetVolume;
	/**
	 * 资产的平均价格区间（10 1000万以下 20 1000万到5000万 30 5000万到1亿 40 1亿以上）
	 */
	private String assetAveragePriceRange;
	/**
	 * 资产包来源（10 AMC 20 其他资管公司 30 当地政府 40 国有企业 50 银行）
	 */
	private String assetPackageSource;
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
	 * 持有资产类型（10 债权 20 物权 30 债权+物权）
	 */
	public String getAssetType(){
		return assetType;
	}
	
	/**
	 * 持有资产类型（10 债权 20 物权 30 债权+物权）
	 */
	public void setAssetType(String assetType){
		this.assetType = assetType;
	}
	
	/**
	 * 资产体量
	 */
	public java.math.BigDecimal getAssetVolume(){
		return assetVolume;
	}
	
	/**
	 * 资产体量
	 */
	public void setAssetVolume(java.math.BigDecimal assetVolume){
		this.assetVolume = assetVolume;
	}
	
	/**
	 * 资产分布区域，城市名，逗号分隔
	 */
	public String getAssetDistributionArea(){
		return assetDistributionArea;
	}
	
	/**
	 * 资产分布区域，城市名，逗号分隔
	 */
	public void setAssetDistributionArea(String assetDistributionArea){
		this.assetDistributionArea = assetDistributionArea;
	}
	
	/**
	 * 上年度不良资产处置规模
	 */
	public java.math.BigDecimal getLastYearDisposalAssetVolume(){
		return lastYearDisposalAssetVolume;
	}
	
	/**
	 * 上年度不良资产处置规模
	 */
	public void setLastYearDisposalAssetVolume(java.math.BigDecimal lastYearDisposalAssetVolume){
		this.lastYearDisposalAssetVolume = lastYearDisposalAssetVolume;
	}
	
	/**
	 * 资产的平均价格区间（10 1000万以下 20 1000万到5000万 30 5000万到1亿 40 1亿以上）
	 */
	public String getAssetAveragePriceRange(){
		return assetAveragePriceRange;
	}
	
	/**
	 * 资产的平均价格区间（10 1000万以下 20 1000万到5000万 30 5000万到1亿 40 1亿以上）
	 */
	public void setAssetAveragePriceRange(String assetAveragePriceRange){
		this.assetAveragePriceRange = assetAveragePriceRange;
	}
	
	/**
	 * 资产包来源（10 AMC 20 其他资管公司 30 当地政府 40 国有企业）
	 */
	public String getAssetPackageSource(){
		return assetPackageSource;
	}
	
	/**
	 * 资产包来源（10 AMC 20 其他资管公司 30 当地政府 40 国有企业）
	 */
	public void setAssetPackageSource(String assetPackageSource){
		this.assetPackageSource = assetPackageSource;
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
