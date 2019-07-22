
package com._360pai.core.condition.agreement;

import com._360pai.arch.core.abs.DaoCondition;

import java.util.Date;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
public class DealAgreementCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Long orderId;
	/**
	 * 
	 */
	private Boolean allSigned;
	/**
	 * 
	 */
	private Boolean buyerSigned;
	/**
	 * 
	 */
	private Boolean sellerSigned;
	/**
	 * 
	 */
	private String downloadUrl;
	/**
	 * 
	 */
	private Integer templateId;
	/**
	 * 
	 */
	private String viewpdfUrl;
	/**
	 * 
	 */
	private String contractId;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	private java.util.Date updateTime;

	private String contractType;

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

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
	 * 
	 */
	public Long getOrderId(){
		return orderId;
	}
	
	/**
	 * 
	 */
	public void setOrderId(Long orderId){
		this.orderId = orderId;
	}
	
	/**
	 * 
	 */
	public Boolean getAllSigned(){
		return allSigned;
	}
	
	/**
	 * 
	 */
	public void setAllSigned(Boolean allSigned){
		this.allSigned = allSigned;
	}
	
	/**
	 * 
	 */
	public Boolean getBuyerSigned(){
		return buyerSigned;
	}
	
	/**
	 * 
	 */
	public void setBuyerSigned(Boolean buyerSigned){
		this.buyerSigned = buyerSigned;
	}
	
	/**
	 * 
	 */
	public Boolean getSellerSigned(){
		return sellerSigned;
	}
	
	/**
	 * 
	 */
	public void setSellerSigned(Boolean sellerSigned){
		this.sellerSigned = sellerSigned;
	}
	
	/**
	 * 
	 */
	public String getDownloadUrl(){
		return downloadUrl;
	}
	
	/**
	 * 
	 */
	public void setDownloadUrl(String downloadUrl){
		this.downloadUrl = downloadUrl;
	}
	
	/**
	 * 
	 */
	public Integer getTemplateId(){
		return templateId;
	}
	
	/**
	 * 
	 */
	public void setTemplateId(Integer templateId){
		this.templateId = templateId;
	}
	
	/**
	 * 
	 */
	public String getViewpdfUrl(){
		return viewpdfUrl;
	}
	
	/**
	 * 
	 */
	public void setViewpdfUrl(String viewpdfUrl){
		this.viewpdfUrl = viewpdfUrl;
	}
	
	/**
	 * 
	 */
	public String getContractId(){
		return contractId;
	}
	
	/**
	 * 
	 */
	public void setContractId(String contractId){
		this.contractId = contractId;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreatedAt(){
		return createdAt;
	}
	
	/**
	 * 
	 */
	public void setCreatedAt(java.util.Date createdAt){
		this.createdAt = createdAt;
	}

}