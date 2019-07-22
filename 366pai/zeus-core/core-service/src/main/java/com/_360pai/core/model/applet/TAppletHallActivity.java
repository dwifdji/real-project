
package com._360pai.core.model.applet;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年02月26日 14时42分55秒
 */
public class TAppletHallActivity implements java.io.Serializable{

	/**
	 * 自增主键
	 */
	private Integer id;
	/**
	 * 活动类型 0 拍卖 1 招商
	 */
	private Integer type;
	/**
	 * 活动ID
	 */
	private Integer activityId;
	/**
	 * 排序编号
	 */
	private Integer orderNum;
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
	 * 自增主键
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 自增主键
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 活动类型 0 拍卖 1 招商
	 */
	public Integer getType(){
		return type;
	}
	
	/**
	 * 活动类型 0 拍卖 1 招商
	 */
	public void setType(Integer type){
		this.type = type;
	}
	
	/**
	 * 活动ID
	 */
	public Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 活动ID
	 */
	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 排序编号
	 */
	public Integer getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 排序编号
	 */
	public void setOrderNum(Integer orderNum){
		this.orderNum = orderNum;
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
