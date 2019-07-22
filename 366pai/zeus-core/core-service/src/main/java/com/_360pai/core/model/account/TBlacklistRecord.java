
package com._360pai.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
public class TBlacklistRecord implements java.io.Serializable{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 账户类型(USER、COMPANY)
	 */
	private java.lang.String accountType;
	/**
	 * 账户Id user_id or company_id
	 */
	private java.lang.Integer accountId;
	/**
	 * 动作(RELEASE、LOCK)
	 */
	private java.lang.String action;
	/**
	 * 原因
	 */
	private java.lang.String reason;
	/**
	 * 操作人
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
	 * 账户类型(USER、COMPANY)
	 */
	public java.lang.String getAccountType(){
		return accountType;
	}
	
	/**
	 * 账户类型(USER、COMPANY)
	 */
	public void setAccountType(java.lang.String accountType){
		this.accountType = accountType;
	}
	
	/**
	 * 账户Id user_id or company_id
	 */
	public java.lang.Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 账户Id user_id or company_id
	 */
	public void setAccountId(java.lang.Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 动作(RELEASE、LOCK)
	 */
	public java.lang.String getAction(){
		return action;
	}
	
	/**
	 * 动作(RELEASE、LOCK)
	 */
	public void setAction(java.lang.String action){
		this.action = action;
	}
	
	/**
	 * 原因
	 */
	public java.lang.String getReason(){
		return reason;
	}
	
	/**
	 * 原因
	 */
	public void setReason(java.lang.String reason){
		this.reason = reason;
	}
	
	/**
	 * 操作人
	 */
	public java.lang.Integer getOperatorId(){
		return operatorId;
	}
	
	/**
	 * 操作人
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
