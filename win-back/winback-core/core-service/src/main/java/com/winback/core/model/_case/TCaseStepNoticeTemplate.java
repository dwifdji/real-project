
package com.winback.core.model._case;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月16日 16时13分05秒
 */
public class TCaseStepNoticeTemplate implements java.io.Serializable{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 案件大类步骤名称
	 */
	private java.lang.String name;
	/**
	 * 步骤id
	 */
	private java.lang.Integer stepId;

	/**
	 * 步骤id
	 */
	private java.lang.Integer branchStepId;
	/**
	 * 通知类型 1 app 2 短信 3 邮件 4 app、邮件 5 短信、邮件
	 */
	private java.lang.Integer type;
	/**
	 * 顺序
	 */
	private java.lang.Integer orderDesc;
	/**
	 * 删除标志0 不删除 1删除
	 */
	private java.lang.Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

	private String nameDesc;

	public String getNameDesc() {
		return nameDesc;
	}

	public void setNameDesc(String nameDesc) {
		this.nameDesc = nameDesc;
	}

	public Integer getBranchStepId() {
		return branchStepId;
	}

	public void setBranchStepId(Integer branchStepId) {
		this.branchStepId = branchStepId;
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
	 * 案件大类步骤名称
	 */
	public java.lang.String getName(){
		return name;
	}
	
	/**
	 * 案件大类步骤名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	
	/**
	 * 步骤id
	 */
	public java.lang.Integer getStepId(){
		return stepId;
	}
	
	/**
	 * 步骤id
	 */
	public void setStepId(java.lang.Integer stepId){
		this.stepId = stepId;
	}
	
	/**
	 * 通知类型 1 app 2 短信 3 邮件 4 app、邮件 5 短信、邮件
	 */
	public java.lang.Integer getType(){
		return type;
	}
	
	/**
	 * 通知类型 1 app 2 短信 3 邮件 4 app、邮件 5 短信、邮件
	 */
	public void setType(java.lang.Integer type){
		this.type = type;
	}
	
	/**
	 * 顺序
	 */
	public java.lang.Integer getOrderDesc(){
		return orderDesc;
	}
	
	/**
	 * 顺序
	 */
	public void setOrderDesc(java.lang.Integer orderDesc){
		this.orderDesc = orderDesc;
	}
	
	/**
	 * 删除标志0 不删除 1删除
	 */
	public java.lang.Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志0 不删除 1删除
	 */
	public void setDeleteFlag(java.lang.Boolean deleteFlag){
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
