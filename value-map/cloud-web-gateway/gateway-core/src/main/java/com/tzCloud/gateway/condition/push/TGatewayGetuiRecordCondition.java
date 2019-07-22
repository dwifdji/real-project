
package com.tzCloud.gateway.condition.push;


import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月24日 15时09分36秒
 */
public class TGatewayGetuiRecordCondition implements DaoCondition {

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 推送类型
	 */
	private java.lang.Integer type;
	/**
	 * 业务类型
	 */
	private java.lang.String busType;
	/**
	 * 推送转态
	 */
	private java.lang.Integer status;
	/**
	 * 请求参数
	 */
	private java.lang.String reqParam;
	/**
	 * 返回参数
	 */
	private java.lang.String respParam;
	/**
	 * 删除标志0 不删除 1删除
	 */
	private java.lang.Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

	private String clientId;//

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * 
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 推送类型
	 */
	public java.lang.Integer getType(){
		return type;
	}
	
	/**
	 * 推送类型
	 */
	public void setType(java.lang.Integer type){
		this.type = type;
	}
	
	/**
	 * 业务类型
	 */
	public java.lang.String getBusType(){
		return busType;
	}
	
	/**
	 * 业务类型
	 */
	public void setBusType(java.lang.String busType){
		this.busType = busType;
	}
	
	/**
	 * 推送转态
	 */
	public java.lang.Integer getStatus(){
		return status;
	}
	
	/**
	 * 推送转态
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
	
	/**
	 * 请求参数
	 */
	public java.lang.String getReqParam(){
		return reqParam;
	}
	
	/**
	 * 请求参数
	 */
	public void setReqParam(java.lang.String reqParam){
		this.reqParam = reqParam;
	}
	
	/**
	 * 返回参数
	 */
	public java.lang.String getRespParam(){
		return respParam;
	}
	
	/**
	 * 返回参数
	 */
	public void setRespParam(java.lang.String respParam){
		this.respParam = respParam;
	}
	
	/**
	 * 删除标志0 不删除 1删除
	 */
	public java.lang.Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志0 不删除 1删除
	 */
	public void setDeleteFlag(java.lang.Boolean deleteFlag){
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