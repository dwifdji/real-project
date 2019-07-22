
package com._360pai.core.condition.fdd;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月25日 21时54分48秒
 */
public class GatewayFddCallBackRecordCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 返回的类型
	 */
	private java.lang.String type;
	/**
	 * 订单号
	 */
	private java.lang.String transactionId;
	/**
	 * 合同号
	 */
	private java.lang.String contractId;
	/**
	 * gatway处理状态
	 */
	private java.lang.String gatewayStatus;
	/**
	 * 通知业务方状态
	 */
	private java.lang.String coreStatus;
	/**
	 * 请求返回的数据
	 */
	private java.lang.String reqparam;
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
	 * 返回的类型
	 */
	public java.lang.String getType(){
		return type;
	}
	
	/**
	 * 返回的类型
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	
	/**
	 * 订单号
	 */
	public java.lang.String getTransactionId(){
		return transactionId;
	}
	
	/**
	 * 订单号
	 */
	public void setTransactionId(java.lang.String transactionId){
		this.transactionId = transactionId;
	}
	
	/**
	 * 合同号
	 */
	public java.lang.String getContractId(){
		return contractId;
	}
	
	/**
	 * 合同号
	 */
	public void setContractId(java.lang.String contractId){
		this.contractId = contractId;
	}
	
	/**
	 * gatway处理状态
	 */
	public java.lang.String getGatewayStatus(){
		return gatewayStatus;
	}
	
	/**
	 * gatway处理状态
	 */
	public void setGatewayStatus(java.lang.String gatewayStatus){
		this.gatewayStatus = gatewayStatus;
	}
	
	/**
	 * 通知业务方状态
	 */
	public java.lang.String getCoreStatus(){
		return coreStatus;
	}
	
	/**
	 * 通知业务方状态
	 */
	public void setCoreStatus(java.lang.String coreStatus){
		this.coreStatus = coreStatus;
	}
	
	/**
	 * 请求返回的数据
	 */
	public java.lang.String getReqparam(){
		return reqparam;
	}
	
	/**
	 * 请求返回的数据
	 */
	public void setReqparam(java.lang.String reqparam){
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