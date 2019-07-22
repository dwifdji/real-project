
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public class TAssetFieldGroupFilterMapCondition implements DaoCondition{

	/**
	 * 自增ID
	 */
	private Integer id;
	/**
	 * 筛选器ID
	 */
	private Integer assetFilterId;
	/**
	 * 字段分组ID
	 */
	private Integer assetFieldGroupId;
	/**
	 * 模板ID
	 */
	private Integer templateId;
	
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
	 * 筛选器ID
	 */
	public Integer getAssetFilterId(){
		return assetFilterId;
	}
	
	/**
	 * 筛选器ID
	 */
	public void setAssetFilterId(Integer assetFilterId){
		this.assetFilterId = assetFilterId;
	}
	
	/**
	 * 字段分组ID
	 */
	public Integer getAssetFieldGroupId(){
		return assetFieldGroupId;
	}
	
	/**
	 * 字段分组ID
	 */
	public void setAssetFieldGroupId(Integer assetFieldGroupId){
		this.assetFieldGroupId = assetFieldGroupId;
	}
	
	/**
	 * 模板ID
	 */
	public Integer getTemplateId(){
		return templateId;
	}
	
	/**
	 * 模板ID
	 */
	public void setTemplateId(Integer templateId){
		this.templateId = templateId;
	}

}