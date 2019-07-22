
package com._360pai.core.model.activity;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分25秒
 */
public class AuctionActivityCategory implements java.io.Serializable{

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
