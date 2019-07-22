
package com._360pai.core.model.activity;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年07月01日 10时42分57秒
 */
public class TActivityPoster implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 推广位名称
	 */
	private String name;
	/**
	 * 开始时间
	 */
	private java.util.Date beginAt;
	/**
	 * 结束时间
	 */
	private java.util.Date endAt;
	/**
	 * 机构显示标志
	 */
	private Boolean agencyDisplayFlag;
	/**
	 * 图片链接
	 */
	private String imgUrl;
	/**
	 * 自动添加资产标志
	 */
	private Boolean autoFlag;
	/**
	 * 省id
	 */
	private Integer provinceId;
	/**
	 * 市id
	 */
	private Integer cityId;
	/**
	 * 区县id
	 */
	private Integer areaId;
	/**
	 * 资产类型
	 */
	private String category;
	/**
	 * 商品分类
	 */
	private String busType;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 活动id，逗号分隔
	 */
	private String activityIds;
	/**
	 * 排序号
	 */
	private Integer orderNum;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
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
	 * 推广位名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 推广位名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 开始时间
	 */
	public java.util.Date getBeginAt(){
		return beginAt;
	}
	
	/**
	 * 开始时间
	 */
	public void setBeginAt(java.util.Date beginAt){
		this.beginAt = beginAt;
	}
	
	/**
	 * 结束时间
	 */
	public java.util.Date getEndAt(){
		return endAt;
	}
	
	/**
	 * 结束时间
	 */
	public void setEndAt(java.util.Date endAt){
		this.endAt = endAt;
	}
	
	/**
	 * 机构显示标志
	 */
	public Boolean getAgencyDisplayFlag(){
		return agencyDisplayFlag;
	}
	
	/**
	 * 机构显示标志
	 */
	public void setAgencyDisplayFlag(Boolean agencyDisplayFlag){
		this.agencyDisplayFlag = agencyDisplayFlag;
	}
	
	/**
	 * 图片链接
	 */
	public String getImgUrl(){
		return imgUrl;
	}
	
	/**
	 * 图片链接
	 */
	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}
	
	/**
	 * 自动添加资产标志
	 */
	public Boolean getAutoFlag(){
		return autoFlag;
	}
	
	/**
	 * 自动添加资产标志
	 */
	public void setAutoFlag(Boolean autoFlag){
		this.autoFlag = autoFlag;
	}
	
	/**
	 * 省id
	 */
	public Integer getProvinceId(){
		return provinceId;
	}
	
	/**
	 * 省id
	 */
	public void setProvinceId(Integer provinceId){
		this.provinceId = provinceId;
	}
	
	/**
	 * 市id
	 */
	public Integer getCityId(){
		return cityId;
	}
	
	/**
	 * 市id
	 */
	public void setCityId(Integer cityId){
		this.cityId = cityId;
	}
	
	/**
	 * 区县id
	 */
	public Integer getAreaId(){
		return areaId;
	}
	
	/**
	 * 区县id
	 */
	public void setAreaId(Integer areaId){
		this.areaId = areaId;
	}
	
	/**
	 * 资产类型
	 */
	public String getCategory(){
		return category;
	}
	
	/**
	 * 资产类型
	 */
	public void setCategory(String category){
		this.category = category;
	}
	
	/**
	 * 商品分类
	 */
	public String getBusType(){
		return busType;
	}
	
	/**
	 * 商品分类
	 */
	public void setBusType(String busType){
		this.busType = busType;
	}
	
	/**
	 * 状态
	 */
	public String getStatus(){
		return status;
	}
	
	/**
	 * 状态
	 */
	public void setStatus(String status){
		this.status = status;
	}
	
	/**
	 * 活动id，逗号分隔
	 */
	public String getActivityIds(){
		return activityIds;
	}
	
	/**
	 * 活动id，逗号分隔
	 */
	public void setActivityIds(String activityIds){
		this.activityIds = activityIds;
	}
	
	/**
	 * 排序号
	 */
	public Integer getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 排序号
	 */
	public void setOrderNum(Integer orderNum){
		this.orderNum = orderNum;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public void setDeleteFlag(Boolean deleteFlag){
		this.deleteFlag = deleteFlag;
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
