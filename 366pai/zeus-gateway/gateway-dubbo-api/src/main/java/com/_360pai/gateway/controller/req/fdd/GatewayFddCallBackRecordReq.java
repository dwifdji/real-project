
package com._360pai.gateway.controller.req.fdd;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月25日 21时54分48秒
 */
public class GatewayFddCallBackRecordReq implements java.io.Serializable{

	/**
	 *
	 */
	private Integer id;
	/**
	 * 返回的类型
	 */
	private String type;
	/**
	 * 订单号
	 */
	private String transactionId;
	/**
	 * 合同号
	 */
	private String contractId;
	/**
	 * gatway处理状态
	 */
	private String gatewayStatus;
	/**
	 * 通知业务方状态
	 */
	private String coreStatus;
	/**
	 * 请求返回的数据
	 */
	private String reqparam;
	/**
	 * 创建时间
	 */
	private java.util.Date createdAt;
	/**
	 * 更新时间
	 */
	private java.util.Date updateAt;

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
	 * 返回的类型
	 */
	public String getType(){
		return type;
	}

	/**
	 * 返回的类型
	 */
	public void setType(String type){
		this.type = type;
	}

	/**
	 * 订单号
	 */
	public String getTransactionId(){
		return transactionId;
	}

	/**
	 * 订单号
	 */
	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	/**
	 * 合同号
	 */
	public String getContractId(){
		return contractId;
	}

	/**
	 * 合同号
	 */
	public void setContractId(String contractId){
		this.contractId = contractId;
	}

	/**
	 * gatway处理状态
	 */
	public String getGatewayStatus(){
		return gatewayStatus;
	}

	/**
	 * gatway处理状态
	 */
	public void setGatewayStatus(String gatewayStatus){
		this.gatewayStatus = gatewayStatus;
	}

	/**
	 * 通知业务方状态
	 */
	public String getCoreStatus(){
		return coreStatus;
	}

	/**
	 * 通知业务方状态
	 */
	public void setCoreStatus(String coreStatus){
		this.coreStatus = coreStatus;
	}

	/**
	 * 请求返回的数据
	 */
	public String getReqparam(){
		return reqparam;
	}

	/**
	 * 请求返回的数据
	 */
	public void setReqparam(String reqparam){
		this.reqparam = reqparam;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreatedAt(){
		return createdAt;
	}
	
	/**
	 * 创建时间
	 */
	public void setCreatedAt(java.util.Date createdAt){
		this.createdAt = createdAt;
	}
	
	/**
	 * 更新时间
	 */
	public java.util.Date getUpdateAt(){
		return updateAt;
	}
	
	/**
	 * 更新时间
	 */
	public void setUpdateAt(java.util.Date updateAt){
		this.updateAt = updateAt;
	}

}
