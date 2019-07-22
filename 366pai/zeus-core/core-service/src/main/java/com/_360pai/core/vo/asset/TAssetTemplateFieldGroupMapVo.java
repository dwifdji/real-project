
package com._360pai.core.vo.asset;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public class TAssetTemplateFieldGroupMapVo implements java.io.Serializable{

	/**
	 * 自增ID
	 */
	private Integer id;
	/**
	 * 模板ID
	 */
	private Integer assetTemplateCategoryId;
	/**
	 * 模板名称
	 */
	private Integer assetTemplateCategoryName;
	/**
	 * 字段分组ID
	 */
	private Integer assetFieldGroupId;
	/**
	 * 字段分组名称
	 */
	private String assetFieldGroupName;

	public Integer getAssetTemplateCategoryName() {
		return assetTemplateCategoryName;
	}

	public void setAssetTemplateCategoryName(Integer assetTemplateCategoryName) {
		this.assetTemplateCategoryName = assetTemplateCategoryName;
	}

	public String getAssetFieldGroupName() {
		return assetFieldGroupName;
	}

	public void setAssetFieldGroupName(String assetFieldGroupName) {
		this.assetFieldGroupName = assetFieldGroupName;
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
