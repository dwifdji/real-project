
package com._360pai.core.model.assistant;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分27秒
 */
public class PlatformAnnouncement implements java.io.Serializable{

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
