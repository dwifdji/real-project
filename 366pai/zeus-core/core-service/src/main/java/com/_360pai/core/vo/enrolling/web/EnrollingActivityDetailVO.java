package com._360pai.core.vo.enrolling.web;

import java.io.Serializable;

/**
 * @author: liuhaolei
 * @Title: EnrollingActivityDetailVO
 * @ProjectName: core-serviceO
 * @Description: 首页预招商详情VO
 * @Date: 2018-08-28
 */
public class EnrollingActivityDetailVO implements Serializable{
	
	private String cityName;//所在地
	
	private String deposit;	//保证金		
	
	private String status;//状态
	
	private String beginAt;//开始时间
	
	private String images;//图片地址
	
	private String endAt;//结束时间
	
	private String id;
	
	private String name;
	
	private String browse;//浏览量
	
	private String ref_price;//市值估计
	
	private String remind;//设置提醒

	private String apply;//报名量
	
	private String focus;//关注
	
	private String agency;//送拍机构

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getEndAt() {
		return endAt;
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

	public String getBrowse() {
		return browse;
	}

	public void setBrowse(String browse) {
		this.browse = browse;
	}

	public String getRef_price() {
		return ref_price;
	}

	public void setRef_price(String ref_price) {
		this.ref_price = ref_price;
	}

	public String getRemind() {
		return remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}

	public String getApply() {
		return apply;
	}

	public void setApply(String apply) {
		this.apply = apply;
	}

	public String getFocus() {
		return focus;
	}

	public void setFocus(String focus) {
		this.focus = focus;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}
}
