
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
public class AssetTemplateFieldMappingCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer groupId;
	/**
	 * 
	 */
	private Integer fieldId;
	
	/**
	 * 
	 */
	public Integer getGroupId(){
		return groupId;
	}
	
	/**
	 * 
	 */
	public void setGroupId(Integer groupId){
		this.groupId = groupId;
	}
	
	/**
	 * 
	 */
	public Integer getFieldId(){
		return fieldId;
	}
	
	/**
	 * 
	 */
	public void setFieldId(Integer fieldId){
		this.fieldId = fieldId;
	}

}