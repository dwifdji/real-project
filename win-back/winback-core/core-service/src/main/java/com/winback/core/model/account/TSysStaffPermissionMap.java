
package com.winback.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
public class TSysStaffPermissionMap implements java.io.Serializable{

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