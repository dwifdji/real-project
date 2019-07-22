
package com.winback.core.condition.account;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
public class TSysStaffCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * QQ号
	 */
	private String qq;
	/**
	 * 部门
	 */
	private String dept;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 是否管理员
	 */
	private Boolean adminFlag;
	/**
	 * 状态（0：禁用，1：启用）
	 */
	private Boolean status;
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
	 * 手机号
	 */
	public String getMobile(){
		return mobile;
	}
	
	/**
	 * 手机号
	 */
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	
	/**
	 * 密码
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * 密码
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 * 名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 邮箱
	 */
	public String getEmail(){
		return email;
	}
	
	/**
	 * 邮箱
	 */
	public void setEmail(String email){
		this.email = email;
	}
	
	/**
	 * QQ号
	 */
	public String getQq(){
		return qq;
	}
	
	/**
	 * QQ号
	 */
	public void setQq(String qq){
		this.qq = qq;
	}
	
	/**
	 * 部门
	 */
	public String getDept(){
		return dept;
	}
	
	/**
	 * 部门
	 */
	public void setDept(String dept){
		this.dept = dept;
	}
	
	/**
	 * 备注
	 */
	public String getMemo(){
		return memo;
	}
	
	/**
	 * 备注
	 */
	public void setMemo(String memo){
		this.memo = memo;
	}
	
	/**
	 * 是否管理员
	 */
	public Boolean getAdminFlag(){
		return adminFlag;
	}
	
	/**
	 * 是否管理员
	 */
	public void setAdminFlag(Boolean adminFlag){
		this.adminFlag = adminFlag;
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