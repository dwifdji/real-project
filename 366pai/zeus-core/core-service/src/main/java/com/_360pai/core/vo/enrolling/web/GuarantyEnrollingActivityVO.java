package com._360pai.core.vo.enrolling.web;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: liuhaolei
 * @Title: GuarantyEnrollingActivityVO
 * @ProjectName: core-service
 * @Description: 首页预招商列表
 * @Date: 2018-08-28
 */
public class GuarantyEnrollingActivityVO implements Serializable{
	
	private String cityName;//所在地
	
	private String deposit;	//保证金		
	
	private String endAt;//截止时间
	
	private Map<String, Object> estra;//具体图片信息
	
	private String favoriteNumber;
	
	private String id;
	
	private String name;
	
	private String participantNumber;//报名人数
	
	private String status;//状态
	
	private String viewCounts;//浏览量

	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public Map<String, Object> getEstra() {
		return estra;
	}

	public void setEstra(Map<String, Object> estra) {
		this.estra = estra;
	}

	public String getFavoriteNumber() {
		return favoriteNumber;
	}

	public void setFavoriteNumber(String favoriteNumber) {
		this.favoriteNumber = favoriteNumber;
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

	public String getParticipantNumber() {
		return participantNumber;
	}

	public void setParticipantNumber(String participantNumber) {
		this.participantNumber = participantNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEndAt() {
		return endAt;
	}

	public void setEndAt(String endAt) {
		this.endAt = endAt;
	}

	public String getViewCounts() {
		return viewCounts;
	}

	public void setViewCounts(String viewCounts) {
		this.viewCounts = viewCounts;
	}

}
