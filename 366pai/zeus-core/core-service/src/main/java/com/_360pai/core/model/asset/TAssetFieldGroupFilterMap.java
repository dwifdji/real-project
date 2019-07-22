
package com._360pai.core.model.asset;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public class TAssetFieldGroupFilterMap implements java.io.Serializable{

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
