
package com._360pai.core.facade.asset.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月13日 15时24分26秒
 */
public class TAssetTemplateRecodeReq extends RequestModel {

	/**
	 * 主键自增
	 */
	private Integer id;
	/**
	 * 模板ID
	 */
	private Integer templateId;
	/**
	 * 筛选器ID
	 */
	private Integer filterId;
	/**
	 * 一级筛选器ID
	 */
	private Integer filterOptionId;
	/**
	 * 二级筛选器ID
	 */
	private Integer filterOptionItemId;
	/**
	 * 三级筛选器ID
	 */
	private Integer filterOptionItemDataId;
	/**
	 * 模块ID
	 */
	private Integer modelId;
	/**
	 * 子模块ID
	 */
	private Integer modelOptionId;
	/**
	 * 模块名称
	 */
	private String modelTitle;
	/**
	 * 子模块名称
	 */
	private String modelOptionTitle;
	/**
	 * 用户ID
	 */
	private Integer partyId;
	/**
	 * 分组ID
	 */
	private Integer groupId;
	/**
	 *  模块版本version
	 */
	private String version;
	/**
	 * 是否删除 0没有删除  1删除
	 */
	private Integer isDel;
	/**
	 * 是否提交 0没有提交  1提交
	 */
	private Integer isSubmit;

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getIsSubmit() {
		return isSubmit;
	}

	public void setIsSubmit(Integer isSubmit) {
		this.isSubmit = isSubmit;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getModelTitle() {
		return modelTitle;
	}

	public void setModelTitle(String modelTitle) {
		this.modelTitle = modelTitle;
	}

	public String getModelOptionTitle() {
		return modelOptionTitle;
	}

	public void setModelOptionTitle(String modelOptionTitle) {
		this.modelOptionTitle = modelOptionTitle;
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
	
	/**
	 * 一级筛选器ID
	 */
	public Integer getFilterOptionId(){
		return filterOptionId;
	}
	
	/**
	 * 一级筛选器ID
	 */
	public void setFilterOptionId(Integer filterOptionId){
		this.filterOptionId = filterOptionId;
	}
	
	/**
	 * 二级筛选器ID
	 */
	public Integer getFilterOptionItemId(){
		return filterOptionItemId;
	}
	
	/**
	 * 二级筛选器ID
	 */
	public void setFilterOptionItemId(Integer filterOptionItemId){
		this.filterOptionItemId = filterOptionItemId;
	}
	
	/**
	 * 三级筛选器ID
	 */
	public Integer getFilterOptionItemDataId(){
		return filterOptionItemDataId;
	}
	
	/**
	 * 三级筛选器ID
	 */
	public void setFilterOptionItemDataId(Integer filterOptionItemDataId){
		this.filterOptionItemDataId = filterOptionItemDataId;
	}
	
	/**
	 * 模块ID
	 */
	public Integer getModelId(){
		return modelId;
	}
	
	/**
	 * 模块ID
	 */
	public void setModelId(Integer modelId){
		this.modelId = modelId;
	}
	
	/**
	 * 子模块ID
	 */
	public Integer getModelOptionId(){
		return modelOptionId;
	}
	
	/**
	 * 子模块ID
	 */
	public void setModelOptionId(Integer modelOptionId){
		this.modelOptionId = modelOptionId;
	}
	
	/**
	 * 用户ID
	 */
	public Integer getPartyId(){
		return partyId;
	}
	
	/**
	 * 用户ID
	 */
	public void setPartyId(Integer partyId){
		this.partyId = partyId;
	}
	
	/**
	 *  模块版本version
	 */
	public String getVersion(){
		return version;
	}
	
	/**
	 *  模块版本version
	 */
	public void setVersion(String version){
		this.version = version;
	}

}
