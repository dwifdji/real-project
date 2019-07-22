
package com.winback.core.condition.contract;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public class TContractTypeMapCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 合同id
	 */
	private Integer contractId;
	/**
	 * 合同类型id
	 */
	private Integer contractTypeId;
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
	 * 合同id
	 */
	public Integer getContractId(){
		return contractId;
	}
	
	/**
	 * 合同id
	 */
	public void setContractId(Integer contractId){
		this.contractId = contractId;
	}
	
	/**
	 * 合同类型id
	 */
	public Integer getContractTypeId(){
		return contractTypeId;
	}
	
	/**
	 * 合同类型id
	 */
	public void setContractTypeId(Integer contractTypeId){
		this.contractTypeId = contractTypeId;
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