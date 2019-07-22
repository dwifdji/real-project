
package com._360pai.core.model.activity;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分25秒
 */
public class AuctionActivityAlbumMap implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer albumId;
	/**
	 * 
	 */
	private Integer activityId;
	/**
	 * 活动类型（拍卖 auction，招商 enrolling）
	 */
	private java.lang.String activityType;
	/**
	 *
	 */
	private java.lang.Integer orderNum;
	
	/**
	 * 
	 */
	public Integer getAlbumId(){
		return albumId;
	}
	
	/**
	 * 
	 */
	public void setAlbumId(Integer albumId){
		this.albumId = albumId;
	}
	
	/**
	 * 
	 */
	public Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 
	 */
	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}
