
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public class TAssetTemplateFieldGroupMapCondition implements DaoCondition{

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