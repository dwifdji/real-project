
package com._360pai.core.model.applet;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
public class TAppletReadMessageMap implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 微信的openid
	 */
	private String openId;
	/**
	 * 店铺id
	 */
	private Integer shopId;
	/**
	 * 已读消息id
	 */
	private Integer messageId;
	/**
	 * 消息类型 1 站内消息 2 系统公告
	 */
	private byte type;
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
	 * 店铺id
	 */
	public Integer getShopId(){
		return shopId;
	}
	
	/**
	 * 店铺id
	 */
	public void setShopId(Integer shopId){
		this.shopId = shopId;
	}
	
	/**
	 * 已读消息id
	 */
	public Integer getMessageId(){
		return messageId;
	}
	
	/**
	 * 已读消息id
	 */
	public void setMessageId(Integer messageId){
		this.messageId = messageId;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
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
