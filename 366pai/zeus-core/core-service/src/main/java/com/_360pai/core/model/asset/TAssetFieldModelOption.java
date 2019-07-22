
package com._360pai.core.model.asset;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月13日 17时39分46秒
 */
public class TAssetFieldModelOption implements java.io.Serializable{

	/**
	 * 自增主键
	 */
	private Integer id;
	/**
	 * 模块ID
	 */
	private Integer modelId;
	/**
	 * 子模块名称
	 */
	private String name;
	/**
	 * 是否可扩展
	 */
	private Integer extensible;
	
	/**
	 * 自增主键
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 自增主键
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 模块ID
	 */
	public Integer getModelId(){
		return modelId;
	}
	
	/**
	 * 模块ID
	 */
	public void setModelId(Integer modelId){
		this.modelId = modelId;
	}
	
	/**
	 * 子模块名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 子模块名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 是否可扩展
	 */
	public Integer getExtensible(){
		return extensible;
	}
	
	/**
	 * 是否可扩展
	 */
	public void setExtensible(Integer extensible){
		this.extensible = extensible;
	}

}
