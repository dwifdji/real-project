
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
public class PromotionCategoryCardCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private String hint;
	/**
	 * 
	 */
	private Integer assetCategoryGroupId;
	/**
	 * 
	 */
	private Integer assetPropertyId;
	/**
	 * 
	 */
	private Integer orderNo;
	/**
	 * 
	 */
	private String img;
	/**
	 * 
	 */
	private String link;
	
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
	public String getTitle(){
		return title;
	}
	
	/**
	 * 
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * 
	 */
	public String getHint(){
		return hint;
	}
	
	/**
	 * 
	 */
	public void setHint(String hint){
		this.hint = hint;
	}
	
	/**
	 * 
	 */
	public Integer getAssetCategoryGroupId(){
		return assetCategoryGroupId;
	}
	
	/**
	 * 
	 */
	public void setAssetCategoryGroupId(Integer assetCategoryGroupId){
		this.assetCategoryGroupId = assetCategoryGroupId;
	}
	
	/**
	 * 
	 */
	public Integer getAssetPropertyId(){
		return assetPropertyId;
	}
	
	/**
	 * 
	 */
	public void setAssetPropertyId(Integer assetPropertyId){
		this.assetPropertyId = assetPropertyId;
	}
	
	/**
	 * 
	 */
	public Integer getOrderNo(){
		return orderNo;
	}
	
	/**
	 * 
	 */
	public void setOrderNo(Integer orderNo){
		this.orderNo = orderNo;
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
	public String getLink(){
		return link;
	}
	
	/**
	 * 
	 */
	public void setLink(String link){
		this.link = link;
	}

}