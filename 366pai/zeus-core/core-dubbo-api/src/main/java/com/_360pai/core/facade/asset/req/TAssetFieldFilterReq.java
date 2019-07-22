
package com._360pai.core.facade.asset.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public class TAssetFieldFilterReq extends RequestModel {

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

}
