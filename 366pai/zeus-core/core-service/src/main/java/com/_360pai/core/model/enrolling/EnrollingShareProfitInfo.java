
package com._360pai.core.model.enrolling;

import java.math.BigDecimal;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年10月16日 13时57分17秒
 */
public class EnrollingShareProfitInfo implements java.io.Serializable{

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
	private java.math.BigDecimal amount;

	/**
	 *
	 */
	private String status;


	private String sellerAgencyName;
	/**
	 *
	 */
	private String sellerMemCode;
	/**
	 *
	 */
	private String sellerPercent;
	/**
	 *
	 */
	private String buyerAgencyName;
	/**
	 *
	 */
	private String buyerMemCode;


	/**
	 *
	 */
	private String buyerPercent;


	private String sellerAgencyId;


	private String buyerAgencyId;


	public String getSellerAgencyId() {
		return sellerAgencyId;
	}

	public void setSellerAgencyId(String sellerAgencyId) {
		this.sellerAgencyId = sellerAgencyId;
	}

	public String getBuyerAgencyId() {
		return buyerAgencyId;
	}

	public void setBuyerAgencyId(String buyerAgencyId) {
		this.buyerAgencyId = buyerAgencyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSellerAgencyName() {
		return sellerAgencyName;
	}

	public void setSellerAgencyName(String sellerAgencyName) {
		this.sellerAgencyName = sellerAgencyName;
	}

	public String getSellerMemCode() {
		return sellerMemCode;
	}

	public void setSellerMemCode(String sellerMemCode) {
		this.sellerMemCode = sellerMemCode;
	}

	public String getSellerPercent() {
		return sellerPercent;
	}

	public void setSellerPercent(String sellerPercent) {
		this.sellerPercent = sellerPercent;
	}

	public String getBuyerAgencyName() {
		return buyerAgencyName;
	}

	public void setBuyerAgencyName(String buyerAgencyName) {
		this.buyerAgencyName = buyerAgencyName;
	}

	public String getBuyerMemCode() {
		return buyerMemCode;
	}

	public void setBuyerMemCode(String buyerMemCode) {
		this.buyerMemCode = buyerMemCode;
	}

	public String getBuyerPercent() {
		return buyerPercent;
	}

	public void setBuyerPercent(String buyerPercent) {
		this.buyerPercent = buyerPercent;
	}
}
