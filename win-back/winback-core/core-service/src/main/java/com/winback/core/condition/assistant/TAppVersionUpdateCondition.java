
package com.winback.core.condition.assistant;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年05月06日 18时55分44秒
 */
public class TAppVersionUpdateCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 设备类型 Android 0/iOS 1
	 */
	private String deviceType;
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 弹窗提示标志（0 否 1 是）
	 */
	private Boolean alertFlag;
	/**
	 * 强制更新标志（0 否 1 是）
	 */
	private Boolean forceFlag;
	/**
	 * 描述
	 */
	private String description;
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
	 * 主键
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 设备类型 Android 0/iOS 1
	 */
	public String getDeviceType(){
		return deviceType;
	}
	
	/**
	 * 设备类型 Android 0/iOS 1
	 */
	public void setDeviceType(String deviceType){
		this.deviceType = deviceType;
	}
	
	/**
	 * 版本
	 */
	public String getVersion(){
		return version;
	}
	
	/**
	 * 版本
	 */
	public void setVersion(String version){
		this.version = version;
	}
	
	/**
	 * 弹窗提示标志（0 否 1 是）
	 */
	public Boolean getAlertFlag(){
		return alertFlag;
	}
	
	/**
	 * 弹窗提示标志（0 否 1 是）
	 */
	public void setAlertFlag(Boolean alertFlag){
		this.alertFlag = alertFlag;
	}
	
	/**
	 * 强制更新标志（0 否 1 是）
	 */
	public Boolean getForceFlag(){
		return forceFlag;
	}
	
	/**
	 * 强制更新标志（0 否 1 是）
	 */
	public void setForceFlag(Boolean forceFlag){
		this.forceFlag = forceFlag;
	}
	
	/**
	 * 描述
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * 描述
	 */
	public void setDescription(String description){
		this.description = description;
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

}