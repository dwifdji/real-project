
package com._360pai.core.model.assistant;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月06日 18时53分50秒
 */
public class TServiceConfig implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private java.lang.Integer id;
	/**
	 * 配置类型 10 总条数 11 总金额 20 资产大买办需求单价格 21 配资乐需求单价格
	 */
	private java.lang.String configType;
	/**
	 * 配置的值
	 */
	private java.lang.String configValue;
	/**
	 * 生成时间
	 */
	private java.util.Date createdTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	/**
	 * 操作人id
	 */
	private java.lang.String operatorId;
	
	/**
	 * 主键id
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 主键id
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 配置类型 10 总条数 11 总金额 20 资产大买办需求单价格 21 配资乐需求单价格
	 */
	public java.lang.String getConfigType(){
		return configType;
	}
	
	/**
	 * 配置类型 10 总条数 11 总金额 20 资产大买办需求单价格 21 配资乐需求单价格
	 */
	public void setConfigType(java.lang.String configType){
		this.configType = configType;
	}
	
	/**
	 * 配置的值
	 */
	public java.lang.String getConfigValue(){
		return configValue;
	}
	
	/**
	 * 配置的值
	 */
	public void setConfigValue(java.lang.String configValue){
		this.configValue = configValue;
	}
	
	/**
	 * 生成时间
	 */
	public java.util.Date getCreatedTime(){
		return createdTime;
	}
	
	/**
	 * 生成时间
	 */
	public void setCreatedTime(java.util.Date createdTime){
		this.createdTime = createdTime;
	}
	
	/**
	 * 更新时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 更新时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}
	
	/**
	 * 操作人id
	 */
	public java.lang.String getOperatorId(){
		return operatorId;
	}
	
	/**
	 * 操作人id
	 */
	public void setOperatorId(java.lang.String operatorId){
		this.operatorId = operatorId;
	}

}
