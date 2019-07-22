
package com._360pai.core.model.applet;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
public class TAppletAuctionMap implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 店铺id
	 */
	private String shopId;
	/**
	 * 标的id
	 */
	private Integer auctionId;
	/**
	 * 热推的排序 默认为0 查询倒叙
	 */
	private Integer pushDesc;
	/**
	 * 状态0 下架 1 上架
	 */
	private String status;
	/**
	 * 删除状态 不显示0 显示 1 不显示
	 */
	private String isDel;
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
	 * 店铺id
	 */
	public String getshopId(){
		return shopId;
	}
	
	/**
	 * 店铺id
	 */
	public void setshopId(String shopId){
		this.shopId = shopId;
	}
	
	/**
	 * 标的id
	 */
	public Integer getAuctionId(){
		return auctionId;
	}
	
	/**
	 * 标的id
	 */
	public void setAuctionId(Integer auctionId){
		this.auctionId = auctionId;
	}
	
	/**
	 * 热推的排序 默认为0 查询倒叙
	 */
	public Integer getPushDesc(){
		return pushDesc;
	}
	
	/**
	 * 热推的排序 默认为0 查询倒叙
	 */
	public void setPushDesc(Integer pushDesc){
		this.pushDesc = pushDesc;
	}
	
	/**
	 * 状态0 下架 1 上架
	 */
	public String getStatus(){
		return status;
	}
	
	/**
	 * 状态0 下架 1 上架
	 */
	public void setStatus(String status){
		this.status = status;
	}
	
	/**
	 * 删除状态 不显示0 显示 1 不显示
	 */
	public String getIsDel(){
		return isDel;
	}
	
	/**
	 * 删除状态 不显示0 显示 1 不显示
	 */
	public void setIsDel(String isDel){
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
