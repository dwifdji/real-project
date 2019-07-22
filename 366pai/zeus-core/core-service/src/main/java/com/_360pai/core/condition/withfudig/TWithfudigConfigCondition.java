
package com._360pai.core.condition.withfudig;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月06日 18时32分30秒
 */
public class TWithfudigConfigCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 排序字段
	 */
	private Integer rank;
	/**
	 * 成交信息
	 */
	private String description;
	/**
	 * 备注
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
	 * 排序字段
	 */
	public Integer getRank(){
		return rank;
	}
	
	/**
	 * 排序字段
	 */
	public void setRank(Integer rank){
		this.rank = rank;
	}
	
	/**
	 * 成交信息
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * 成交信息
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * 备注
	 */
	public String getRemark(){
		return remark;
	}
	
	/**
	 * 备注
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