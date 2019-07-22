
package com._360pai.core.model.asset;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月26日 16时17分16秒
 */
@Data
public class TAssetCategoryOption implements java.io.Serializable{

	/**
	 *  自增
	 */
	private Integer id;
	/**
	 * 拍品类型ID
	 */
	private Integer assetCategoryId;
	/**
	 * 子类型名称
	 */
	private String name;
	/**
	 * 是否启用  0：不启用  1：启用
	 */
	private Integer enable;
	private Integer orderNum;

	/**
	 *  自增
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 *  自增
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 拍品类型ID
	 */
	public Integer getAssetCategoryId(){
		return assetCategoryId;
	}
	
	/**
	 * 拍品类型ID
	 */
	public void setAssetCategoryId(Integer assetCategoryId){
		this.assetCategoryId = assetCategoryId;
	}
	
	/**
	 * 子类型名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 子类型名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 是否启用  0：不启用  1：启用
	 */
	public Integer getEnable(){
		return enable;
	}
	
	/**
	 * 是否启用  0：不启用  1：启用
	 */
	public void setEnable(Integer enable){
		this.enable = enable;
	}

}
