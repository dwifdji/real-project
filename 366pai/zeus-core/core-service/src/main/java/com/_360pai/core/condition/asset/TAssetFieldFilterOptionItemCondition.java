
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月01日 16时58分24秒
 */
@Data
public class TAssetFieldFilterOptionItemCondition implements DaoCondition{

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