
package com.winback.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
public class TSysRoleMenuMap implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 角色id
	 */
	private Integer roleId;
	/**
	 * 模块id
	 */
	private Integer menuId;
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
	 * 角色id
	 */
	public Integer getRoleId(){
		return roleId;
	}
	
	/**
	 * 角色id
	 */
	public void setRoleId(Integer roleId){
		this.roleId = roleId;
	}
	
	/**
	 * 模块id
	 */
	public Integer getMenuId(){
		return menuId;
	}
	
	/**
	 * 模块id
	 */
	public void setMenuId(Integer menuId){
		this.menuId = menuId;
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
