
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月17日 10时09分27秒
 */
public class TServiceFollowCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 
	 */
	private java.lang.Integer accountId;
	private java.lang.Integer partyId;
	/**
	 * 
	 */
	private java.lang.String  relativeType;
	/**
	 * 
	 */
	private java.lang.Integer relativeId;
	/**
	 * 
	 */
	private java.util.Date    createdTime;
	/**
	 * 删除标识 0未删除 1删除
	 */
	private java.lang.Boolean delFlag;
	
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
	 * 
	 */
	public java.lang.Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 
	 */
	public void setAccountId(java.lang.Integer accountId){
		this.accountId = accountId;
	}

	public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

	/**
	 * 
	 */
	public java.lang.String getRelativeType(){
		return relativeType;
	}
	
	/**
	 * 
	 */
	public void setRelativeType(java.lang.String relativeType){
		this.relativeType = relativeType;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getRelativeId(){
		return relativeId;
	}
	
	/**
	 * 
	 */
	public void setRelativeId(java.lang.Integer relativeId){
		this.relativeId = relativeId;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreatedTime(){
		return createdTime;
	}
	
	/**
	 * 
	 */
	public void setCreatedTime(java.util.Date createdTime){
		this.createdTime = createdTime;
	}
	
	/**
	 * 删除标识 0未删除 1删除
	 */
	public java.lang.Boolean getDelFlag(){
		return delFlag;
	}
	
	/**
	 * 删除标识 0未删除 1删除
	 */
	public void setDelFlag(java.lang.Boolean delFlag){
		this.delFlag = delFlag;
	}

}