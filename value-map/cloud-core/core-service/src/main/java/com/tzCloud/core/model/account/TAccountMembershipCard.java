
package com.tzCloud.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月19日 13时33分42秒
 */
public class TAccountMembershipCard implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 账户id
	 */
	private Integer accountId;
	/**
	 * 类型（N 普通会员不写表， M 月会员 Y 年会员）
	 */
	private String type;
	/**
	 * 开始时间
	 */
	private java.util.Date startTime;
	/**
	 * 结束时间
	 */
	private java.util.Date endTime;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
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
	 * 账户id
	 */
	public Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 账户id
	 */
	public void setAccountId(Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 类型（N 普通会员不写表， M 月会员 Y 年会员）
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 类型（N 普通会员不写表， M 月会员 Y 年会员）
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 开始时间
	 */
	public java.util.Date getStartTime(){
		return startTime;
	}
	
	/**
	 * 开始时间
	 */
	public void setStartTime(java.util.Date startTime){
		this.startTime = startTime;
	}
	
	/**
	 * 结束时间
	 */
	public java.util.Date getEndTime(){
		return endTime;
	}
	
	/**
	 * 结束时间
	 */
	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public void setDeleteFlag(Boolean deleteFlag){
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
