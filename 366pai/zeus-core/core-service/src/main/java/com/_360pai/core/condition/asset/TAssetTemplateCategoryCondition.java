
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public class TAssetTemplateCategoryCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 模板名称
	 */
	private String templateName;
	/**
	 * 债券类型
	 */
	private Integer categoryId;
	/**
	 * 是否是默认模板
	 */
	private Integer isGroupDefault;
	/**
	 * 子类型
	 */
	private Integer categoryOptionId;
	
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
	 * 模板名称
	 */
	public String getTemplateName(){
		return templateName;
	}
	
	/**
	 * 模板名称
	 */
	public void setTemplateName(String templateName){
		this.templateName = templateName;
	}
	
	/**
	 * 债券类型
	 */
	public Integer getCategoryId(){
		return categoryId;
	}
	
	/**
	 * 债券类型
	 */
	public void setCategoryId(Integer categoryId){
		this.categoryId = categoryId;
	}
	
	/**
	 * 是否是默认模板
	 */
	public Integer getIsGroupDefault(){
		return isGroupDefault;
	}
	
	/**
	 * 是否是默认模板
	 */
	public void setIsGroupDefault(Integer isGroupDefault){
		this.isGroupDefault = isGroupDefault;
	}

	public Integer getCategoryOptionId() {
		return categoryOptionId;
	}

	public void setCategoryOptionId(Integer categoryOptionId) {
		this.categoryOptionId = categoryOptionId;
	}
}