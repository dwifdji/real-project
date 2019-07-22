
package com._360pai.core.model.fdd;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月15日 16时37分04秒
 */
public class GatewayFddRecord implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private java.lang.Integer id;
	/**
	 * 业务关键字
	 */
	private java.lang.String busId;
	/**
	 * 请求的接口编码
	 */
	private java.lang.String reqType;
	/**
	 * 请求参数
	 */
	private java.lang.String requestParam;
	/**
	 * 返回参数
	 */
	private java.lang.String responseParam;
	/**
	 * 返回码
	 */
	private java.lang.String responseCode;
	/**
	 * 请求时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	
	/**
	 * 主键id
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 主键id
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 业务关键字
	 */
	public java.lang.String getBusId(){
		return busId;
	}
	
	/**
	 * 业务关键字
	 */
	public void setBusId(java.lang.String busId){
		this.busId = busId;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	/**
	 * 请求参数
	 */
	public java.lang.String getRequestParam(){
		return requestParam;
	}
	
	/**
	 * 请求参数
	 */
	public void setRequestParam(java.lang.String requestParam){
		this.requestParam = requestParam;
	}
	
	/**
	 * 返回参数
	 */
	public java.lang.String getResponseParam(){
		return responseParam;
	}
	
	/**
	 * 返回参数
	 */
	public void setResponseParam(java.lang.String responseParam){
		this.responseParam = responseParam;
	}
	
	/**
	 * 返回码
	 */
	public java.lang.String getResponseCode(){
		return responseCode;
	}
	
	/**
	 * 返回码
	 */
	public void setResponseCode(java.lang.String responseCode){
		this.responseCode = responseCode;
	}
	
	/**
	 * 请求时间
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 请求时间
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
