
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public class TAssetFieldFilterOptionCondition implements DaoCondition{

	/**
	 * 自增id
	 */
	private Integer id;
	/**
	 * 筛选器选项名称
	 */
	private String name;
	/**
	 * 筛选器ID
	 */
	private Integer filterId;
	/**
	 * 搜索宽度
	 */
	private Integer searchWeight;
	
	/**
	 * 自增id
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 自增id
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 筛选器选项名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 筛选器选项名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 筛选器ID
	 */
	public Integer getFilterId(){
		return filterId;
	}
	
	/**
	 * 筛选器ID
	 */
	public void setFilterId(Integer filterId){
		this.filterId = filterId;
	}
	
	/**
	 * 搜索宽度
	 */
	public Integer getSearchWeight(){
		return searchWeight;
	}
	
	/**
	 * 搜索宽度
	 */
	public void setSearchWeight(Integer searchWeight){
		this.searchWeight = searchWeight;
	}

}