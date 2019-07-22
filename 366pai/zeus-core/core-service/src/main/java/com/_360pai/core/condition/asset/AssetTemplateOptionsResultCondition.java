
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
public class AssetTemplateOptionsResultCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String options;
	/**
	 * 
	 */
	private Integer categoryGroupId;
	/**
	 * 
	 */
	private Integer currentCategoryId;
	
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
	public String getOptions(){
		return options;
	}
	
	/**
	 * 
	 */
	public void setOptions(String options){
		this.options = options;
	}
	
	/**
	 * 
	 */
	public Integer getCategoryGroupId(){
		return categoryGroupId;
	}
	
	/**
	 * 
	 */
	public void setCategoryGroupId(Integer categoryGroupId){
		this.categoryGroupId = categoryGroupId;
	}
	
	/**
	 * 
	 */
	public Integer getCurrentCategoryId(){
		return currentCategoryId;
	}
	
	/**
	 * 
	 */
	public void setCurrentCategoryId(Integer currentCategoryId){
		this.currentCategoryId = currentCategoryId;
	}

}