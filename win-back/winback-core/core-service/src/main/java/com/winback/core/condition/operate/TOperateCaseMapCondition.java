
package com.winback.core.condition.operate;


import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月22日 10时27分34秒
 */
public class TOperateCaseMapCondition implements DaoCondition {

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 关联的案件id
	 */
	private java.lang.Integer caseId;
	/**
	 * 排序
	 */
	private java.lang.Integer sort;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private java.lang.Boolean deleteFlag;
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
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 主键
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 关联的案件id
	 */
	public java.lang.Integer getCaseId(){
		return caseId;
	}
	
	/**
	 * 关联的案件id
	 */
	public void setCaseId(java.lang.Integer caseId){
		this.caseId = caseId;
	}
	
	/**
	 * 排序
	 */
	public java.lang.Integer getSort(){
		return sort;
	}
	
	/**
	 * 排序
	 */
	public void setSort(java.lang.Integer sort){
		this.sort = sort;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public java.lang.Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public void setDeleteFlag(java.lang.Boolean deleteFlag){
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