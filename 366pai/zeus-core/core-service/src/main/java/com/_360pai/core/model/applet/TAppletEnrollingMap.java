
package com._360pai.core.model.applet;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年02月25日 15时52分17秒
 */
public class TAppletEnrollingMap implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 店铺id
	 */
	private String shopId;
	/**
	 * 预招商活动id
	 */
	private Integer enrollingId;
	/**
	 * 热推排序
	 */
	private Integer pushDesc;
	/**
	 * 0 已经下架 1 已经上架
	 */
	private String status;
	/**
	 * 0正常数据 1 已删除
	 */
	private String isDel;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	
	/**
	 * 主键
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 店铺id
	 */
	public String getShopId(){
		return shopId;
	}
	
	/**
	 * 店铺id
	 */
	public void setShopId(String shopId){
		this.shopId = shopId;
	}
	
	/**
	 * 预招商活动id
	 */
	public Integer getEnrollingId(){
		return enrollingId;
	}
	
	/**
	 * 预招商活动id
	 */
	public void setEnrollingId(Integer enrollingId){
		this.enrollingId = enrollingId;
	}
	
	/**
	 * 热推排序
	 */
	public Integer getPushDesc(){
		return pushDesc;
	}
	
	/**
	 * 热推排序
	 */
	public void setPushDesc(Integer pushDesc){
		this.pushDesc = pushDesc;
	}
	
	/**
	 * 0 已经下架 1 已经上架
	 */
	public String getStatus(){
		return status;
	}
	
	/**
	 * 0 已经下架 1 已经上架
	 */
	public void setStatus(String status){
		this.status = status;
	}
	
	/**
	 * 0正常数据 1 已删除
	 */
	public String getIsDel(){
		return isDel;
	}
	
	/**
	 * 0正常数据 1 已删除
	 */
	public void setIsDel(String isDel){
		this.isDel = isDel;
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
