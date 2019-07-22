
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月21日 09时57分53秒
 */
@Data
public class TAssetFieldItemCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 债券类型ID
	 */
	private Integer categoryId;
	/**
	 * 模板ID
	 */
	private Integer templateId;
	/**
	 * 排序编号
	 */
	private Integer orderNum;
	/**
	 * 字段分组ID
	 */
	private Integer fieldGroupId;
	/**
	 * 字段ID
	 */
	private Integer fieldId;
	/**
	 * 是否隐藏 0不隐藏  1隐藏
	 */
	private Integer displayed;
	/**
	 * 是否必填
	 */
	private Integer required;
	/**
	 * 是否紧挨
	 */
	private Integer isNear;
	/**
	 * 日期校验
	 */
	private Integer timeDay;
	/**
	 * 附属的二级筛选器ID
	 */
	private Integer partyFilterOptionId;
	/**
	 * 二级筛选器ID
	 */
	private Integer filterOptionId;
	/**
	 * 三级筛选器ID
	 */
	private Integer filterOptionItemId;
	/**
	 * 四级筛选器ID
	 */
	private Integer filterOptionItemDataId;
	/**
	 * 模块id
	 */
	private Integer modelId;
	/**
	 * 子模块id
	 */
	private Integer modelOptionId;
	
	/**
	 * 
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 债券类型ID
	 */
	public Integer getCategoryId(){
		return categoryId;
	}
	
	/**
	 * 债券类型ID
	 */
	public void setCategoryId(Integer categoryId){
		this.categoryId = categoryId;
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
	
	/**
	 * 排序编号
	 */
	public Integer getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 排序编号
	 */
	public void setOrderNum(Integer orderNum){
		this.orderNum = orderNum;
	}
	
	/**
	 * 字段分组ID
	 */
	public Integer getFieldGroupId(){
		return fieldGroupId;
	}
	
	/**
	 * 字段分组ID
	 */
	public void setFieldGroupId(Integer fieldGroupId){
		this.fieldGroupId = fieldGroupId;
	}
	
	/**
	 * 字段ID
	 */
	public Integer getFieldId(){
		return fieldId;
	}
	
	/**
	 * 字段ID
	 */
	public void setFieldId(Integer fieldId){
		this.fieldId = fieldId;
	}
	
	/**
	 * 是否隐藏 0不隐藏  1隐藏
	 */
	public Integer getDisplayed(){
		return displayed;
	}
	
	/**
	 * 是否隐藏 0不隐藏  1隐藏
	 */
	public void setDisplayed(Integer displayed){
		this.displayed = displayed;
	}
	
	/**
	 * 是否必填
	 */
	public Integer getRequired(){
		return required;
	}
	
	/**
	 * 是否必填
	 */
	public void setRequired(Integer required){
		this.required = required;
	}
	
	/**
	 * 是否紧挨
	 */
	public Integer getIsNear(){
		return isNear;
	}
	
	/**
	 * 是否紧挨
	 */
	public void setIsNear(Integer isNear){
		this.isNear = isNear;
	}
	
	/**
	 * 附属的二级筛选器ID
	 */
	public Integer getPartyFilterOptionId(){
		return partyFilterOptionId;
	}
	
	/**
	 * 附属的二级筛选器ID
	 */
	public void setPartyFilterOptionId(Integer partyFilterOptionId){
		this.partyFilterOptionId = partyFilterOptionId;
	}
	
	/**
	 * 二级筛选器ID
	 */
	public Integer getFilterOptionId(){
		return filterOptionId;
	}
	
	/**
	 * 二级筛选器ID
	 */
	public void setFilterOptionId(Integer filterOptionId){
		this.filterOptionId = filterOptionId;
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
	 * 四级筛选器ID
	 */
	public Integer getFilterOptionItemDataId(){
		return filterOptionItemDataId;
	}
	
	/**
	 * 四级筛选器ID
	 */
	public void setFilterOptionItemDataId(Integer filterOptionItemDataId){
		this.filterOptionItemDataId = filterOptionItemDataId;
	}
	
	/**
	 * 模块id
	 */
	public Integer getModelId(){
		return modelId;
	}
	
	/**
	 * 模块id
	 */
	public void setModelId(Integer modelId){
		this.modelId = modelId;
	}
	
	/**
	 * 子模块id
	 */
	public Integer getModelOptionId(){
		return modelOptionId;
	}
	
	/**
	 * 子模块id
	 */
	public void setModelOptionId(Integer modelOptionId){
		this.modelOptionId = modelOptionId;
	}

}