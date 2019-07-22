
package com._360pai.core.model.asset;

import lombok.Data;
import org.apache.commons.collections4.list.TreeList;

import java.util.List;
import java.util.Map;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月01日 16时58分24秒
 */
@Data
public class TAssetFieldFilterOptionItem implements java.io.Serializable{

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
	private Integer orderNum;

	private List<Map> maps;
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

	private List<TAssetFieldFilterOptionItemData> itemDatas;

	public List<TAssetFieldFilterOptionItemData> getItemDatas() {
		return itemDatas;
	}

	public void setItemDatas(List<TAssetFieldFilterOptionItemData> itemDatas) {
		this.itemDatas = itemDatas;
	}

	public List<Map> getMaps() {
		return maps;
	}

	public void setMaps(List<Map> maps) {
		this.maps = maps;
	}

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
