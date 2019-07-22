
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月14日 09时48分08秒
 */
public class TAccountViewRecordCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 账号id
	 */
	private Integer accountId;
	/**
	 * 角色id
	 */
	private Integer partyId;
	/**
	 * 活动id
	 */
	private Integer activityId;
	/**
	 * 活动类型auction拍卖enrolling招商disposal处置
	 */
	private String type;
	/**
	 * 创建时间
	 */
	private java.util.Date createAt;
	/**
	 * 修改时间
	 */
	private java.util.Date updateAt;
	
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
	 * 账号id
	 */
	public Integer getAccountId(){
		return accountId;
	}

	public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

	/**
	 * 账号id
	 */
	public void setAccountId(Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 活动id
	 */
	public Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 活动id
	 */
	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 活动类型auction拍卖enrolling招商disposal处置
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 活动类型auction拍卖enrolling招商disposal处置
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateAt(){
		return createAt;
	}
	
	/**
	 * 创建时间
	 */
	public void setCreateAt(java.util.Date createAt){
		this.createAt = createAt;
	}
	
	/**
	 * 修改时间
	 */
	public java.util.Date getUpdateAt(){
		return updateAt;
	}
	
	/**
	 * 修改时间
	 */
	public void setUpdateAt(java.util.Date updateAt){
		this.updateAt = updateAt;
	}

}