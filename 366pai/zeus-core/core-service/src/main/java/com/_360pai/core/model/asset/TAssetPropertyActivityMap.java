
package com._360pai.core.model.asset;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月05日 10时01分32秒
 */
public class TAssetPropertyActivityMap implements java.io.Serializable{

	/**
	 * 主键自增
	 */
	private Integer id;
	/**
	 * 标的属性ID
	 */
	private Integer assetPropertyId;
	/**
	 * 活动ID
	 */
	private Integer activityId;
	/**
	 * 是否上线 0:不上线  1:上线
	 */
	private Integer isOnline;
	
	/**
	 * 主键自增
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键自增
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 标的属性ID
	 */
	public Integer getAssetPropertyId(){
		return assetPropertyId;
	}
	
	/**
	 * 标的属性ID
	 */
	public void setAssetPropertyId(Integer assetPropertyId){
		this.assetPropertyId = assetPropertyId;
	}
	
	/**
	 * 活动ID
	 */
	public Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 活动ID
	 */
	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 是否上线 0:不上线  1:上线
	 */
	public Integer getIsOnline(){
		return isOnline;
	}
	
	/**
	 * 是否上线 0:不上线  1:上线
	 */
	public void setIsOnline(Integer isOnline){
		this.isOnline = isOnline;
	}

}
