package com._360pai.core.vo.enrolling.web;

import com._360pai.core.common.constants.EnrollingEnum;

import java.io.Serializable;

/**
 * @author: liuhaolei
 * @Title: GuarantyEnrollingActivityVO
 * @ProjectName: core-service
 * @Description: 首页预招商列表
 * @Date: 2018-08-28
 */
public class PersonalEnrollingActivityVO implements Serializable{
	
	private String typeDesc;//类型描述	
	
	private String type;//类型
	
	private String endAt;//截止时间
	
	private String beginAt;//截止时间
	
	private String images;//图片
	
	private String id;//主键
	
	private String name;//名称
	
	private String depositNum;//报名人数
	
	private String status;//状态
	
	private String statusDesc;//状态描述
	
	private String focusNum;
	  
	private String browseNum;
	 
	private String cityName;

	private String amount;//展示金额

	private String amountDesc;//金额的描述
	
	private String rejectReason;
	
	private String depositStatus;
	
	private String depositStatusDesc;

	private String proof;//凭证

	private String endPayTime;//支付结束时间


	private Integer shopId;//店铺id

	private String createdAt;//创建时间



	private String shopName;//店铺名称


	private Long  endTimestamp;


	private String enrollingAmount;//

	public String getEnrollingAmount() {
		return enrollingAmount;
	}

	public void setEnrollingAmount(String enrollingAmount) {
		this.enrollingAmount = enrollingAmount;
	}

	public Long getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(Long endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getEndPayTime() {
		return endPayTime;
	}

	public void setEndPayTime(String endPayTime) {
		this.endPayTime = endPayTime;
	}

	public void setDepositStatusDesc(String depositStatusDesc) {
		this.depositStatusDesc = depositStatusDesc;
	}

	public String getProof() {
		return proof;
	}

	public void setProof(String proof) {
		this.proof = proof;
	}

	public String getDepositStatus() {
		return depositStatus;
	}

	public String getDepositStatusDesc() {
		return depositStatusDesc;
	}

	public void setDepositStatus(String depositStatus) {
		this.depositStatus = depositStatus;
	}

	public void setDepositStatusDesc(String type, String depositStatus) {
		if(EnrollingEnum.ENROLLING_TYPE.MORTGAGED_PROPERTY.getType().equals(type)) {
			this.depositStatusDesc = EnrollingEnum.PAY_STATUS.getDesc(depositStatus);
		}else {
			this.depositStatusDesc = EnrollingEnum.PAY_STATUS.NONE_PAY.getTypeName();
		}
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getAmountDesc() {
		return amountDesc;
	}

	public void setAmountDesc(String amountDesc) {
		this.amountDesc = amountDesc;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getFocusNum() {
		return focusNum;
	}

	public String getBrowseNum() {
		return browseNum;
	}
	
	public void setFocusNum(String focusNum) {
		this.focusNum = focusNum;
	}
	
	public void setBrowseNum(String browseNum) {
		this.browseNum = browseNum;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEndAt() {
		return endAt;
	}

	public void setEndAt(String endAt) {
		this.endAt = endAt;
	}


	public String getBeginAt() {
		return beginAt;
	}

	public void setBeginAt(String beginAt) {
		this.beginAt = beginAt;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepositNum() {
		return depositNum;
	}

	public void setDepositNum(String depositNum) {
		this.depositNum = depositNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
}
