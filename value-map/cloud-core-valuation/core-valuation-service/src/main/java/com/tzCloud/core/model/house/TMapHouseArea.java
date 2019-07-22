
package com.tzCloud.core.model.house;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年06月14日 11时14分35秒
 */
public class TMapHouseArea implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 查找路径
	 */
	private String areaUrl;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 区域
	 */
	private String area;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 删除标识
	 */
	private Boolean deleteFlag;
	
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
	 * 省份
	 */
	public String getProvince(){
		return province;
	}
	
	/**
	 * 省份
	 */
	public void setProvince(String province){
		this.province = province;
	}
	
	/**
	 * 查找路径
	 */
	public String getAreaUrl(){
		return areaUrl;
	}
	
	/**
	 * 查找路径
	 */
	public void setAreaUrl(String areaUrl){
		this.areaUrl = areaUrl;
	}
	
	/**
	 * 城市
	 */
	public String getCity(){
		return city;
	}
	
	/**
	 * 城市
	 */
	public void setCity(String city){
		this.city = city;
	}
	
	/**
	 * 区域
	 */
	public String getArea(){
		return area;
	}
	
	/**
	 * 区域
	 */
	public void setArea(String area){
		this.area = area;
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
	 * 删除标识
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标识
	 */
	public void setDeleteFlag(Boolean deleteFlag){
		this.deleteFlag = deleteFlag;
	}

}
