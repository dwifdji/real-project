
package com._360pai.core.model.asset;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分25秒
 */
public class AssetProperty implements java.io.Serializable{

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
