
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
public class AssetCategoryGroupSelfMappingCondition implements DaoCondition{

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