
package com.winback.core.model._case;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月25日 10时50分54秒
 */
public class TCaseLawyerOrder implements java.io.Serializable{

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 案件id
	 */
	private String caseId;
	/**
	 * 承接律师账户id
	 */
	private Integer lawyerAccountId;
	/**
	 * 状态
	 */
	private String orderStatus;
	/**
	 * 操作人id
	 */
	private Integer operatorId;
	/**
	 * 删除标志,1:删除
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
	
	/**
	 * 主键
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * 主键
	 */
	public void setId(String id){
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
	 * 承接律师账户id
	 */
	public Integer getLawyerAccountId(){
		return lawyerAccountId;
	}
	
	/**
	 * 承接律师账户id
	 */
	public void setLawyerAccountId(Integer lawyerAccountId){
		this.lawyerAccountId = lawyerAccountId;
	}
	
	/**
	 * 状态
	 */
	public String getOrderStatus(){
		return orderStatus;
	}
	
	/**
	 * 状态
	 */
	public void setOrderStatus(String orderStatus){
		this.orderStatus = orderStatus;
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
	 * 删除标志,1:删除
	 */
	public Integer getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志,1:删除
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
