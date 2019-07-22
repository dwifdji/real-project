
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年10月16日 13时08分26秒
 */
public class AssetDataCopyCondition implements DaoCondition{

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