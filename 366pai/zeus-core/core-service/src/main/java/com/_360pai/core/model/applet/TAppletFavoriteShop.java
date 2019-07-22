
package com._360pai.core.model.applet;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
public class TAppletFavoriteShop implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 店铺id
	 */
	private Integer shopId;
	/**
	 * 小程序openid
	 */
	private String openId;
	/**
	 * 是否删除（0 否 1 是）
	 */
	private Boolean isDelete;
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
	 * 小程序openid
	 */
	public String getOpenId(){
		return openId;
	}
	
	/**
	 * 小程序openid
	 */
	public void setOpenId(String openId){
		this.openId = openId;
	}
	
	/**
	 * 是否删除（0 否 1 是）
	 */
	public Boolean getIsDelete(){
		return isDelete;
	}
	
	/**
	 * 是否删除（0 否 1 是）
	 */
	public void setIsDelete(Boolean isDelete){
		this.isDelete = isDelete;
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
