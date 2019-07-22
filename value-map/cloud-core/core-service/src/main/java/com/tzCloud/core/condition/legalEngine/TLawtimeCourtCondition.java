
package com.tzCloud.core.condition.legalEngine;

import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年05月13日 09时31分46秒
 */
public class TLawtimeCourtCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 详情链接
	 */
	private String itemUrl;
	/**
	 * 法院类型
	 */
	private String type;
	/**
	 * 法院名称
	 */
	private String name;
	/**
	 * 法院地址
	 */
	private String address;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 其他联系方式
	 */
	private String otherPhone;
	/**
	 * 工作时间
	 */
	private String workTime;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 图片链接
	 */
	private String imgUrl;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 删除标志
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
	 * 详情链接
	 */
	public String getItemUrl(){
		return itemUrl;
	}
	
	/**
	 * 详情链接
	 */
	public void setItemUrl(String itemUrl){
		this.itemUrl = itemUrl;
	}
	
	/**
	 * 法院类型
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 法院类型
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 法院名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 法院名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 法院地址
	 */
	public String getAddress(){
		return address;
	}
	
	/**
	 * 法院地址
	 */
	public void setAddress(String address){
		this.address = address;
	}
	
	/**
	 * 电话
	 */
	public String getPhone(){
		return phone;
	}
	
	/**
	 * 电话
	 */
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	/**
	 * 其他联系方式
	 */
	public String getOtherPhone(){
		return otherPhone;
	}
	
	/**
	 * 其他联系方式
	 */
	public void setOtherPhone(String otherPhone){
		this.otherPhone = otherPhone;
	}
	
	/**
	 * 工作时间
	 */
	public String getWorkTime(){
		return workTime;
	}
	
	/**
	 * 工作时间
	 */
	public void setWorkTime(String workTime){
		this.workTime = workTime;
	}
	
	/**
	 * 描述
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * 描述
	 */
	public void setDescription(String description){
		this.description = description;
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
	 * 省
	 */
	public String getProvince(){
		return province;
	}
	
	/**
	 * 省
	 */
	public void setProvince(String province){
		this.province = province;
	}
	
	/**
	 * 市
	 */
	public String getCity(){
		return city;
	}
	
	/**
	 * 市
	 */
	public void setCity(String city){
		this.city = city;
	}
	
	/**
	 * 删除标志
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志
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