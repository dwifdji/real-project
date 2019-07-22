
package com._360pai.core.model.assistant;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月24日 18时12分14秒
 */
public class TFrontError implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 登录id
	 */
	private Integer loginId;
	/**
	 * 主站：1, 小程序：2，机构：3，管理后台：4
	 */
	private String application;
	/**
	 * 埋点key
	 */
	private String pointKey;
	/**
	 * 埋点描述
	 */
	private String pointDesc;
	/**
	 * 业务id
	 */
	private String buzId;
	/**
	 * 业务参数
	 */
	private String buzParams;
	/**
	 * 设备标识
	 */
	private String deviceMark;
	/**
	 * 错误
	 */
	private String error;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * ip
	 */
	private String ip;
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
	 * 登录id
	 */
	public Integer getLoginId(){
		return loginId;
	}
	
	/**
	 * 登录id
	 */
	public void setLoginId(Integer loginId){
		this.loginId = loginId;
	}
	
	/**
	 * 主站：1, 小程序：2，机构：3，管理后台：4
	 */
	public String getApplication(){
		return application;
	}
	
	/**
	 * 主站：1, 小程序：2，机构：3，管理后台：4
	 */
	public void setApplication(String application){
		this.application = application;
	}
	
	/**
	 * 埋点key
	 */
	public String getPointKey(){
		return pointKey;
	}
	
	/**
	 * 埋点key
	 */
	public void setPointKey(String pointKey){
		this.pointKey = pointKey;
	}
	
	/**
	 * 埋点描述
	 */
	public String getPointDesc(){
		return pointDesc;
	}
	
	/**
	 * 埋点描述
	 */
	public void setPointDesc(String pointDesc){
		this.pointDesc = pointDesc;
	}
	
	/**
	 * 业务id
	 */
	public String getBuzId(){
		return buzId;
	}
	
	/**
	 * 业务id
	 */
	public void setBuzId(String buzId){
		this.buzId = buzId;
	}
	
	/**
	 * 业务参数
	 */
	public String getBuzParams(){
		return buzParams;
	}
	
	/**
	 * 业务参数
	 */
	public void setBuzParams(String buzParams){
		this.buzParams = buzParams;
	}
	
	/**
	 * 设备标识
	 */
	public String getDeviceMark(){
		return deviceMark;
	}
	
	/**
	 * 设备标识
	 */
	public void setDeviceMark(String deviceMark){
		this.deviceMark = deviceMark;
	}
	
	/**
	 * 错误
	 */
	public String getError(){
		return error;
	}
	
	/**
	 * 错误
	 */
	public void setError(String error){
		this.error = error;
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
	 * ip
	 */
	public String getIp(){
		return ip;
	}
	
	/**
	 * ip
	 */
	public void setIp(String ip){
		this.ip = ip;
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
