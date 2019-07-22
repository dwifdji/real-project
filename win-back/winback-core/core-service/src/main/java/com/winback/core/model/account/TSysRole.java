
package com.winback.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
public class TSysRole implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 状态（0：禁用，1：启用）
	 */
	private Boolean status;
	/**
	 * 角色描述
	 */
	private String description;
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
	 * 角色名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 角色名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 状态（0：禁用，1：启用）
	 */
	public Boolean getStatus(){
		return status;
	}
	
	/**
	 * 状态（0：禁用，1：启用）
	 */
	public void setStatus(Boolean status){
		this.status = status;
	}
	
	/**
	 * 角色描述
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * 角色描述
	 */
	public void setDescription(String description){
		this.description = description;
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
