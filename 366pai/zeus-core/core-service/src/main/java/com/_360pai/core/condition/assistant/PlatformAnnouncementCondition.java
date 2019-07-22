
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
public class PlatformAnnouncementCondition implements DaoCondition{

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
	private String url;
	/**
	 * 
	 */
	private String detail;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private java.util.Date expiredAt;
	/**
	 * 
	 */
	private java.util.Date publicAt;
	/**
	 * 
	 */
	private String category;
	/**
	 * 浏览量
	 */
	private java.lang.Integer viewCount;

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
	public String getUrl(){
		return url;
	}
	
	/**
	 * 
	 */
	public void setUrl(String url){
		this.url = url;
	}
	
	/**
	 * 
	 */
	public String getDetail(){
		return detail;
	}
	
	/**
	 * 
	 */
	public void setDetail(String detail){
		this.detail = detail;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreatedAt(){
		return createdAt;
	}
	
	/**
	 * 
	 */
	public void setCreatedAt(java.util.Date createdAt){
		this.createdAt = createdAt;
	}
	
	/**
	 * 
	 */
	public java.util.Date getExpiredAt(){
		return expiredAt;
	}
	
	/**
	 * 
	 */
	public void setExpiredAt(java.util.Date expiredAt){
		this.expiredAt = expiredAt;
	}
	
	/**
	 * 
	 */
	public java.util.Date getPublicAt(){
		return publicAt;
	}
	
	/**
	 * 
	 */
	public void setPublicAt(java.util.Date publicAt){
		this.publicAt = publicAt;
	}
	
	/**
	 * 
	 */
	public String getCategory(){
		return category;
	}
	
	/**
	 * 
	 */
	public void setCategory(String category){
		this.category = category;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
}