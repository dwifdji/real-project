
package com._360pai.core.condition.fdd;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月15日 16时37分04秒
 */
public class GatewayFddSignAutoCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 平台用户id
	 */
	private java.lang.Integer memberId;
	/**
	 * 法大大的用户id
	 */
	private java.lang.String customerId;
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
	 * 开户状态
	 */
	private java.lang.Integer status;
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
	
	/**
	 * 平台用户id
	 */
	public java.lang.Integer getMemberId(){
		return memberId;
	}
	
	/**
	 * 平台用户id
	 */
	public void setMemberId(java.lang.Integer memberId){
		this.memberId = memberId;
	}
	
	/**
	 * 法大大的用户id
	 */
	public java.lang.String getCustomerId(){
		return customerId;
	}
	
	/**
	 * 法大大的用户id
	 */
	public void setCustomerId(java.lang.String customerId){
		this.customerId = customerId;
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
	 * 开户状态
	 */
	public java.lang.Integer getStatus(){
		return status;
	}
	
	/**
	 * 开户状态
	 */
	public void setStatus(java.lang.Integer status){
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