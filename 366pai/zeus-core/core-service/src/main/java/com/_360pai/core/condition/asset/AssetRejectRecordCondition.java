
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
public class AssetRejectRecordCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer assetId;
	/**
	 * 
	 */
	private String reason;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	
	/**
	 * 
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 
	 */
	public Integer getAssetId(){
		return assetId;
	}
	
	/**
	 * 
	 */
	public void setAssetId(Integer assetId){
		this.assetId = assetId;
	}
	
	/**
	 * 
	 */
	public String getReason(){
		return reason;
	}
	
	/**
	 * 
	 */
	public void setReason(String reason){
		this.reason = reason;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreatedAt(){
		return createdAt;
	}
	
	/**
	 * 
	 */
	public void setCreatedAt(java.util.Date createdAt){
		this.createdAt = createdAt;
	}

}