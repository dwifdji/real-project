
package com._360pai.core.condition.fdd;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月03日 14时51分14秒
 */
public class GatewayFddSignKeyworkCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 签章类型 1 委托协议 2 委托补充协议 3 成交协议 4 预招商协议
	 */
	private java.lang.String type;
	/**
	 * 角色的类型
	 */
	private java.lang.String roleType;
	/**
	 * 关键字的值
	 */
	private java.lang.String keyName;
	
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
	 * 签章类型 1 委托协议 2 委托补充协议 3 成交协议 4 预招商协议
	 */
	public java.lang.String getType(){
		return type;
	}
	
	/**
	 * 签章类型 1 委托协议 2 委托补充协议 3 成交协议 4 预招商协议
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	
	/**
	 * 角色的类型
	 */
	public java.lang.String getRoleType(){
		return roleType;
	}
	
	/**
	 * 角色的类型
	 */
	public void setRoleType(java.lang.String roleType){
		this.roleType = roleType;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
}