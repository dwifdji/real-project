
package com._360pai.core.model.asset;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分25秒
 */
public class AssetRejectRecord implements java.io.Serializable{

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
