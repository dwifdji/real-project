
package com._360pai.core.model.asset;

import org.apache.commons.collections4.list.TreeList;

import java.util.List;
import java.util.Map;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public class TAssetFieldFilterOption implements java.io.Serializable{

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

	private List<Map> fields = new TreeList<>();
	/**
	 * 隐藏域
	 */
	private List<Map> hideList = new TreeList<>();

	public List<Map> getHideList() {
		return hideList;
	}

	public void setHideList(List<Map> hideList) {
		this.hideList = hideList;
	}

	private List<TAssetFieldFilterOptionItem> items;

	public List<TAssetFieldFilterOptionItem> getItems() {
		return items;
	}

	public void setItems(List<TAssetFieldFilterOptionItem> items) {
		this.items = items;
	}

	public List<Map> getFields() {
		return fields;
	}

	public void setFields(List<Map> fields) {
		this.fields = fields;
	}

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
