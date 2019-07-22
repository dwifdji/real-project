
package com.winback.core.condition.assistant;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月17日 13时31分40秒
 */
public class TCourtCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 类型（基层：beginner，中级：intermediate，高级：advanced）
	 */
	private String type;
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
	 * 类型（基层：beginner，中级：intermediate，高级：advanced）
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 类型（基层：beginner，中级：intermediate，高级：advanced）
	 */
	public void setType(String type){
		this.type = type;
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