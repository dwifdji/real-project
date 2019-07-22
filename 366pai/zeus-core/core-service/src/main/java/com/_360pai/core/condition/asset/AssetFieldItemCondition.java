
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
public class AssetFieldItemCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer categoryId;
	/**
	 * 
	 */
	private Integer groupId;
	/**
	 * 
	 */
	private Integer fieldId;
	/**
	 * 
	 */
	private Boolean required;
	
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
	 * 
	 */
	public Integer getCategoryId(){
		return categoryId;
	}
	
	/**
	 * 
	 */
	public void setCategoryId(Integer categoryId){
		this.categoryId = categoryId;
	}
	
	/**
	 * 
	 */
	public Integer getGroupId(){
		return groupId;
	}
	
	/**
	 * 
	 */
	public void setGroupId(Integer groupId){
		this.groupId = groupId;
	}
	
	/**
	 * 
	 */
	public Integer getFieldId(){
		return fieldId;
	}
	
	/**
	 * 
	 */
	public void setFieldId(Integer fieldId){
		this.fieldId = fieldId;
	}
	
	/**
	 * 
	 */
	public Boolean getRequired(){
		return required;
	}
	
	/**
	 * 
	 */
	public void setRequired(Boolean required){
		this.required = required;
	}

}