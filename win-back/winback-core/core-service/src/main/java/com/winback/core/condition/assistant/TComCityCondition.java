
package com.winback.core.condition.assistant;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月16日 15时40分03秒
 */
public class TComCityCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 城市code
	 */
	private java.lang.String code;
	/**
	 * 城市名称
	 */
	private java.lang.String name;
	/**
	 * 省份code
	 */
	private java.lang.String provinceCode;
	/**
	 * 
	 */
	private java.lang.Boolean deleteFlag;
	
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
	 * 城市code
	 */
	public java.lang.String getCode(){
		return code;
	}
	
	/**
	 * 城市code
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	
	/**
	 * 城市名称
	 */
	public java.lang.String getName(){
		return name;
	}
	
	/**
	 * 城市名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	
	/**
	 * 省份code
	 */
	public java.lang.String getProvinceCode(){
		return provinceCode;
	}
	
	/**
	 * 省份code
	 */
	public void setProvinceCode(java.lang.String provinceCode){
		this.provinceCode = provinceCode;
	}
	
	/**
	 * 
	 */
	public java.lang.Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 
	 */
	public void setDeleteFlag(java.lang.Boolean deleteFlag){
		this.deleteFlag = deleteFlag;
	}

}