
package com._360pai.core.condition.lease;


import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月24日 12时55分32秒
 */
public class TLeaseApplyCondition implements DaoCondition {

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 申请人id
	 */
	private java.lang.Integer partId;
	/**
	 * 申请标的id
	 */
	private java.lang.Integer activityId;
	/**
	 * 申请状态
	 */
	private java.lang.String status;
	/**
	 * 申请的证明资料
	 */
	private java.lang.String proveUrl;
	/**
	 * 审核拒绝原因
	 */
	private java.lang.String reason;
	/**
	 * 
	 */
	private java.util.Date createTime;
	/**
	 * 
	 */
	private java.util.Date updateTime;
	/**
	 * 是否删除（0 否1 是）
	 */
	private java.lang.Boolean deleteFlag;
	
	/**
	 * 
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 申请人id
	 */
	public java.lang.Integer getPartId(){
		return partId;
	}
	
	/**
	 * 申请人id
	 */
	public void setPartId(java.lang.Integer partId){
		this.partId = partId;
	}
	
	/**
	 * 申请标的id
	 */
	public java.lang.Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 申请标的id
	 */
	public void setActivityId(java.lang.Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 申请状态
	 */
	public java.lang.String getStatus(){
		return status;
	}
	
	/**
	 * 申请状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	
	/**
	 * 申请的证明资料
	 */
	public java.lang.String getProveUrl(){
		return proveUrl;
	}
	
	/**
	 * 申请的证明资料
	 */
	public void setProveUrl(java.lang.String proveUrl){
		this.proveUrl = proveUrl;
	}
	
	/**
	 * 审核拒绝原因
	 */
	public java.lang.String getReason(){
		return reason;
	}
	
	/**
	 * 审核拒绝原因
	 */
	public void setReason(java.lang.String reason){
		this.reason = reason;
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
	
	/**
	 * 是否删除（0 否1 是）
	 */
	public java.lang.Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 是否删除（0 否1 是）
	 */
	public void setDeleteFlag(java.lang.Boolean deleteFlag){
		this.deleteFlag = deleteFlag;
	}

}