
package com.tzCloud.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月19日 13时33分42秒
 */
public class TAccountMembershipPermission implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 会员类型（N 普通会员 M 月会员 Y 年会员）
	 */
	private String type;
	/**
	 * 权限类型（1 检索）
	 */
	private String permissionType;
	/**
	 * 权限名称
	 */
	private String permissionName;
	/**
	 * 权限code
	 */
	private String permissionCode;
	/**
	 * 日限制
	 */
	private Integer dayLimit;
	/**
	 * 总限制
	 */
	private Integer totalLimit;
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
	 * 会员类型（N 普通会员 M 月会员 Y 年会员）
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 会员类型（N 普通会员 M 月会员 Y 年会员）
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 权限类型（1 检索）
	 */
	public String getPermissionType(){
		return permissionType;
	}
	
	/**
	 * 权限类型（1 检索）
	 */
	public void setPermissionType(String permissionType){
		this.permissionType = permissionType;
	}
	
	/**
	 * 权限名称
	 */
	public String getPermissionName(){
		return permissionName;
	}
	
	/**
	 * 权限名称
	 */
	public void setPermissionName(String permissionName){
		this.permissionName = permissionName;
	}
	
	/**
	 * 权限code
	 */
	public String getPermissionCode(){
		return permissionCode;
	}
	
	/**
	 * 权限code
	 */
	public void setPermissionCode(String permissionCode){
		this.permissionCode = permissionCode;
	}
	
	/**
	 * 日限制
	 */
	public Integer getDayLimit(){
		return dayLimit;
	}
	
	/**
	 * 日限制
	 */
	public void setDayLimit(Integer dayLimit){
		this.dayLimit = dayLimit;
	}
	
	/**
	 * 总限制
	 */
	public Integer getTotalLimit(){
		return totalLimit;
	}
	
	/**
	 * 总限制
	 */
	public void setTotalLimit(Integer totalLimit){
		this.totalLimit = totalLimit;
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
