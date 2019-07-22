
package com._360pai.core.model.activity;

import java.math.BigDecimal;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月14日 16时50分02秒
 */
public class TAuctionPayOrder implements java.io.Serializable{

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
	private String depositPayId;
	/**
	 * 
	 */
	private java.math.BigDecimal depositPayAmount;
	/**
	 * 
	 */
	private String buyerRemainPayId;
	/**
	 * 
	 */
	private java.math.BigDecimal buyerRemainPayAmount;
	/**
	 * 
	 */
	private String buyerCommissionPayId;
	/**
	 * 
	 */
	private java.math.BigDecimal buyerCommissionPayAmount;
	/**
	 * 
	 */
	private String sellerCommissionPayId;
	/**
	 * 
	 */
	private java.math.BigDecimal sellerCommissionPayAmount;
	/**
	 * 
	 */
	private String buyerAgencyCommissionPayId;
	/**
	 * 
	 */
	private java.math.BigDecimal buyerAgencyCommissionPayAmount;
	/**
	 * 
	 */
	private String sellerAgencyCommissionPayId;
	/**
	 * 
	 */
	private java.math.BigDecimal sellerAgencyCommissionPayAmount;
	/**
	 * 
	 */
	private String platformCommissionPayId;
	/**
	 * 
	 */
	private java.math.BigDecimal platformCommissionPayAmount;
	/**
	 * 
	 */
	private String buyerChannelCommissionPayId;
	/**
	 * 
	 */
	private java.math.BigDecimal buyerChannelCommissionPayAmount;
	/**
	 * 
	 */
	private String sellerChannelCommissionPayId;
	/**
	 * 
	 */
	private java.math.BigDecimal sellerChannelCommissionPayAmount;
	/**
	 * 
	 */
	private java.util.Date createTime;
	/**
	 * 
	 */
	private java.util.Date updateTime;


	private String belongShopCode;

	private BigDecimal belongShopCommission;

	private String parentCode;

	private String parentType;

	private BigDecimal parentCommission;

	private Integer buyerAgencyId;

	private Integer buyerPartyId;

	private String channel;

	private Integer buyerFromApplet;

	private String belongShopType;

	/**
	 *
	 */
	private java.lang.Integer belongAcctId;
	/**
	 *
	 */
	private java.lang.Integer parentAcctId;

	public String getBelongShopType() {
		return belongShopType;
	}

	public void setBelongShopType(String belongShopType) {
		this.belongShopType = belongShopType;
	}

	public Integer getBuyerFromApplet() {
		return buyerFromApplet;
	}

	public void setBuyerFromApplet(Integer buyerFromApplet) {
		this.buyerFromApplet = buyerFromApplet;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getBelongShopCode() {
		return belongShopCode;
	}

	public void setBelongShopCode(String belongShopCode) {
		this.belongShopCode = belongShopCode;
	}

	public BigDecimal getBelongShopCommission() {
		return belongShopCommission;
	}

	public void setBelongShopCommission(BigDecimal belongShopCommission) {
		this.belongShopCommission = belongShopCommission;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public BigDecimal getParentCommission() {
		return parentCommission;
	}

	public void setParentCommission(BigDecimal parentCommission) {
		this.parentCommission = parentCommission;
	}

	public Integer getBuyerAgencyId() {
		return buyerAgencyId;
	}

	public void setBuyerAgencyId(Integer buyerAgencyId) {
		this.buyerAgencyId = buyerAgencyId;
	}

	public Integer getBuyerPartyId() {
		return buyerPartyId;
	}

	public void setBuyerPartyId(Integer buyerPartyId) {
		this.buyerPartyId = buyerPartyId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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
	public String getDepositPayId(){
		return depositPayId;
	}
	
	/**
	 * 
	 */
	public void setDepositPayId(String depositPayId){
		this.depositPayId = depositPayId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getDepositPayAmount(){
		return depositPayAmount;
	}
	
	/**
	 * 
	 */
	public void setDepositPayAmount(java.math.BigDecimal depositPayAmount){
		this.depositPayAmount = depositPayAmount;
	}
	
	/**
	 * 
	 */
	public String getBuyerRemainPayId(){
		return buyerRemainPayId;
	}
	
	/**
	 * 
	 */
	public void setBuyerRemainPayId(String buyerRemainPayId){
		this.buyerRemainPayId = buyerRemainPayId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getBuyerRemainPayAmount(){
		return buyerRemainPayAmount;
	}
	
	/**
	 * 
	 */
	public void setBuyerRemainPayAmount(java.math.BigDecimal buyerRemainPayAmount){
		this.buyerRemainPayAmount = buyerRemainPayAmount;
	}
	
	/**
	 * 
	 */
	public String getBuyerCommissionPayId(){
		return buyerCommissionPayId;
	}
	
	/**
	 * 
	 */
	public void setBuyerCommissionPayId(String buyerCommissionPayId){
		this.buyerCommissionPayId = buyerCommissionPayId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getBuyerCommissionPayAmount(){
		return buyerCommissionPayAmount;
	}
	
	/**
	 * 
	 */
	public void setBuyerCommissionPayAmount(java.math.BigDecimal buyerCommissionPayAmount){
		this.buyerCommissionPayAmount = buyerCommissionPayAmount;
	}
	
	/**
	 * 
	 */
	public String getSellerCommissionPayId(){
		return sellerCommissionPayId;
	}
	
	/**
	 * 
	 */
	public void setSellerCommissionPayId(String sellerCommissionPayId){
		this.sellerCommissionPayId = sellerCommissionPayId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getSellerCommissionPayAmount(){
		return sellerCommissionPayAmount;
	}
	
	/**
	 * 
	 */
	public void setSellerCommissionPayAmount(java.math.BigDecimal sellerCommissionPayAmount){
		this.sellerCommissionPayAmount = sellerCommissionPayAmount;
	}
	
	/**
	 * 
	 */
	public String getBuyerAgencyCommissionPayId(){
		return buyerAgencyCommissionPayId;
	}
	
	/**
	 * 
	 */
	public void setBuyerAgencyCommissionPayId(String buyerAgencyCommissionPayId){
		this.buyerAgencyCommissionPayId = buyerAgencyCommissionPayId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getBuyerAgencyCommissionPayAmount(){
		return buyerAgencyCommissionPayAmount;
	}
	
	/**
	 * 
	 */
	public void setBuyerAgencyCommissionPayAmount(java.math.BigDecimal buyerAgencyCommissionPayAmount){
		this.buyerAgencyCommissionPayAmount = buyerAgencyCommissionPayAmount;
	}
	
	/**
	 * 
	 */
	public String getSellerAgencyCommissionPayId(){
		return sellerAgencyCommissionPayId;
	}
	
	/**
	 * 
	 */
	public void setSellerAgencyCommissionPayId(String sellerAgencyCommissionPayId){
		this.sellerAgencyCommissionPayId = sellerAgencyCommissionPayId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getSellerAgencyCommissionPayAmount(){
		return sellerAgencyCommissionPayAmount;
	}
	
	/**
	 * 
	 */
	public void setSellerAgencyCommissionPayAmount(java.math.BigDecimal sellerAgencyCommissionPayAmount){
		this.sellerAgencyCommissionPayAmount = sellerAgencyCommissionPayAmount;
	}
	
	/**
	 * 
	 */
	public String getPlatformCommissionPayId(){
		return platformCommissionPayId;
	}
	
	/**
	 * 
	 */
	public void setPlatformCommissionPayId(String platformCommissionPayId){
		this.platformCommissionPayId = platformCommissionPayId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getPlatformCommissionPayAmount(){
		return platformCommissionPayAmount;
	}
	
	/**
	 * 
	 */
	public void setPlatformCommissionPayAmount(java.math.BigDecimal platformCommissionPayAmount){
		this.platformCommissionPayAmount = platformCommissionPayAmount;
	}
	
	/**
	 * 
	 */
	public String getBuyerChannelCommissionPayId(){
		return buyerChannelCommissionPayId;
	}
	
	/**
	 * 
	 */
	public void setBuyerChannelCommissionPayId(String buyerChannelCommissionPayId){
		this.buyerChannelCommissionPayId = buyerChannelCommissionPayId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getBuyerChannelCommissionPayAmount(){
		return buyerChannelCommissionPayAmount;
	}
	
	/**
	 * 
	 */
	public void setBuyerChannelCommissionPayAmount(java.math.BigDecimal buyerChannelCommissionPayAmount){
		this.buyerChannelCommissionPayAmount = buyerChannelCommissionPayAmount;
	}
	
	/**
	 * 
	 */
	public String getSellerChannelCommissionPayId(){
		return sellerChannelCommissionPayId;
	}
	
	/**
	 * 
	 */
	public void setSellerChannelCommissionPayId(String sellerChannelCommissionPayId){
		this.sellerChannelCommissionPayId = sellerChannelCommissionPayId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getSellerChannelCommissionPayAmount(){
		return sellerChannelCommissionPayAmount;
	}
	
	/**
	 * 
	 */
	public void setSellerChannelCommissionPayAmount(java.math.BigDecimal sellerChannelCommissionPayAmount){
		this.sellerChannelCommissionPayAmount = sellerChannelCommissionPayAmount;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	
	/**
	 * 
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

	public Integer getBelongAcctId() {
		return belongAcctId;
	}

	public void setBelongAcctId(Integer belongAcctId) {
		this.belongAcctId = belongAcctId;
	}

	public Integer getParentAcctId() {
		return parentAcctId;
	}

	public void setParentAcctId(Integer parentAcctId) {
		this.parentAcctId = parentAcctId;
	}
}
