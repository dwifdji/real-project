
package com._360pai.core.model.asset;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分24秒
 */
public class AssetCategoryGroupSelfMapping implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer fromCategoryGroupId;
	/**
	 * 
	 */
	private Integer toCategoryGroupId;
	
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
	public Integer getFromCategoryGroupId(){
		return fromCategoryGroupId;
	}
	
	/**
	 * 
	 */
	public void setFromCategoryGroupId(Integer fromCategoryGroupId){
		this.fromCategoryGroupId = fromCategoryGroupId;
	}
	
	/**
	 * 
	 */
	public Integer getToCategoryGroupId(){
		return toCategoryGroupId;
	}
	
	/**
	 * 
	 */
	public void setToCategoryGroupId(Integer toCategoryGroupId){
		this.toCategoryGroupId = toCategoryGroupId;
	}

}
