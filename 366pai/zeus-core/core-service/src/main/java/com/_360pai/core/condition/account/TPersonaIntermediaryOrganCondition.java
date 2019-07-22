
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月29日 13时21分37秒
 */
public class TPersonaIntermediaryOrganCondition implements DaoCondition {

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
	 * 擅长寻找哪种资产类型客户，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	private String assetPropertyType;
	/**
	 * 客户需求类型（10 债权 20 物权 30 债权+物权）
	 */
	private String assetType;
	/**
	 * 是否有成功案列（0 否 1 是）
	 */
	private Boolean hasSuccessfulCase;
	/**
	 * 是否愿意加盟店铺（0 否 1 是）
	 */
	private Boolean isGoingToJoinShop;
	/**
	 * 其他需求
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
	 * 擅长寻找哪种资产类型客户，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	public String getAssetPropertyType(){
		return assetPropertyType;
	}
	
	/**
	 * 擅长寻找哪种资产类型客户，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
	 */
	public void setAssetPropertyType(String assetPropertyType){
		this.assetPropertyType = assetPropertyType;
	}
	
	/**
	 * 客户需求类型（10 债权 20 物权 30 债权+物权）
	 */
	public String getAssetType(){
		return assetType;
	}
	
	/**
	 * 客户需求类型（10 债权 20 物权 30 债权+物权）
	 */
	public void setAssetType(String assetType){
		this.assetType = assetType;
	}
	
	/**
	 * 是否有成功案列（0 否 1 是）
	 */
	public Boolean getHasSuccessfulCase(){
		return hasSuccessfulCase;
	}
	
	/**
	 * 是否有成功案列（0 否 1 是）
	 */
	public void setHasSuccessfulCase(Boolean hasSuccessfulCase){
		this.hasSuccessfulCase = hasSuccessfulCase;
	}
	
	/**
	 * 是否愿意加盟店铺（0 否 1 是）
	 */
	public Boolean getIsGoingToJoinShop(){
		return isGoingToJoinShop;
	}
	
	/**
	 * 是否愿意加盟店铺（0 否 1 是）
	 */
	public void setIsGoingToJoinShop(Boolean isGoingToJoinShop){
		this.isGoingToJoinShop = isGoingToJoinShop;
	}
	
	/**
	 * 其他需求
	 */
	public String getOtherDemand(){
		return otherDemand;
	}
	
	/**
	 * 其他需求
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