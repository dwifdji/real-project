
package com._360pai.core.condition.numberJump;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
public class TCalculatorRequestRecordCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 外部绑定表id
	 */
	private Integer extBindId;
	/**
	 * 0 本息计算器 1 债权计算器
	 */
	private String type;
	/**
	 * 入参
	 */
	private String inputParameter;
	/**
	 * 出参
	 */
	private String outputParameter;
	/**
	 * 是否删除（0 否 1 是）
	 */
	private Boolean isDelete;
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
	 * 外部绑定表id
	 */
	public Integer getExtBindId(){
		return extBindId;
	}
	
	/**
	 * 外部绑定表id
	 */
	public void setExtBindId(Integer extBindId){
		this.extBindId = extBindId;
	}
	
	/**
	 * 0 本息计算器 1 债权计算器
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 0 本息计算器 1 债权计算器
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 入参
	 */
	public String getInputParameter(){
		return inputParameter;
	}
	
	/**
	 * 入参
	 */
	public void setInputParameter(String inputParameter){
		this.inputParameter = inputParameter;
	}
	
	/**
	 * 出参
	 */
	public String getOutputParameter(){
		return outputParameter;
	}
	
	/**
	 * 出参
	 */
	public void setOutputParameter(String outputParameter){
		this.outputParameter = outputParameter;
	}
	
	/**
	 * 是否删除（0 否 1 是）
	 */
	public Boolean getIsDelete(){
		return isDelete;
	}
	
	/**
	 * 是否删除（0 否 1 是）
	 */
	public void setIsDelete(Boolean isDelete){
		this.isDelete = isDelete;
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