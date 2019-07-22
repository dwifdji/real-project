package com._360pai.core.vo.enrolling.web;

import java.io.Serializable;

/**
 * @author: liuhaolei
 * @Title: EnrollingAnnouncementVO
 * @ProjectName: core-service
 * @Description: 公告模板信息
 * @Date: 2018-09-29
 */
public class EnrollingAnnouncementVO implements Serializable{
	
	private String partyName;
	
	private String agencyName;
	
	private String agencyPhone;
	
	private String contactName;
	
	private String contactPhone;
	
	private String beginAt;
	
	private String endAt;
	
	private String deposit;
	
	private String name;
	
	private String expireAt;

	private String projectManager;

	private String projectPhone;

	private String userAgencyName;

	private Integer thirdPartyStatus;

	private String webName;

	private String unionAgencyName;

	private String type;

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public String getUserAgencyName() {
		return userAgencyName;
	}

	public void setUserAgencyName(String userAgencyName) {
		this.userAgencyName = userAgencyName;
	}

	public Integer getThirdPartyStatus() {
		return thirdPartyStatus;
	}

	public void setThirdPartyStatus(Integer thirdPartyStatus) {
		this.thirdPartyStatus = thirdPartyStatus;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getProjectPhone() {
		return projectPhone;
	}

	public void setProjectPhone(String projectPhone) {
		this.projectPhone = projectPhone;
	}

	public String getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(String expireAt) {
		this.expireAt = expireAt;
	}

	public String getPartyName() {
		return partyName;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public String getAgencyPhone() {
		return agencyPhone;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public String getBeginAt() {
		return beginAt;
	}

	public String getEndAt() {
		return endAt;
	}

	public String getDeposit() {
		return deposit;
	}

	public String getName() {
		return name;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public void setAgencyPhone(String agencyPhone) {
		this.agencyPhone = agencyPhone;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setBeginAt(String beginAt) {
		this.beginAt = beginAt;
	}

	public void setEndAt(String endAt) {
		this.endAt = endAt;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnionAgencyName() {
		return unionAgencyName;
	}

	public void setUnionAgencyName(String unionAgencyName) {
		this.unionAgencyName = unionAgencyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
