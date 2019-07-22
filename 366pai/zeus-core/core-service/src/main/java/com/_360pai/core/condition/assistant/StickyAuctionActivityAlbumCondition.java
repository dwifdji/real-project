
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
public class StickyAuctionActivityAlbumCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String img;
	/**
	 * 
	 */
	private Integer albumId;
	/**
	 * 
	 */
	private Integer orderNumber;
	
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
	 * 
	 */
	public String getImg(){
		return img;
	}
	
	/**
	 * 
	 */
	public void setImg(String img){
		this.img = img;
	}
	
	/**
	 * 
	 */
	public Integer getAlbumId(){
		return albumId;
	}
	
	/**
	 * 
	 */
	public void setAlbumId(Integer albumId){
		this.albumId = albumId;
	}
	
	/**
	 * 
	 */
	public Integer getOrderNumber(){
		return orderNumber;
	}
	
	/**
	 * 
	 */
	public void setOrderNumber(Integer orderNumber){
		this.orderNumber = orderNumber;
	}

}