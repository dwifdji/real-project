
package com.winback.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月03日 14时37分27秒
 */
public class TAccountContacts implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer accountId;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 电话
	 */
	private String telephone;
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
	 * 
	 */
	public Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 
	 */
	public void setAccountId(Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 姓名
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 姓名
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 电话
	 */
	public String getTelephone(){
		return telephone;
	}
	
	/**
	 * 电话
	 */
	public void setTelephone(String telephone){
		this.telephone = telephone;
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
