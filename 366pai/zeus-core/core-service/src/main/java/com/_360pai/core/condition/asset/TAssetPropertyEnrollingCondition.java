
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月31日 11时03分31秒
 */
public class TAssetPropertyEnrollingCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 
	 */
	private java.lang.Integer assetPropertyId;
	/**
	 * 
	 */
	private java.lang.Integer activityId;
	/**
	 * 
	 */
	private java.util.Date crteateAt;
	
	/**
	 * 
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getAssetPropertyId(){
		return assetPropertyId;
	}
	
	/**
	 * 
	 */
	public void setAssetPropertyId(java.lang.Integer assetPropertyId){
		this.assetPropertyId = assetPropertyId;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 
	 */
	public void setActivityId(java.lang.Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCrteateAt(){
		return crteateAt;
	}
	
	/**
	 * 
	 */
	public void setCrteateAt(java.util.Date crteateAt){
		this.crteateAt = crteateAt;
	}

}