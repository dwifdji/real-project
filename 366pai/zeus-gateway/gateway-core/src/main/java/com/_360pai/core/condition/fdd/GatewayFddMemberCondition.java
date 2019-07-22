
package com._360pai.core.condition.fdd;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月15日 16时37分04秒
 */
public class GatewayFddMemberCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 平台用户id
	 */
	private java.lang.Integer memberId;
	/**
	 * 法大大开户用户名
	 */
	private java.lang.String customerName;
	/**
	 * 开户手机号码
	 */
	private java.lang.String mobile;
	/**
	 * 开户证件类型
	 */
	private java.lang.String identType;
	/**
	 * 开户证件号码
	 */
	private java.lang.String idCard;
	/**
	 * 开户邮件号码
	 */
	private java.lang.String email;
	/**
	 * 法大大的开户id
	 */
	private java.lang.String fddId;
	/**
	 * 开户状态
	 */
	private java.lang.Integer status;
	/**
	 * 
	 */
	private java.lang.String msg;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	
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
	 * 平台用户id
	 */
	public java.lang.Integer getMemberId(){
		return memberId;
	}
	
	/**
	 * 平台用户id
	 */
	public void setMemberId(java.lang.Integer memberId){
		this.memberId = memberId;
	}
	
	/**
	 * 法大大开户用户名
	 */
	public java.lang.String getCustomerName(){
		return customerName;
	}
	
	/**
	 * 法大大开户用户名
	 */
	public void setCustomerName(java.lang.String customerName){
		this.customerName = customerName;
	}
	
	/**
	 * 开户手机号码
	 */
	public java.lang.String getMobile(){
		return mobile;
	}
	
	/**
	 * 开户手机号码
	 */
	public void setMobile(java.lang.String mobile){
		this.mobile = mobile;
	}
	
	/**
	 * 开户证件类型
	 */
	public java.lang.String getIdentType(){
		return identType;
	}
	
	/**
	 * 开户证件类型
	 */
	public void setIdentType(java.lang.String identType){
		this.identType = identType;
	}
	
	/**
	 * 开户证件号码
	 */
	public java.lang.String getIdCard(){
		return idCard;
	}
	
	/**
	 * 开户证件号码
	 */
	public void setIdCard(java.lang.String idCard){
		this.idCard = idCard;
	}
	
	/**
	 * 开户邮件号码
	 */
	public java.lang.String getEmail(){
		return email;
	}
	
	/**
	 * 开户邮件号码
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
	}
	
	/**
	 * 法大大的开户id
	 */
	public java.lang.String getFddId(){
		return fddId;
	}
	
	/**
	 * 法大大的开户id
	 */
	public void setFddId(java.lang.String fddId){
		this.fddId = fddId;
	}
	
	/**
	 * 开户状态
	 */
	public java.lang.Integer getStatus(){
		return status;
	}
	
	/**
	 * 开户状态
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
	
	/**
	 * 
	 */
	public java.lang.String getMsg(){
		return msg;
	}
	
	/**
	 * 
	 */
	public void setMsg(java.lang.String msg){
		this.msg = msg;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	
	/**
	 * 更新时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 更新时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}