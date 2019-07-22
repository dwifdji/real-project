
package com.tzCloud.core.model.order;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年06月19日 15时47分59秒
 */
public class TServiceConfig implements java.io.Serializable{

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
	 * 生成时间
	 */
	private java.util.Date createdTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	
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

}
