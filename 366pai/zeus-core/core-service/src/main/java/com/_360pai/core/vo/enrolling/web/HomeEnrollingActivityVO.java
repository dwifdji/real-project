package com._360pai.core.vo.enrolling.web;

import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.core.common.constants.EnrollingEnum;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;


/**
 * @author: liuhaolei
 * @Title: HomeEnrollingActivityVO
 * @ProjectName: core-service
 * @Description: 首页预招商查询列表和首页预招商排行榜
 * @Date: 2018-08-28
 */
public class HomeEnrollingActivityVO implements Serializable{
	
	private String cityName;//城市名称
	
	private String cityLabel;//所在地字段
	
	private String mortgageLabel;//抵押情况字段
	
	private String mortgageValue;//抵押情况真正的数值
	
	private String deposit;	//保证金
	
	private String depositLabel; //金额类型
	
	private String beginAt;//开始时间
	
	private String status;//状态
	
	private String statusName;
	
	private String images;//图片地址
	
	private String endAt;//结束时间
	
	private String id;
	
	private String name;
	
	private String refPrice;//市值估计
	
	private String type;//预招商类型
	
	private String typeName;
	
	private String focusNum;
	
	private String browseNum;
	
	private String depositNum;
	
	private String rejectReason;
	
	private String depositStatus;

	private String proof;

	private String endPayTime;//支付结束时间

	private String createdAt;

	private String depositTime;

	private String source;

	private Integer resourceId;


	private String shopName;


	private String enrollingDeposit;


	private String enrollingRefPrice;

	public String getEnrollingDeposit() {
		return enrollingDeposit;
	}

	public void setEnrollingDeposit(String enrollingDeposit) {
		this.enrollingDeposit = enrollingDeposit;
	}

	public String getEnrollingRefPrice() {
		return enrollingRefPrice;
	}

	public void setEnrollingRefPrice(String enrollingRefPrice) {
		this.enrollingRefPrice = enrollingRefPrice;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDepositTime() {
		return depositTime;
	}

	public void setDepositTime(String depositTime) {
		this.depositTime = depositTime;
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

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public void setDepositStatus(String depositStatus) {
		this.depositStatus = depositStatus;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getFocusNum() {
		return focusNum;
	}

	public String getBrowseNum() {
		return browseNum;
	}

	public void setFocusNum(String focusNum) {
		if(focusNum == null) {
			this.focusNum = "0";
		}else {
			this.focusNum = focusNum;
		}
		
	}

	public void setBrowseNum(String browseNum) {
		if(browseNum == null) {
			this.browseNum = "0";
		}else {
			this.browseNum = browseNum;
		}
		
	}

	
	public String getDepositNum() {
		return depositNum;
	}

	public void setDepositNum(String depositNum) {
		if(depositNum == null) {
			this.depositNum = "0";
		}else {
			this.depositNum = depositNum;
		}
		
	}

	public String getTypeName() {
		return typeName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityLabel() {
		return cityLabel;
	}

	public void setCityLabel(String cityLabel) {
		this.cityLabel = cityLabel;
	}

	public String getMortgageLabel() {
		return mortgageLabel;
	}

	public void setMortgageLabel(String mortgageLabel) {
		this.mortgageLabel = mortgageLabel;
	}

	public String getMortgageValue() {
		return mortgageValue;
	}

	public void setMortgageValue(String mortgageValue) {
		if(StringUtils.isNotBlank(mortgageValue)) {
			this.mortgageValue = EnrollingEnum.ENROLLING_GUARANTEE.getDesc(mortgageValue);
		}else {
			this.mortgageValue = mortgageValue;
		}
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public String getDepositLabel() {
		return depositLabel;
	}

	public void setDepositLabel(String depositLabel) {
		this.depositLabel = depositLabel;
	}

	public String getBeginAt() {
		return beginAt;
	}

	public void setBeginAt(String beginAt) {
		this.beginAt = beginAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.statusName = EnrollingEnum.STATUS.getDesc(status);
		this.status = EnrollingEnum.QUERY_STATUS.getType(status);
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getEndAt() {
		return endAt;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setEndAt(String endAt) {
		this.endAt = endAt;
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

	public String getRefPrice() {
		return refPrice;
	}

	public void setRefPrice(String refPrice) {
		this.refPrice = refPrice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.typeName = EnrollingEnum.ENROLLING_TYPE.getDesc(type);
		this.type = type;
	}
}
