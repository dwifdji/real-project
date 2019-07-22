
package com.winback.core.model._case;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年05月06日 15时34分52秒
 */
public class TCaseProjectManagerMap implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 案件id
	 */
	private String caseId;
	/**
	 * 项目经理账户id
	 */
	private Integer accountId;
	/**
	 * 操作人id
	 */
	private Integer operatorId;
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
	 * 案件id
	 */
	public String getCaseId(){
		return caseId;
	}
	
	/**
	 * 案件id
	 */
	public void setCaseId(String caseId){
		this.caseId = caseId;
	}
	
	/**
	 * 项目经理账户id
	 */
	public Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 项目经理账户id
	 */
	public void setAccountId(Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 操作人id
	 */
	public Integer getOperatorId(){
		return operatorId;
	}
	
	/**
	 * 操作人id
	 */
	public void setOperatorId(Integer operatorId){
		this.operatorId = operatorId;
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
