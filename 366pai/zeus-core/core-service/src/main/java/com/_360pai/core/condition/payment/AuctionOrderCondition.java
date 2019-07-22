
package com._360pai.core.condition.payment;

import com._360pai.arch.core.abs.DaoCondition;

import java.util.Date;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分18秒
 */
public class AuctionOrderCondition implements DaoCondition{

	/**
	 * 
	 */
	private Long id;
	/**
	 * 
	 */
	private Integer activityId;
	/**
	 * 
	 */
	private String status;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private java.math.BigDecimal deposit;
	/**
	 * 
	 */
	private Integer bidId;
	/**
	 * 
	 */
	private Integer buyerAgencyId;
	/**
	 * 
	 */
	private Integer sellerAgencyId;
	/**
	 * 
	 */
	private Integer buyerId;
	/**
	 * 
	 */
	private Integer sellerId;
	/**
	 * 
	 */
	private java.math.BigDecimal amount;
	/**
	 * 
	 */
	private Boolean buyerPaidCommission;
	/**
	 * 
	 */
	private Boolean buyerPaidOrder;
	/**
	 * 
	 */
	private Boolean sellerPaidCommission;
	/**
	 * 
	 */
	private Boolean sellerPaidOrder;
	/**
	 * 
	 */
	private String assetCategory;
	/**
	 * 
	 */
	private java.math.BigDecimal serveBuyerPercent;
	/**
	 * 
	 */
	private java.math.BigDecimal serveSellerPercent;
	/**
	 * 
	 */
	private Boolean autoHandleDelay;

	private String buyerHasPayEnd;
	private String sellerHasPayEnd;
	/**
	 * 上拍来源：0 平台 1 机构
	 */
	private java.lang.String comeFrom;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private java.lang.Boolean deleteFlag;

	public String getBuyerHasPayEnd() {
		return buyerHasPayEnd;
	}

	public void setBuyerHasPayEnd(String buyerHasPayEnd) {
		this.buyerHasPayEnd = buyerHasPayEnd;
	}

	public String getSellerHasPayEnd() {
		return sellerHasPayEnd;
	}

	public void setSellerHasPayEnd(String sellerHasPayEnd) {
		this.sellerHasPayEnd = sellerHasPayEnd;
	}



	private Date updateTime;
	
	/**
	 * 
	 */
	public Long getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(Long id){
		this.id = id;
	}
	
	/**
	 * 
	 */
	public Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 
	 */
	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 
	 */
	public String getStatus(){
		return status;
	}
	
	/**
	 * 
	 */
	public void setStatus(String status){
		this.status = status;
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
	
	/**
	 * 
	 */
	public java.math.BigDecimal getDeposit(){
		return deposit;
	}
	
	/**
	 * 
	 */
	public void setDeposit(java.math.BigDecimal deposit){
		this.deposit = deposit;
	}
	
	/**
	 * 
	 */
	public Integer getBidId(){
		return bidId;
	}
	
	/**
	 * 
	 */
	public void setBidId(Integer bidId){
		this.bidId = bidId;
	}
	
	/**
	 * 
	 */
	public Integer getBuyerAgencyId(){
		return buyerAgencyId;
	}
	
	/**
	 * 
	 */
	public void setBuyerAgencyId(Integer buyerAgencyId){
		this.buyerAgencyId = buyerAgencyId;
	}
	
	/**
	 * 
	 */
	public Integer getSellerAgencyId(){
		return sellerAgencyId;
	}
	
	/**
	 * 
	 */
	public void setSellerAgencyId(Integer sellerAgencyId){
		this.sellerAgencyId = sellerAgencyId;
	}
	
	/**
	 * 
	 */
	public Integer getBuyerId(){
		return buyerId;
	}
	
	/**
	 * 
	 */
	public void setBuyerId(Integer buyerId){
		this.buyerId = buyerId;
	}
	
	/**
	 * 
	 */
	public Integer getSellerId(){
		return sellerId;
	}
	
	/**
	 * 
	 */
	public void setSellerId(Integer sellerId){
		this.sellerId = sellerId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getAmount(){
		return amount;
	}
	
	/**
	 * 
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}
	
	/**
	 * 
	 */
	public Boolean getBuyerPaidCommission(){
		return buyerPaidCommission;
	}
	
	/**
	 * 
	 */
	public void setBuyerPaidCommission(Boolean buyerPaidCommission){
		this.buyerPaidCommission = buyerPaidCommission;
	}
	
	/**
	 * 
	 */
	public Boolean getBuyerPaidOrder(){
		return buyerPaidOrder;
	}
	
	/**
	 * 
	 */
	public void setBuyerPaidOrder(Boolean buyerPaidOrder){
		this.buyerPaidOrder = buyerPaidOrder;
	}
	
	/**
	 * 
	 */
	public Boolean getSellerPaidCommission(){
		return sellerPaidCommission;
	}
	
	/**
	 * 
	 */
	public void setSellerPaidCommission(Boolean sellerPaidCommission){
		this.sellerPaidCommission = sellerPaidCommission;
	}
	
	/**
	 * 
	 */
	public Boolean getSellerPaidOrder(){
		return sellerPaidOrder;
	}
	
	/**
	 * 
	 */
	public void setSellerPaidOrder(Boolean sellerPaidOrder){
		this.sellerPaidOrder = sellerPaidOrder;
	}
	
	/**
	 * 
	 */
	public String getAssetCategory(){
		return assetCategory;
	}
	
	/**
	 * 
	 */
	public void setAssetCategory(String assetCategory){
		this.assetCategory = assetCategory;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getServeBuyerPercent(){
		return serveBuyerPercent;
	}
	
	/**
	 * 
	 */
	public void setServeBuyerPercent(java.math.BigDecimal serveBuyerPercent){
		this.serveBuyerPercent = serveBuyerPercent;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getServeSellerPercent(){
		return serveSellerPercent;
	}
	
	/**
	 * 
	 */
	public void setServeSellerPercent(java.math.BigDecimal serveSellerPercent){
		this.serveSellerPercent = serveSellerPercent;
	}
	
	/**
	 * 
	 */
	public Boolean getAutoHandleDelay(){
		return autoHandleDelay;
	}
	
	/**
	 * 
	 */
	public void setAutoHandleDelay(Boolean autoHandleDelay){
		this.autoHandleDelay = autoHandleDelay;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}