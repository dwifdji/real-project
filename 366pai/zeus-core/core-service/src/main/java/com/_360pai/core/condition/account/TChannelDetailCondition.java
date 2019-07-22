
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
public class TChannelDetailCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 下线用户类型( USER、COMPANY、AGENCY)
	 */
	private java.lang.String childType;
	/**
	 * 下线账户Id
	 */
	private java.lang.Integer childId;
	/**
	 * 上线用户类型( USER、COMPANY、AGENCY)
	 */
	private java.lang.String parentType;
	/**
	 * 上线账户Id
	 */
	private java.lang.Integer parentId;
	/**
	 * 分佣比例
	 */
	private java.math.BigDecimal commissionPercent;
	/**
	 * 操作人Id
	 */
	private java.lang.Integer operatorId;
	/**
	 * 删除标志(0:未删除,1:已删除)
	 */
	private java.lang.Integer isDel;
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
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 主键
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 下线用户类型( USER、COMPANY、AGENCY)
	 */
	public java.lang.String getChildType(){
		return childType;
	}
	
	/**
	 * 下线用户类型( USER、COMPANY、AGENCY)
	 */
	public void setChildType(java.lang.String childType){
		this.childType = childType;
	}
	
	/**
	 * 下线账户Id
	 */
	public java.lang.Integer getChildId(){
		return childId;
	}
	
	/**
	 * 下线账户Id
	 */
	public void setChildId(java.lang.Integer childId){
		this.childId = childId;
	}
	
	/**
	 * 上线用户类型( USER、COMPANY、AGENCY)
	 */
	public java.lang.String getParentType(){
		return parentType;
	}
	
	/**
	 * 上线用户类型( USER、COMPANY、AGENCY)
	 */
	public void setParentType(java.lang.String parentType){
		this.parentType = parentType;
	}
	
	/**
	 * 上线账户Id
	 */
	public java.lang.Integer getParentId(){
		return parentId;
	}
	
	/**
	 * 上线账户Id
	 */
	public void setParentId(java.lang.Integer parentId){
		this.parentId = parentId;
	}
	
	/**
	 * 分佣比例
	 */
	public java.math.BigDecimal getCommissionPercent(){
		return commissionPercent;
	}
	
	/**
	 * 分佣比例
	 */
	public void setCommissionPercent(java.math.BigDecimal commissionPercent){
		this.commissionPercent = commissionPercent;
	}
	
	/**
	 * 操作人Id
	 */
	public java.lang.Integer getOperatorId(){
		return operatorId;
	}
	
	/**
	 * 操作人Id
	 */
	public void setOperatorId(java.lang.Integer operatorId){
		this.operatorId = operatorId;
	}
	
	/**
	 * 删除标志(0:未删除,1:已删除)
	 */
	public java.lang.Integer getIsDel(){
		return isDel;
	}
	
	/**
	 * 删除标志(0:未删除,1:已删除)
	 */
	public void setIsDel(java.lang.Integer isDel){
		this.isDel = isDel;
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