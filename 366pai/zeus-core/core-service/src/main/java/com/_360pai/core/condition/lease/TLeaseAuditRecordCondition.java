
package com._360pai.core.condition.lease;


import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月26日 13时29分17秒
 */
public class TLeaseAuditRecordCondition implements DaoCondition {

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 审核人id
	 */
	private java.lang.Integer accountId;
	/**
	 * 审核的标id
	 */
	private java.lang.Integer activityId;
	/**
	 * 审核状态
	 */
	private java.lang.String status;
	/**
	 * 审核的步骤
	 */
	private java.lang.String steps;
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
	 * 审核人id
	 */
	public java.lang.Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 审核人id
	 */
	public void setAccountId(java.lang.Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 审核的标id
	 */
	public java.lang.Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 审核的标id
	 */
	public void setActivityId(java.lang.Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 审核状态
	 */
	public java.lang.String getStatus(){
		return status;
	}
	
	/**
	 * 审核状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	
	/**
	 * 审核的步骤
	 */
	public java.lang.String getSteps(){
		return steps;
	}
	
	/**
	 * 审核的步骤
	 */
	public void setSteps(java.lang.String steps){
		this.steps = steps;
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