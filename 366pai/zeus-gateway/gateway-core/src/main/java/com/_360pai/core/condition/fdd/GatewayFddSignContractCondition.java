
package com._360pai.core.condition.fdd;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月02日 11时38分38秒
 */
public class GatewayFddSignContractCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 竞买人签字转态 0 未签署 1 已签署
	 */
	private java.lang.Boolean buyerSigned;
	/**
	 * 委托人签章状态 0 未签署 1 已签署
	 */
	private java.lang.Boolean sellerSigned;
	/**
	 * 所有的签章状态 0 未签署 1 已签署
	 */
	private java.lang.Boolean allSigned;
	/**
	 * 活动的订单状态
	 */
	private java.lang.String activityId;
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
	 * 签章类型1委托协议2补充协议3成交协议4预招商协议
	 */
	private java.lang.String signType;
	/**
	 * 自动签章参数
	 */
	private java.lang.String autoSignParam;
	/**
	 * 委托人签章参数
	 */
	private java.lang.String sellerSignParam;
	/**
	 * 竞买人签章参数
	 */
	private java.lang.String buyerSignParam;
	
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
	 * 竞买人签字转态 0 未签署 1 已签署
	 */
	public java.lang.Boolean getBuyerSigned(){
		return buyerSigned;
	}
	
	/**
	 * 竞买人签字转态 0 未签署 1 已签署
	 */
	public void setBuyerSigned(java.lang.Boolean buyerSigned){
		this.buyerSigned = buyerSigned;
	}
	
	/**
	 * 委托人签章状态 0 未签署 1 已签署
	 */
	public java.lang.Boolean getSellerSigned(){
		return sellerSigned;
	}
	
	/**
	 * 委托人签章状态 0 未签署 1 已签署
	 */
	public void setSellerSigned(java.lang.Boolean sellerSigned){
		this.sellerSigned = sellerSigned;
	}
	
	/**
	 * 所有的签章状态 0 未签署 1 已签署
	 */
	public java.lang.Boolean getAllSigned(){
		return allSigned;
	}
	
	/**
	 * 所有的签章状态 0 未签署 1 已签署
	 */
	public void setAllSigned(java.lang.Boolean allSigned){
		this.allSigned = allSigned;
	}
	
	/**
	 * 活动的订单状态
	 */
	public java.lang.String getActivityId(){
		return activityId;
	}
	
	/**
	 * 活动的订单状态
	 */
	public void setActivityId(java.lang.String activityId){
		this.activityId = activityId;
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
	
	/**
	 * 签章类型1委托协议2补充协议3成交协议4预招商协议
	 */
	public java.lang.String getSignType(){
		return signType;
	}
	
	/**
	 * 签章类型1委托协议2补充协议3成交协议4预招商协议
	 */
	public void setSignType(java.lang.String signType){
		this.signType = signType;
	}
	
	/**
	 * 自动签章参数
	 */
	public java.lang.String getAutoSignParam(){
		return autoSignParam;
	}
	
	/**
	 * 自动签章参数
	 */
	public void setAutoSignParam(java.lang.String autoSignParam){
		this.autoSignParam = autoSignParam;
	}
	
	/**
	 * 委托人签章参数
	 */
	public java.lang.String getSellerSignParam(){
		return sellerSignParam;
	}
	
	/**
	 * 委托人签章参数
	 */
	public void setSellerSignParam(java.lang.String sellerSignParam){
		this.sellerSignParam = sellerSignParam;
	}
	
	/**
	 * 竞买人签章参数
	 */
	public java.lang.String getBuyerSignParam(){
		return buyerSignParam;
	}
	
	/**
	 * 竞买人签章参数
	 */
	public void setBuyerSignParam(java.lang.String buyerSignParam){
		this.buyerSignParam = buyerSignParam;
	}

}