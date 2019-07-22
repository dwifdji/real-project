
package com.winback.core.condition._case;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月25日 10时50分54秒
 */
public class TCaseStatusUpdateRecordCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 案件id
	 */
	private String caseId;
	/**
	 * 案件主状态
	 */
	private String mainStatus;
	/**
	 * 案件子状态
	 */
	private String subStaus;
	/**
	 * 审核意见
	 */
	private String remark;
	/**
	 * 操作人id
	 */
	private Integer operatorId;
	/**
	 * 律师账户id
	 */
	private Integer lawyerAccountId;
	/**
	 * 删除标志
	 */
	private Integer deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

	private String recordType;

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	
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
	 * 案件主状态
	 */
	public String getMainStatus(){
		return mainStatus;
	}
	
	/**
	 * 案件主状态
	 */
	public void setMainStatus(String mainStatus){
		this.mainStatus = mainStatus;
	}
	
	/**
	 * 案件子状态
	 */
	public String getSubStaus(){
		return subStaus;
	}
	
	/**
	 * 案件子状态
	 */
	public void setSubStaus(String subStaus){
		this.subStaus = subStaus;
	}
	
	/**
	 * 审核意见
	 */
	public String getRemark(){
		return remark;
	}
	
	/**
	 * 审核意见
	 */
	public void setRemark(String remark){
		this.remark = remark;
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
	 * 律师账户id
	 */
	public Integer getLawyerAccountId(){
		return lawyerAccountId;
	}
	
	/**
	 * 律师账户id
	 */
	public void setLawyerAccountId(Integer lawyerAccountId){
		this.lawyerAccountId = lawyerAccountId;
	}
	
	/**
	 * 删除标志
	 */
	public Integer getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志
	 */
	public void setDeleteFlag(Integer deleteFlag){
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