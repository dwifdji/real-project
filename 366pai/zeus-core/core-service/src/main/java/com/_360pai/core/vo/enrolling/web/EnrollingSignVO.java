package com._360pai.core.vo.enrolling.web;

import java.io.Serializable;

public class EnrollingSignVO implements Serializable{
	
	private String agencyFddId;//送拍机构法大大ID

	private String userFddId;//用户法大大id

	private String comFddId;//公司法大大id

	private String type;//预招商类型

	private String partyId;//

	private Integer thirdPartyStatus;

	private String userAgencyFddId;

	public String getUserAgencyFddId() {
		return userAgencyFddId;
	}

	public void setUserAgencyFddId(String userAgencyFddId) {
		this.userAgencyFddId = userAgencyFddId;
	}

	public Integer getThirdPartyStatus() {
		return thirdPartyStatus;
	}

	public void setThirdPartyStatus(Integer thirdPartyStatus) {
		this.thirdPartyStatus = thirdPartyStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getComFddId() {
		return comFddId;
	}

	public void setComFddId(String comFddId) {
		this.comFddId = comFddId;
	}

	public String getAgencyFddId() {
		return agencyFddId;
	}

	public void setAgencyFddId(String agencyFddId) {
		this.agencyFddId = agencyFddId;
	}

	public String getUserFddId() {
		return userFddId;
	}

	public void setUserFddId(String userFddId) {
		this.userFddId = userFddId;
	}
}
