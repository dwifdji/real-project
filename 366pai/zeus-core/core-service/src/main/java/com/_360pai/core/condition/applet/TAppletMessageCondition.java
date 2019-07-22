
package com._360pai.core.condition.applet;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
public class TAppletMessageCondition implements DaoCondition{

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
	 * 消息类型 备用字段
	 */
	private String type;
	/**
	 * 消息标题
	 */
	private String name;
	/**
	 * 消息内容
	 */
	private String context;
	/**
	 * 是否删除 0 未删除 1已删除
	 */
	private Boolean isDel;
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
	 * 消息类型 备用字段
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 消息类型 备用字段
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 消息标题
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 消息标题
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 消息内容
	 */
	public String getContext(){
		return context;
	}
	
	/**
	 * 消息内容
	 */
	public void setContext(String context){
		this.context = context;
	}
	
	/**
	 * 是否删除 0 未删除 1已删除
	 */
	public Boolean getIsDel(){
		return isDel;
	}
	
	/**
	 * 是否删除 0 未删除 1已删除
	 */
	public void setIsDel(Boolean isDel){
		this.isDel = isDel;
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