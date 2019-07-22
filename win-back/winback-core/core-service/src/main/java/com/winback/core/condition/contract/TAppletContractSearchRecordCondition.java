
package com.winback.core.condition.contract;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月18日 13时38分37秒
 */
public class TAppletContractSearchRecordCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 账户外部关系绑定表id
	 */
	private Integer extBindId;
	/**
	 * 搜索内容
	 */
	private String keyword;
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
	 * 账户外部关系绑定表id
	 */
	public Integer getExtBindId(){
		return extBindId;
	}
	
	/**
	 * 账户外部关系绑定表id
	 */
	public void setExtBindId(Integer extBindId){
		this.extBindId = extBindId;
	}
	
	/**
	 * 搜索内容
	 */
	public String getKeyword(){
		return keyword;
	}
	
	/**
	 * 搜索内容
	 */
	public void setKeyword(String keyword){
		this.keyword = keyword;
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