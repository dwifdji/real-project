
package com._360pai.core.model.lease;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月23日 10时11分08秒
 */
public class TLeaseStaff implements java.io.Serializable{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 公司id
	 */
	private java.lang.Integer partId;


	/**
	 * 公司id
	 */
	private java.lang.Integer comId;
	/**
	 * 员工名称
	 */
	private java.lang.String name;

	/**
	 * 员工名称
	 */
	private java.lang.String fadadaId;

	/**
	 * 员工手机号
	 */
	private java.lang.String mobile;
	/**
	 * 账号id
	 */
	private java.lang.Integer accountId;
	/**
	 * 经办人权限
	 */
	private java.lang.Boolean agentFlag;
	/**
	 * 初审权限
	 */
	private java.lang.Boolean trialFlag;
	/**
	 * 终审权限标志
	 */
	private java.lang.Boolean finalFlag;
	/**
	 * 禁用标志
	 */
	private java.lang.Boolean forbidFlag;
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
	private java.lang.Boolean isDelete;


	public Integer getComId() {
		return comId;
	}

	public void setComId(Integer comId) {
		this.comId = comId;
	}

	public String getFadadaId() {
		return fadadaId;
	}

	public void setFadadaId(String fadadaId) {
		this.fadadaId = fadadaId;
	}

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
	 * 公司id
	 */
	public java.lang.Integer getPartId(){
		return partId;
	}
	
	/**
	 * 公司id
	 */
	public void setPartId(java.lang.Integer partId){
		this.partId = partId;
	}
	
	/**
	 * 员工名称
	 */
	public java.lang.String getName(){
		return name;
	}
	
	/**
	 * 员工名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	
	/**
	 * 员工手机号
	 */
	public java.lang.String getMobile(){
		return mobile;
	}
	
	/**
	 * 员工手机号
	 */
	public void setMobile(java.lang.String mobile){
		this.mobile = mobile;
	}
	
	/**
	 * 账号id
	 */
	public java.lang.Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 账号id
	 */
	public void setAccountId(java.lang.Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 经办人权限
	 */
	public java.lang.Boolean getAgentFlag(){
		return agentFlag;
	}
	
	/**
	 * 经办人权限
	 */
	public void setAgentFlag(java.lang.Boolean agentFlag){
		this.agentFlag = agentFlag;
	}
	
	/**
	 * 初审权限
	 */
	public java.lang.Boolean getTrialFlag(){
		return trialFlag;
	}
	
	/**
	 * 初审权限
	 */
	public void setTrialFlag(java.lang.Boolean trialFlag){
		this.trialFlag = trialFlag;
	}
	
	/**
	 * 终审权限标志
	 */
	public java.lang.Boolean getFinalFlag(){
		return finalFlag;
	}
	
	/**
	 * 终审权限标志
	 */
	public void setFinalFlag(java.lang.Boolean finalFlag){
		this.finalFlag = finalFlag;
	}
	
	/**
	 * 禁用标志
	 */
	public java.lang.Boolean getForbidFlag(){
		return forbidFlag;
	}
	
	/**
	 * 禁用标志
	 */
	public void setForbidFlag(java.lang.Boolean forbidFlag){
		this.forbidFlag = forbidFlag;
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
	public java.lang.Boolean getIsDelete(){
		return isDelete;
	}
	
	/**
	 * 是否删除（0 否1 是）
	 */
	public void setIsDelete(java.lang.Boolean isDelete){
		this.isDelete = isDelete;
	}

}
