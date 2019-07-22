
package com.winback.core.condition.account;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月30日 14时39分52秒
 */
public class TSysPermissionCondition implements DaoCondition{

	/**
	 * 自定id,主要供前端展示权限列表分类排序使用.
	 */
	private Integer id;
	/**
	 * 归属菜单,前端判断并展示菜单使用,
	 */
	private String buttonCode;
	/**
	 * 菜单的中文释义
	 */
	private String buttonName;
	/**
	 * 排序号
	 */
	private Integer orderNum;
	/**
	 * 权限的代码/通配符,对应代码中@RequiresPermissions 的value
	 */
	private String permissionCode;
	/**
	 * 本权限的中文释义
	 */
	private String permissionName;
	/**
	 * 是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选
	 */
	private Boolean requiredPermission;
	/**
	 * 按钮类型：0普通，1特殊
	 */
	private String permissionType;
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
	 * 自定id,主要供前端展示权限列表分类排序使用.
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 自定id,主要供前端展示权限列表分类排序使用.
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 归属菜单,前端判断并展示菜单使用,
	 */
	public String getButtonCode(){
		return buttonCode;
	}
	
	/**
	 * 归属菜单,前端判断并展示菜单使用,
	 */
	public void setButtonCode(String buttonCode){
		this.buttonCode = buttonCode;
	}
	
	/**
	 * 菜单的中文释义
	 */
	public String getButtonName(){
		return buttonName;
	}
	
	/**
	 * 菜单的中文释义
	 */
	public void setButtonName(String buttonName){
		this.buttonName = buttonName;
	}
	
	/**
	 * 排序号
	 */
	public Integer getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 排序号
	 */
	public void setOrderNum(Integer orderNum){
		this.orderNum = orderNum;
	}
	
	/**
	 * 权限的代码/通配符,对应代码中@RequiresPermissions 的value
	 */
	public String getPermissionCode(){
		return permissionCode;
	}
	
	/**
	 * 权限的代码/通配符,对应代码中@RequiresPermissions 的value
	 */
	public void setPermissionCode(String permissionCode){
		this.permissionCode = permissionCode;
	}
	
	/**
	 * 本权限的中文释义
	 */
	public String getPermissionName(){
		return permissionName;
	}
	
	/**
	 * 本权限的中文释义
	 */
	public void setPermissionName(String permissionName){
		this.permissionName = permissionName;
	}
	
	/**
	 * 是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选
	 */
	public Boolean getRequiredPermission(){
		return requiredPermission;
	}
	
	/**
	 * 是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选
	 */
	public void setRequiredPermission(Boolean requiredPermission){
		this.requiredPermission = requiredPermission;
	}
	
	/**
	 * 按钮类型：0普通，1特殊
	 */
	public String getPermissionType(){
		return permissionType;
	}
	
	/**
	 * 按钮类型：0普通，1特殊
	 */
	public void setPermissionType(String permissionType){
		this.permissionType = permissionType;
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