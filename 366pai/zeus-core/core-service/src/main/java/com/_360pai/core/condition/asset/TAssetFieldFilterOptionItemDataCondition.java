
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月07日 09时37分08秒
 */
public class TAssetFieldFilterOptionItemDataCondition implements DaoCondition{

	/**
	 * 主键自增
	 */
	private Integer id;
	/**
	 * 三级筛选器ID
	 */
	private Integer filterOptionItemId;
	/**
	 * 四级筛选器名称
	 */
	private String name;
	/**
	 * 一级筛选器ID
	 */
	private Integer filterId;
	
	/**
	 * 主键自增
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键自增
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 三级筛选器ID
	 */
	public Integer getFilterOptionItemId(){
		return filterOptionItemId;
	}
	
	/**
	 * 三级筛选器ID
	 */
	public void setFilterOptionItemId(Integer filterOptionItemId){
		this.filterOptionItemId = filterOptionItemId;
	}
	
	/**
	 * 四级筛选器名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 四级筛选器名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 一级筛选器ID
	 */
	public Integer getFilterId(){
		return filterId;
	}
	
	/**
	 * 一级筛选器ID
	 */
	public void setFilterId(Integer filterId){
		this.filterId = filterId;
	}

}