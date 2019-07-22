package com._360pai.core.vo.enrolling;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com._360pai.core.common.constants.EnrollingEnum;

public class AgencyActivityDetailVO implements Serializable{

	private String id;// 主键
	private String name;// 预招商活动名称
	private String status;// 预招商活动状态
	private String beginAt;// 提交时间
	private String cityName;// 资产所在城市
	private String type;// 预招商类型
	private String agencyName;// 送拍机构
	private String contactPhone;// 委托方联系方式
	private String refPrice;// 市场参考价
	private String endAt;// 报名结束时间
	private String commissionPercent;// 预招商成交佣金
	private String extra;// 预览图片
	private String remark;// 委托方备注
	private String descriptionDoc;// 资产描述附件
	private String code;// 预招商活动编号
	private String assetPropertyName;// 拍品属性
	private String partyName;// 委托方
	private String userName;//用户名称
	private String comName;//公司名称
	private String contactName;// 委托方联系人
	private String contactQq;// 委托方联系QQ
	private String deposit;// 保证金
	private String expireAt;// 预计处置完成时间
	private String visibilityLevel;// 活动可见性
	private String statusDesc;//状态描述
	
	public String getStatusDesc() {
		return statusDesc;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		if(!StringUtils.isBlank(userName)) {
			this.partyName = userName;
		}
		this.userName = userName;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		if(!StringUtils.isBlank(comName)) {
			this.partyName = comName;
		}
		this.comName = comName;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = EnrollingEnum.QUERY_STATUS.getType(status);
		this.statusDesc = EnrollingEnum.STATUS.getDesc(status);
	}
	 
	public String getBeginAt() {
		return beginAt;
	}
	public void setBeginAt(String beginAt) {
		this.beginAt = beginAt;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = EnrollingEnum.ENROLLING_TYPE.getDesc(type);
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getRefPrice() {
		return refPrice;
	}
	public void setRefPrice(String refPrice) {
		this.refPrice = refPrice;
	}
	public String getEndAt() {
		return endAt;
	}
	public void setEndAt(String endAt) {
		this.endAt = endAt;
	}
	public String getCommissionPercent() {
		return commissionPercent;
	}
	public void setCommissionPercent(String commissionPercent) {
		this.commissionPercent = commissionPercent;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDescriptionDoc() {
		return descriptionDoc;
	}
	public void setDescriptionDoc(String descriptionDoc) {
		this.descriptionDoc = descriptionDoc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAssetPropertyName() {
		return assetPropertyName;
	}
	public void setAssetPropertyName(String assetPropertyName) {
		this.assetPropertyName = assetPropertyName;
	}
	public String getPartyName() {
		
		return partyName;
	}
	
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactQq() {
		return contactQq;
	}
	public void setContactQq(String contactQq) {
		this.contactQq = contactQq;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public String getExpireAt() {
		return expireAt;
	}
	public void setExpireAt(String expireAt) {
		this.expireAt = expireAt;
	}
	public String getVisibilityLevel() {
		return visibilityLevel;
	}
	public void setVisibilityLevel(String visibilityLevel) {
		this.visibilityLevel = visibilityLevel;
	}
	

}
