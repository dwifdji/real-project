
package com._360pai.core.model.asset;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分24秒
 */
public class AssetData implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private com.alibaba.fastjson.JSONObject content;
	/**
	 * 
	 */
	private Integer assetId;
	
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
	public com.alibaba.fastjson.JSONObject getContent(){
		return content;
	}
	
	/**
	 * 
	 */
	public void setContent(com.alibaba.fastjson.JSONObject content){
		this.content = content;
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

}
