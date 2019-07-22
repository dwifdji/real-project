
package com._360pai.core.model.asset;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public class TAssetTemplateFieldGroupMap implements java.io.Serializable{

	/**
	 * 自增ID
	 */
	private Integer id;
	/**
	 * 模板ID
	 */
	private Integer assetTemplateCategoryId;
	/**
	 * 字段分组ID
	 */
	private Integer assetFieldGroupId;
	/**
	 * 字段分组ID
	 */
	private Integer assetFieldGroupName;
	
	/**
	 * 自增ID
	 */
	public Integer getId(){
		return id;
	}

	public Integer getAssetFieldGroupName() {
		return assetFieldGroupName;
	}

	public void setAssetFieldGroupName(Integer assetFieldGroupName) {
		this.assetFieldGroupName = assetFieldGroupName;
	}

	/**
	 * 自增ID
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 模板ID
	 */
	public Integer getAssetTemplateCategoryId(){
		return assetTemplateCategoryId;
	}
	
	/**
	 * 模板ID
	 */
	public void setAssetTemplateCategoryId(Integer assetTemplateCategoryId){
		this.assetTemplateCategoryId = assetTemplateCategoryId;
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

}
