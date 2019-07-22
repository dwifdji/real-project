
package com._360pai.core.condition.activity;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
public class AuctionActivitySubCategoryCondition implements DaoCondition{

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
	private Integer parentId;
	/**
	 * 
	 */
	private String assetType;
	/**
	 * 
	 */
	private Integer priority;
	
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
	public Integer getParentId(){
		return parentId;
	}
	
	/**
	 * 
	 */
	public void setParentId(Integer parentId){
		this.parentId = parentId;
	}
	
	/**
	 * 
	 */
	public String getAssetType(){
		return assetType;
	}
	
	/**
	 * 
	 */
	public void setAssetType(String assetType){
		this.assetType = assetType;
	}
	
	/**
	 * 
	 */
	public Integer getPriority(){
		return priority;
	}
	
	/**
	 * 
	 */
	public void setPriority(Integer priority){
		this.priority = priority;
	}

}