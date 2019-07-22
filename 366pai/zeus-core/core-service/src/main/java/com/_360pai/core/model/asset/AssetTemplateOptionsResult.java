
package com._360pai.core.model.asset;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分25秒
 */
public class AssetTemplateOptionsResult implements java.io.Serializable{

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
