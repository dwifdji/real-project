
package com.tzCloud.core.condition.legalEngine;

import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月24日 15时06分59秒
 */
public class TCollectCondition implements DaoCondition{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * t_account 表 id
	 */
	private Integer accountId;
	/**
	 * 收藏类型： case: 案例 lawyer: 律师
	 */
	private String collectType;
	/**
	 * key
	 */
	private String collectKey;
	/**
	 * 关注时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	/**
	 * 删除标识
	 */
	private String delFlag;
	
	/**
	 * 主键id
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键id
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * t_account 表 id
	 */
	public Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * t_account 表 id
	 */
	public void setAccountId(Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 收藏类型： case: 案例 lawyer: 律师
	 */
	public String getCollectType(){
		return collectType;
	}
	
	/**
	 * 收藏类型： case: 案例 lawyer: 律师
	 */
	public void setCollectType(String collectType){
		this.collectType = collectType;
	}
	
	/**
	 * key
	 */
	public String getCollectKey(){
		return collectKey;
	}
	
	/**
	 * key
	 */
	public void setCollectKey(String collectKey){
		this.collectKey = collectKey;
	}
	
	/**
	 * 关注时间
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 关注时间
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
	
	/**
	 * 删除标识
	 */
	public String getDelFlag(){
		return delFlag;
	}
	
	/**
	 * 删除标识
	 */
	public void setDelFlag(String delFlag){
		this.delFlag = delFlag;
	}

}