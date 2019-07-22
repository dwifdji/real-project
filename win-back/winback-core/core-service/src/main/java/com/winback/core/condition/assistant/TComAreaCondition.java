
package com.winback.core.condition.assistant;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月16日 15时40分03秒
 */
public class TComAreaCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 区域code
	 */
	private java.lang.String code;
	/**
	 * 区域名称
	 */
	private java.lang.String name;
	/**
	 * 管理的城市code
	 */
	private java.lang.String cityCode;
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
	 * 区域code
	 */
	public java.lang.String getCode(){
		return code;
	}
	
	/**
	 * 区域code
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	
	/**
	 * 1 诉讼步骤 2 执行步骤
	 */
	public java.lang.String getName(){
		return name;
	}
	
	/**
	 * 1 诉讼步骤 2 执行步骤
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	
	/**
	 * 管理的城市code
	 */
	public java.lang.String getCityCode(){
		return cityCode;
	}
	
	/**
	 * 管理的城市code
	 */
	public void setCityCode(java.lang.String cityCode){
		this.cityCode = cityCode;
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