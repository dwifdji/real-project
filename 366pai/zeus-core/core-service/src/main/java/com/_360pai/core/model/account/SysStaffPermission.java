
package com._360pai.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年10月07日 20时50分28秒
 */
public class SysStaffPermission implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 员工id
	 */
	private Integer staffId;
	/**
	 * 权限id
	 */
	private Integer permissionId;
	/**
	 * 
	 */
	private java.util.Date createTime;
	/**
	 * 
	 */
	private java.util.Date updateTime;
	/**
	 * 是否删除（0 否1 是）
	 */
	private Boolean isDelete;
	
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
	 * 员工id
	 */
	public Integer getStaffId(){
		return staffId;
	}
	
	/**
	 * 员工id
	 */
	public void setStaffId(Integer staffId){
		this.staffId = staffId;
	}
	
	/**
	 * 权限id
	 */
	public Integer getPermissionId(){
		return permissionId;
	}
	
	/**
	 * 权限id
	 */
	public void setPermissionId(Integer permissionId){
		this.permissionId = permissionId;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	
	/**
	 * 
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}
	
	/**
	 * 是否删除（0 否1 是）
	 */
	public Boolean getIsDelete(){
		return isDelete;
	}
	
	/**
	 * 是否删除（0 否1 是）
	 */
	public void setIsDelete(Boolean isDelete){
		this.isDelete = isDelete;
	}

}
