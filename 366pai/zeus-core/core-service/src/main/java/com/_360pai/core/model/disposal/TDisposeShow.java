
package com._360pai.core.model.disposal;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月08日 15时58分33秒
 */
public class TDisposeShow implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 服务商id
	 */
	private Integer providerId;
	/**
	 * 城市id
	 */
	private Integer cityId;
	/**
	 * 活动Id
	 */
	private Integer activityId;
	/**
	 * 删除标识  0:未删除 1:删除
	 */
	private Boolean delFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
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
	 * 服务商id
	 */
	public Integer getProviderId(){
		return providerId;
	}
	
	/**
	 * 服务商id
	 */
	public void setProviderId(Integer providerId){
		this.providerId = providerId;
	}
	
	/**
	 * 城市id
	 */
	public Integer getCityId(){
		return cityId;
	}
	
	/**
	 * 城市id
	 */
	public void setCityId(Integer cityId){
		this.cityId = cityId;
	}
	
	/**
	 * 活动Id
	 */
	public Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 活动Id
	 */
	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 删除标识  0:未删除 1:删除
	 */
	public Boolean getDelFlag(){
		return delFlag;
	}
	
	/**
	 * 删除标识  0:未删除 1:删除
	 */
	public void setDelFlag(Boolean delFlag){
		this.delFlag = delFlag;
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
	 * 修改时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 修改时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}
