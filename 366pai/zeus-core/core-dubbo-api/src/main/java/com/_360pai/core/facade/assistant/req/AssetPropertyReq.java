
package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分25秒
 */
public class AssetPropertyReq extends RequestModel {

	public static final Integer ASSETTYPE = 1;
	public static final Integer AUCTIONTYPE = 2;
	public static final Integer EAROLLINGTYPE = 3;

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
	 *  类型TYPE 1:拍品属性 2:拍卖专场 3:预招商专场
	 */
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

}
