
package com.tzCloud.gateway.model.pay;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年06月19日 10时08分47秒
 */
public class TGatewayPayRecord implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 业务关键字
	 */
	private String busId;
	/**
	 * 请求的接口编码
	 */
	private String payType;
	/**
	 * 请求参数
	 */
	private String requestParam;
	/**
	 * 返回参数
	 */
	private String responseParam;
	/**
	 * 返回码
	 */
	private String responseCode;
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
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键id
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 业务关键字
	 */
	public String getBusId(){
		return busId;
	}
	
	/**
	 * 业务关键字
	 */
	public void setBusId(String busId){
		this.busId = busId;
	}
	
	/**
	 * 请求的接口编码
	 */
	public String getPayType(){
		return payType;
	}
	
	/**
	 * 请求的接口编码
	 */
	public void setPayType(String payType){
		this.payType = payType;
	}
	
	/**
	 * 请求参数
	 */
	public String getRequestParam(){
		return requestParam;
	}
	
	/**
	 * 请求参数
	 */
	public void setRequestParam(String requestParam){
		this.requestParam = requestParam;
	}
	
	/**
	 * 返回参数
	 */
	public String getResponseParam(){
		return responseParam;
	}
	
	/**
	 * 返回参数
	 */
	public void setResponseParam(String responseParam){
		this.responseParam = responseParam;
	}
	
	/**
	 * 返回码
	 */
	public String getResponseCode(){
		return responseCode;
	}
	
	/**
	 * 返回码
	 */
	public void setResponseCode(String responseCode){
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
