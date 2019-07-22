
package com._360pai.core.vo.activity;

import java.util.Date;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分27秒
 */
public class AuctionActivityVo implements java.io.Serializable{

	/**
	 *
	 */
	private Date beginAt;
	/**
	 *
	 */
	private Date endAt;
	/**
	 *
	 */
	private String activityName;
	/**
	 *
	 */
	private String statusStr;
	/**
	 *
	 */
	private String status;
	/**
	 *
	 */
	private String deposit;
	/**
	 *
	 */
	private String mode;
	/**
	 *
	 */
	private String modeStr;
	/**
	 *
	 */
	private String categoryName;
	/**
	 *
	 */
	private String propertyName;
	/**
	 *
	 */
	private String imageUrl;
	/**
	 *
	 */
	private String cityName;
	/**
	 *
	 */
	private Integer activityId;
	/**
	 *
	 */
	private Integer id;
	/**
	 *
	 */
	private Integer viewCount;
	/**
	 *
	 */
	private Integer depositCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDepositCount() {
		return depositCount;
	}

	public void setDepositCount(Integer depositCount) {
		this.depositCount = depositCount;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getBeginAt() {
		return beginAt;
	}

	public void setBeginAt(Date beginAt) {
		this.beginAt = beginAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getModeStr() {
		return modeStr;
	}

	public void setModeStr(String modeStr) {
		this.modeStr = modeStr;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
}
