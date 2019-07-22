
package com.winback.core.model._case;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月28日 18时47分10秒
 */
public class TCaseStepRecord implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 案件id
	 */
	private Integer caseId;
	/**
	 * 案件类型
	 */
	private String caseType;
	/**
	 * 案件步骤id
	 */
	private String stepId;

	/**
	 * 案件步骤id
	 */
	private String branchStepId;
	/**
	 * 推送的信息
	 */
	private String pushMsg;
	/**
	 * 推送的人员id
	 */
	private Integer accountId;
	/**
	 * 下一组id
	 */
	private Integer nextId;
	/**
	 * 推送的状态
	 */
	private String pushStatus;
	/**
	 * 删除标志0 不删除 1删除
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
	 * 案件id
	 */
	public Integer getCaseId(){
		return caseId;
	}
	
	/**
	 * 案件id
	 */
	public void setCaseId(Integer caseId){
		this.caseId = caseId;
	}
	
	/**
	 * 案件类型
	 */
	public String getCaseType(){
		return caseType;
	}
	
	/**
	 * 案件类型
	 */
	public void setCaseType(String caseType){
		this.caseType = caseType;
	}
	
	/**
	 * 案件步骤id
	 */
	public String getStepId(){
		return stepId;
	}
	
	/**
	 * 案件步骤id
	 */
	public void setStepId(String stepId){
		this.stepId = stepId;
	}
	
	/**
	 * 推送的信息
	 */
	public String getPushMsg(){
		return pushMsg;
	}
	
	/**
	 * 推送的信息
	 */
	public void setPushMsg(String pushMsg){
		this.pushMsg = pushMsg;
	}
	
	/**
	 * 推送的人员id
	 */
	public Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 推送的人员id
	 */
	public void setAccountId(Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 下一组id
	 */
	public Integer getNextId(){
		return nextId;
	}
	
	/**
	 * 下一组id
	 */
	public void setNextId(Integer nextId){
		this.nextId = nextId;
	}
	
	/**
	 * 推送的状态
	 */
	public String getPushStatus(){
		return pushStatus;
	}
	
	/**
	 * 推送的状态
	 */
	public void setPushStatus(String pushStatus){
		this.pushStatus = pushStatus;
	}
	
	/**
	 * 删除标志0 不删除 1删除
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志0 不删除 1删除
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

	public String getBranchStepId() {
		return branchStepId;
	}

	public void setBranchStepId(String branchStepId) {
		this.branchStepId = branchStepId;
	}
}
