
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
public class AssetPropertyCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Integer searchWeight;
	
	
	/**
	 * 
	 */
	private String type;
	
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
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 
	 */
	public Integer getSearchWeight(){
		return searchWeight;
	}
	

	/**
	 * 
	 */
	public void setSearchWeight(Integer searchWeight){
		this.searchWeight = searchWeight;
	}
	

	/**
	 * 
	 */
	public String getType() {
		return type;
	}


	/**
	 * 
	 */
	public void setType(String type) {
		this.type = type;
	}
	

}