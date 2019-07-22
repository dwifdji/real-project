
package com._360pai.core.model.asset;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分24秒
 */
public class AssetCategory implements java.io.Serializable{

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
	private Integer groupId;
	/**
	 * 
	 */
	private Boolean isGroupDefault;
	
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
	public Integer getGroupId(){
		return groupId;
	}
	
	/**
	 * 
	 */
	public void setGroupId(Integer groupId){
		this.groupId = groupId;
	}
	
	/**
	 * 
	 */
	public Boolean getIsGroupDefault(){
		return isGroupDefault;
	}
	
	/**
	 * 
	 */
	public void setIsGroupDefault(Boolean isGroupDefault){
		this.isGroupDefault = isGroupDefault;
	}

}
