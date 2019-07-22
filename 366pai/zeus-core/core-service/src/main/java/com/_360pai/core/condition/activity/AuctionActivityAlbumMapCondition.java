
package com._360pai.core.condition.activity;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
public class AuctionActivityAlbumMapCondition implements DaoCondition{

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