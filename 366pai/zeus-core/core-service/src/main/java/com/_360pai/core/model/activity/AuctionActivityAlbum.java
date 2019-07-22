
package com._360pai.core.model.activity;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分25秒
 */
public class AuctionActivityAlbum implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String img;
	/**
	 * 
	 */
	private java.util.Date beginAt;
	/**
	 * 
	 */
	private java.util.Date endAt;
	/**
	 * 
	 */
	private Boolean isOnline;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private java.util.Date previewAt;
	/**
	 * 
	 */
	private String description;
	/**
	 * 
	 */
	private String detailImg;

	/**
	 * 关联活动数量（非表字段）
	 */
	private Integer activityNumber;
	
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
	public java.util.Date getBeginAt(){
		return beginAt;
	}
	
	/**
	 * 
	 */
	public void setBeginAt(java.util.Date beginAt){
		this.beginAt = beginAt;
	}
	
	/**
	 * 
	 */
	public java.util.Date getEndAt(){
		return endAt;
	}
	
	/**
	 * 
	 */
	public void setEndAt(java.util.Date endAt){
		this.endAt = endAt;
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
	public String getRemark(){
		return remark;
	}
	
	/**
	 * 
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	/**
	 * 
	 */
	public java.util.Date getPreviewAt(){
		return previewAt;
	}
	
	/**
	 * 
	 */
	public void setPreviewAt(java.util.Date previewAt){
		this.previewAt = previewAt;
	}
	
	/**
	 * 
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * 
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * 
	 */
	public String getDetailImg(){
		return detailImg;
	}
	
	/**
	 * 
	 */
	public void setDetailImg(String detailImg){
		this.detailImg = detailImg;
	}

	public Integer getActivityNumber() {
		return activityNumber;
	}

	public void setActivityNumber(Integer activityNumber) {
		this.activityNumber = activityNumber;
	}
}
