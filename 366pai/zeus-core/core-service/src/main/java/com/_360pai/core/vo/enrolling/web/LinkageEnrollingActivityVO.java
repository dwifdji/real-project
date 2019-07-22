package com._360pai.core.vo.enrolling.web;

import java.io.Serializable;

/**
 * @author: liuhaolei
 * @Title: LinkageEnrollingActivityVO
 * @ProjectName: core-service
 * @Description: 全网联拍列表VO
 * @Date: 2018-08-28
 */
public class LinkageEnrollingActivityVO implements Serializable{
	
	private String cityName;//所在地
	
	private String provinceName;//省份
	
	private String logoUrl;//logo地址
	
	private String viewCount;//浏览量
	
	private String participantNumber;//报名量
	
	private String agencyName;//机构名称

	private String agencyCode;//机构代码

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getViewCount() {

		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public String getParticipantNumber() {
		return participantNumber;
	}

	public void setParticipantNumber(String participantNumber) {
		this.participantNumber = participantNumber;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
}
