
package com._360pai.core.model.withfudig;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月06日 15时50分14秒
 */
public class TWithfudigRequirementInvest implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 单号
	 */
	private String investNo;
	/**
	 * 推荐人id
	 */
	private Integer accountId;
	private Integer partyId;
	/**
	 * 关联的 t_withfudig_requirement 的id
	 */
	private Integer requirementId;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 状态 10 推介中 20已完成 30撮合成功
	 */
	private String investStatus;
	/**
	 * 备注 沟通内容
	 */
	private String remark;
	/**
	 * 是否删除（0 否 1 是）
	 */
	private Boolean isDelete;
	/**
	 * 生成时间
	 */
	private java.util.Date createdTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	/**
	 * 操作人id
	 */
	private String operatorId;

	public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

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
	 * 单号
	 */
	public String getInvestNo(){
		return investNo;
	}
	
	/**
	 * 单号
	 */
	public void setInvestNo(String investNo){
		this.investNo = investNo;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * 关联的 t_withfudig_requirement 的id
	 */
	public Integer getRequirementId(){
		return requirementId;
	}
	
	/**
	 * 关联的 t_withfudig_requirement 的id
	 */
	public void setRequirementId(Integer requirementId){
		this.requirementId = requirementId;
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
	 * 状态 10 推介中 20已完成 30撮合成功
	 */
	public String getInvestStatus(){
		return investStatus;
	}
	
	/**
	 * 状态 10 推介中 20已完成 30撮合成功
	 */
	public void setInvestStatus(String investStatus){
		this.investStatus = investStatus;
	}
	
	/**
	 * 备注 沟通内容
	 */
	public String getRemark(){
		return remark;
	}
	
	/**
	 * 备注 沟通内容
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	/**
	 * 是否删除（0 否 1 是）
	 */
	public Boolean getIsDelete(){
		return isDelete;
	}
	
	/**
	 * 是否删除（0 否 1 是）
	 */
	public void setIsDelete(Boolean isDelete){
		this.isDelete = isDelete;
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
