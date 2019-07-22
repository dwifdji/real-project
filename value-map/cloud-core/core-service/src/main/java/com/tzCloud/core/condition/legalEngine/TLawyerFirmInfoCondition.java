
package com.tzCloud.core.condition.legalEngine;

import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月28日 16时43分49秒
 */
public class TLawyerFirmInfoCondition implements DaoCondition{

	/**
	 * 
	 */
	private Long id;
	/**
	 * id
	 */
	private String firmId;
	/**
	 * 成立时间
	 */
	private String foundTime;
	/**
	 * 名称
	 */
	private String firmName;
	/**
	 * 
	 */
	private String jd;
	/**
	 * 联系地址
	 */
	private String contactAddress;
	/**
	 * 联系电话
	 */
	private String contactNumber;
	/**
	 * 执业证号
	 */
	private String licenseNumber;
	/**
	 * 所属地区
	 */
	private String area;
	/**
	 * 图片地址
	 */
	private String imgUrl;
	/**
	 * 省code
	 */
	private String provinceCode;
	/**
	 * 城市code
	 */
	private String cityCode;
	/**
	 * 区域code
	 */
	private String regionCode;
	/**
	 * 
	 */
	private String zt;
	/**
	 * 
	 */
	private String wd;
	/**
	 * 团队规模
	 */
	private Integer nums;
	/**
	 * 简介
	 */
	private String introduction;
	/**
	 * 执业证号
	 */
	private String tyshxydm;
	/**
	 * 
	 */
	private String yezc;
	/**
	 * 
	 */
	private String ywzclist;
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
	public Long getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(Long id){
		this.id = id;
	}
	
	/**
	 * id
	 */
	public String getFirmId(){
		return firmId;
	}
	
	/**
	 * id
	 */
	public void setFirmId(String firmId){
		this.firmId = firmId;
	}
	
	/**
	 * 成立时间
	 */
	public String getFoundTime(){
		return foundTime;
	}
	
	/**
	 * 成立时间
	 */
	public void setFoundTime(String foundTime){
		this.foundTime = foundTime;
	}
	
	/**
	 * 名称
	 */
	public String getFirmName(){
		return firmName;
	}
	
	/**
	 * 名称
	 */
	public void setFirmName(String firmName){
		this.firmName = firmName;
	}
	
	/**
	 * 
	 */
	public String getJd(){
		return jd;
	}
	
	/**
	 * 
	 */
	public void setJd(String jd){
		this.jd = jd;
	}
	
	/**
	 * 联系地址
	 */
	public String getContactAddress(){
		return contactAddress;
	}
	
	/**
	 * 联系地址
	 */
	public void setContactAddress(String contactAddress){
		this.contactAddress = contactAddress;
	}
	
	/**
	 * 联系电话
	 */
	public String getContactNumber(){
		return contactNumber;
	}
	
	/**
	 * 联系电话
	 */
	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}
	
	/**
	 * 执业证号
	 */
	public String getLicenseNumber(){
		return licenseNumber;
	}
	
	/**
	 * 执业证号
	 */
	public void setLicenseNumber(String licenseNumber){
		this.licenseNumber = licenseNumber;
	}
	
	/**
	 * 所属地区
	 */
	public String getArea(){
		return area;
	}
	
	/**
	 * 所属地区
	 */
	public void setArea(String area){
		this.area = area;
	}
	
	/**
	 * 图片地址
	 */
	public String getImgUrl(){
		return imgUrl;
	}
	
	/**
	 * 图片地址
	 */
	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}
	
	/**
	 * 省code
	 */
	public String getProvinceCode(){
		return provinceCode;
	}
	
	/**
	 * 省code
	 */
	public void setProvinceCode(String provinceCode){
		this.provinceCode = provinceCode;
	}
	
	/**
	 * 城市code
	 */
	public String getCityCode(){
		return cityCode;
	}
	
	/**
	 * 城市code
	 */
	public void setCityCode(String cityCode){
		this.cityCode = cityCode;
	}
	
	/**
	 * 区域code
	 */
	public String getRegionCode(){
		return regionCode;
	}
	
	/**
	 * 区域code
	 */
	public void setRegionCode(String regionCode){
		this.regionCode = regionCode;
	}
	
	/**
	 * 
	 */
	public String getZt(){
		return zt;
	}
	
	/**
	 * 
	 */
	public void setZt(String zt){
		this.zt = zt;
	}
	
	/**
	 * 
	 */
	public String getWd(){
		return wd;
	}
	
	/**
	 * 
	 */
	public void setWd(String wd){
		this.wd = wd;
	}
	
	/**
	 * 团队规模
	 */
	public Integer getNums(){
		return nums;
	}
	
	/**
	 * 团队规模
	 */
	public void setNums(Integer nums){
		this.nums = nums;
	}
	
	/**
	 * 简介
	 */
	public String getIntroduction(){
		return introduction;
	}
	
	/**
	 * 简介
	 */
	public void setIntroduction(String introduction){
		this.introduction = introduction;
	}
	
	/**
	 * 执业证号
	 */
	public String getTyshxydm(){
		return tyshxydm;
	}
	
	/**
	 * 执业证号
	 */
	public void setTyshxydm(String tyshxydm){
		this.tyshxydm = tyshxydm;
	}
	
	/**
	 * 
	 */
	public String getYezc(){
		return yezc;
	}
	
	/**
	 * 
	 */
	public void setYezc(String yezc){
		this.yezc = yezc;
	}
	
	/**
	 * 
	 */
	public String getYwzclist(){
		return ywzclist;
	}
	
	/**
	 * 
	 */
	public void setYwzclist(String ywzclist){
		this.ywzclist = ywzclist;
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