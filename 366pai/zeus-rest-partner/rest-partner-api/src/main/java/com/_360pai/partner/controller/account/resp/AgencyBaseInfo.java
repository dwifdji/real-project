package com._360pai.partner.controller.account.resp;

/**
 * 
 *  create by liuhaolei on 2018/09/14
 *  机构详情相应实体
 */
public class AgencyBaseInfo {

	private Integer accountId;
	
	private Integer agencyId;
	
	private String agencyName;
	
	private String mobile;
	
	private String address;
	
	private String code;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
