
package com.tzCloud.core.model.assistant;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年06月19日 15时49分48秒
 */
public class TConfig implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 配置类型 
	 */
	private String configType;
	/**
	 * 配置的值
	 */
	private String configValue;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	/**
	 * 操作人id
	 */
	private String operatorId;
	
	/**
	 * 主键id
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键id
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 配置类型 
	 */
	public String getConfigType(){
		return configType;
	}
	
	/**
	 * 配置类型 
	 */
	public void setConfigType(String configType){
		this.configType = configType;
	}
	
	/**
	 * 配置的值
	 */
	public String getConfigValue(){
		return configValue;
	}
	
	/**
	 * 配置的值
	 */
	public void setConfigValue(String configValue){
		this.configValue = configValue;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public void setDeleteFlag(Boolean deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
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
	public String getOperatorId(){
		return operatorId;
	}
	
	/**
	 * 操作人id
	 */
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}

}
