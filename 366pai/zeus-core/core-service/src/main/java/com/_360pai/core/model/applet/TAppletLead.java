
package com._360pai.core.model.applet;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
public class TAppletLead implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 微信的openid
	 */
	private String openId;
	/**
	 * 新手引导类型 1开店支付服务费 2 发送店铺名称 3 添加小程序 4 捞货 5 搜索提示
	 */
	private String type;
	/**
	 * 提示状态 0 未提示 1 已提示
	 */
	private String status;
	/**
	 * 
	 */
	private java.util.Date createTime;
	/**
	 * 
	 */
	private java.util.Date updateTime;
	
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
	 * 微信的openid
	 */
	public String getOpenId(){
		return openId;
	}
	
	/**
	 * 微信的openid
	 */
	public void setOpenId(String openId){
		this.openId = openId;
	}
	
	/**
	 * 新手引导类型 1开店支付服务费 2 发送店铺名称 3 添加小程序 4 捞货 5 搜索提示
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 新手引导类型 1开店支付服务费 2 发送店铺名称 3 添加小程序 4 捞货 5 搜索提示
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 提示状态 0 未提示 1 已提示
	 */
	public String getStatus(){
		return status;
	}
	
	/**
	 * 提示状态 0 未提示 1 已提示
	 */
	public void setStatus(String status){
		this.status = status;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	
	/**
	 * 
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}
