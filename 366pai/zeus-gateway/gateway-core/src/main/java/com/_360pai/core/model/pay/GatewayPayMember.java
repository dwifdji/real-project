
package com._360pai.core.model.pay;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月15日 16时47分13秒
 */
public class GatewayPayMember implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private java.lang.Integer id;
	/**
	 * 请求接口类型
	 */
	private java.lang.String payType;
	/**
	 * 绑定的会员编号
	 */
	private java.lang.String memCode;
	/**
	 * 会员名称
	 */
	private java.lang.String memName;
	/**
	 * 会员类型 1 企业用户 2 会员用户
	 */
	private java.lang.String memType;
	/**
	 * 开通的状态
	 */
	private java.lang.String status;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	/**
	 * 备注信息
	 */
	private java.lang.String msg;
	
	/**
	 * 主键id
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 主键id
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 请求接口类型
	 */
	public java.lang.String getPayType(){
		return payType;
	}
	
	/**
	 * 请求接口类型
	 */
	public void setPayType(java.lang.String payType){
		this.payType = payType;
	}
	
	/**
	 * 绑定的会员编号
	 */
	public java.lang.String getMemCode(){
		return memCode;
	}
	
	/**
	 * 绑定的会员编号
	 */
	public void setMemCode(java.lang.String memCode){
		this.memCode = memCode;
	}
	
	/**
	 * 会员名称
	 */
	public java.lang.String getMemName(){
		return memName;
	}
	
	/**
	 * 会员名称
	 */
	public void setMemName(java.lang.String memName){
		this.memName = memName;
	}
	
	/**
	 * 会员类型 1 企业用户 2 会员用户
	 */
	public java.lang.String getMemType(){
		return memType;
	}
	
	/**
	 * 会员类型 1 企业用户 2 会员用户
	 */
	public void setMemType(java.lang.String memType){
		this.memType = memType;
	}
	
	/**
	 * 开通的状态
	 */
	public java.lang.String getStatus(){
		return status;
	}
	
	/**
	 * 开通的状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
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
	
	/**
	 * 备注信息
	 */
	public java.lang.String getMsg(){
		return msg;
	}
	
	/**
	 * 备注信息
	 */
	public void setMsg(java.lang.String msg){
		this.msg = msg;
	}

}
