
package com._360pai.core.model.fdd;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月15日 16时37分03秒
 */
public class GatewayFddGenerateContract implements java.io.Serializable{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 平台用户id
	 */
	private java.lang.Integer partyId;
	/**
	 * 合同标题
	 */
	private java.lang.String docTitle;
	/**
	 * 合同的模板id
	 */
	private java.lang.String templateId;
	/**
	 * 签章的合同id
	 */
	private java.lang.String contractId;
	/**
	 * 合同下载地址
	 */
	private java.lang.String downloadUrl;
	/**
	 * 合同的下载地址
	 */
	private java.lang.String viewpdfUrl;
	/**
	 * 法大大的开户id
	 */
	private java.lang.String fddId;
	/**
	 * 开户状态
	 */
	private java.lang.String status;
	/**
	 * 处理结果
	 */
	private java.lang.String msg;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;


	private java.lang.String type;


	private java.lang.String activityId;


	/**
	 * 
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}

	public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/**
	 * 合同标题
	 */
	public java.lang.String getDocTitle(){
		return docTitle;
	}
	
	/**
	 * 合同标题
	 */
	public void setDocTitle(java.lang.String docTitle){
		this.docTitle = docTitle;
	}
	
	/**
	 * 合同的模板id
	 */
	public java.lang.String getTemplateId(){
		return templateId;
	}
	
	/**
	 * 合同的模板id
	 */
	public void setTemplateId(java.lang.String templateId){
		this.templateId = templateId;
	}
	
	/**
	 * 签章的合同id
	 */
	public java.lang.String getContractId(){
		return contractId;
	}
	
	/**
	 * 签章的合同id
	 */
	public void setContractId(java.lang.String contractId){
		this.contractId = contractId;
	}
	
	/**
	 * 合同下载地址
	 */
	public java.lang.String getDownloadUrl(){
		return downloadUrl;
	}
	
	/**
	 * 合同下载地址
	 */
	public void setDownloadUrl(java.lang.String downloadUrl){
		this.downloadUrl = downloadUrl;
	}
	
	/**
	 * 合同的下载地址
	 */
	public java.lang.String getViewpdfUrl(){
		return viewpdfUrl;
	}
	
	/**
	 * 合同的下载地址
	 */
	public void setViewpdfUrl(java.lang.String viewpdfUrl){
		this.viewpdfUrl = viewpdfUrl;
	}
	
	/**
	 * 法大大的开户id
	 */
	public java.lang.String getFddId(){
		return fddId;
	}
	
	/**
	 * 法大大的开户id
	 */
	public void setFddId(java.lang.String fddId){
		this.fddId = fddId;
	}
	
	/**
	 * 开户状态
	 */
	public java.lang.String getStatus(){
		return status;
	}
	
	/**
	 * 开户状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	
	/**
	 * 处理结果
	 */
	public java.lang.String getMsg(){
		return msg;
	}
	
	/**
	 * 处理结果
	 */
	public void setMsg(java.lang.String msg){
		this.msg = msg;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	
	/**
	 * 更新时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 更新时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}
