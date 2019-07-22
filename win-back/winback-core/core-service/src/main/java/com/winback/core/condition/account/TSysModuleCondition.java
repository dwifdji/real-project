
package com.winback.core.condition.account;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
public class TSysModuleCondition implements DaoCondition{

	/**
	 * 自定id,主要供前端展示权限列表分类排序使用.
	 */
	private Integer id;
	/**
	 * 模块code
	 */
	private String code;
	/**
	 * 模块名称
	 */
	private String name;
	/**
	 * 排序号
	 */
	private Integer orderNum;
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
	 * 模块code
	 */
	public String getCode(){
		return code;
	}
	
	/**
	 * 模块code
	 */
	public void setCode(String code){
		this.code = code;
	}
	
	/**
	 * 模块名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 模块名称
	 */
	public void setName(String name){
		this.name = name;
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