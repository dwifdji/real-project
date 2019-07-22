
package com._360pai.core.facade.asset.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public class TAssetFieldFilterOptionItemReq extends RequestModel {

	/**
	 * 自增ID
	 */
	private Integer id;
	/**
	 * 筛选器字段ID
	 */
	private Integer filterOptionId;
	/**
	 * item名称
	 */
	private String itemName;
	/**
	 * 筛选器ID
	 */
	private Integer filterId;

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
	 * 筛选器字段ID
	 */
	public Integer getFilterOptionId(){
		return filterOptionId;
	}

	/**
	 * 筛选器字段ID
	 */
	public void setFilterOptionId(Integer filterOptionId){
		this.filterOptionId = filterOptionId;
	}

	/**
	 * item名称
	 */
	public String getItemName(){
		return itemName;
	}

	/**
	 * item名称
	 */
	public void setItemName(String itemName){
		this.itemName = itemName;
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

}
