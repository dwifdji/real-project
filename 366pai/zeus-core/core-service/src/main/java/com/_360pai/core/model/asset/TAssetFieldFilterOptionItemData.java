
package com._360pai.core.model.asset;

import org.apache.commons.collections4.list.TreeList;

import java.util.List;
import java.util.Map;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月07日 09时37分08秒
 */
public class TAssetFieldFilterOptionItemData implements java.io.Serializable{

	/**
	 * 主键自增
	 */
	private Integer id;
	/**
	 * 三级筛选器ID
	 */
	private Integer filterOptionItemId;
	/**
	 * 四级筛选器名称
	 */
	private String name;
	/**
	 * 一级筛选器ID
	 */
	private Integer filterId;
	/**
	 * 四级筛选器下的字段
	 */
	private List<Map> datas;
	/**
	 * 四级筛选器下的筛选器组合
	 */
	private List<Map> mapData;
	/**
	 * 四级筛选器下的隐藏域
	 */
	private List<Map> hideList = new TreeList<>();

	public List<Map> getHideList() {
		return hideList;
	}

	public void setHideList(List<Map> hideList) {
		this.hideList = hideList;
	}

	public List<Map> getMapData() {
		return mapData;
	}

	public void setMapData(List<Map> mapData) {
		this.mapData = mapData;
	}

	public List<Map> getDatas() {
		return datas;
	}

	public void setDatas(List<Map> datas) {
		this.datas = datas;
	}

	/**
	 * 主键自增
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键自增
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 三级筛选器ID
	 */
	public Integer getFilterOptionItemId(){
		return filterOptionItemId;
	}
	
	/**
	 * 三级筛选器ID
	 */
	public void setFilterOptionItemId(Integer filterOptionItemId){
		this.filterOptionItemId = filterOptionItemId;
	}
	
	/**
	 * 四级筛选器名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 四级筛选器名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 一级筛选器ID
	 */
	public Integer getFilterId(){
		return filterId;
	}
	
	/**
	 * 一级筛选器ID
	 */
	public void setFilterId(Integer filterId){
		this.filterId = filterId;
	}

}
