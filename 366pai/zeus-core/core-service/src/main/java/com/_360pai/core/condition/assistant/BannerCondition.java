
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月21日 09时08分43秒
 */
public class BannerCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String linkUrl;
	/**
	 * 
	 */
	private String imgUrl;
	/**
	 * 
	 */
	private Boolean isAgency;
	/**
	 * 
	 */
	private Boolean isOnline;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Integer orderNum;
	/**
	 * banner类型  1:首页头部 2:背景 3:拍卖大厅 4:预招商 5:处置服务商
	 */
	private Integer type;
	
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
	public String getLinkUrl(){
		return linkUrl;
	}
	
	/**
	 * 
	 */
	public void setLinkUrl(String linkUrl){
		this.linkUrl = linkUrl;
	}
	
	/**
	 * 
	 */
	public String getImgUrl(){
		return imgUrl;
	}
	
	/**
	 * 
	 */
	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}
	
	/**
	 * 
	 */
	public Boolean getIsAgency(){
		return isAgency;
	}
	
	/**
	 * 
	 */
	public void setIsAgency(Boolean isAgency){
		this.isAgency = isAgency;
	}
	
	/**
	 * 
	 */
	public Boolean getIsOnline(){
		return isOnline;
	}
	
	/**
	 * 
	 */
	public void setIsOnline(Boolean isOnline){
		this.isOnline = isOnline;
	}
	
	/**
	 * 
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 
	 */
	public Integer getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 
	 */
	public void setOrderNum(Integer orderNum){
		this.orderNum = orderNum;
	}
	
	/**
	 * banner类型  1:首页头部 2:背景 3:拍卖大厅 4:预招商 5:处置服务商
	 */
	public Integer getType(){
		return type;
	}
	
	/**
	 * banner类型  1:首页头部 2:背景 3:拍卖大厅 4:预招商 5:处置服务商
	 */
	public void setType(Integer type){
		this.type = type;
	}

}