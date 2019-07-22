
package com.winback.core.model._case;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月16日 16时13分05秒
 */
public class TCaseStepBranch implements java.io.Serializable{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 案件大类步骤名称
	 */
	private java.lang.String name;
	/**
	 * 步骤描述
	 */
	private java.lang.String nameDesc;
	/**
	 * 步骤id
	 */
	private java.lang.Integer stepId;
	/**
	 * 下一组id
	 */
	private java.lang.Integer nextId;
	/**
	 * 1 诉讼步骤 2 执行步骤
	 */
	private java.lang.String type;
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
	 * 步骤描述
	 */
	public java.lang.String getNameDesc(){
		return nameDesc;
	}
	
	/**
	 * 步骤描述
	 */
	public void setNameDesc(java.lang.String nameDesc){
		this.nameDesc = nameDesc;
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
	 * 下一组id
	 */
	public java.lang.Integer getNextId(){
		return nextId;
	}
	
	/**
	 * 下一组id
	 */
	public void setNextId(java.lang.Integer nextId){
		this.nextId = nextId;
	}
	
	/**
	 * 1 诉讼步骤 2 执行步骤
	 */
	public java.lang.String getType(){
		return type;
	}
	
	/**
	 * 1 诉讼步骤 2 执行步骤
	 */
	public void setType(java.lang.String type){
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