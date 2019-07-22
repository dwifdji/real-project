
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月04日 14时45分45秒
 */
public class TAssetFieldFilterCondition implements DaoCondition{

	/**
	 * 自增ID
	 */
	private Integer id;
	/**
	 * 筛选器名称
	 */
	private String name;
	/**
	 * 排序编号
	 */
	private Integer orderNum;
	/**
	 * 是否可扩展
	 */
	private Integer extensible;
	/**
	 * 筛选器的KEY
	 */
	private String filterKey;
	/**
	 * 筛选器的TYPE
	 */
	private String type;
	
	/**
	 * 自增ID
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 自增ID
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 筛选器名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 筛选器名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 排序编号
	 */
	public Integer getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 排序编号
	 */
	public void setOrderNum(Integer orderNum){
		this.orderNum = orderNum;
	}
	
	/**
	 * 是否可扩展
	 */
	public Integer getExtensible(){
		return extensible;
	}
	
	/**
	 * 是否可扩展
	 */
	public void setExtensible(Integer extensible){
		this.extensible = extensible;
	}
	
	/**
	 * 筛选器的KEY
	 */
	public String getFilterKey(){
		return filterKey;
	}
	
	/**
	 * 筛选器的KEY
	 */
	public void setFilterKey(String filterKey){
		this.filterKey = filterKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}