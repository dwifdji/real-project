
package com.winback.gateway.model.pay;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月16日 19时25分32秒
 */
public class TGatewayPayCallBack implements java.io.Serializable{

	/**
	 * 主键，案件编号
	 */
	private java.lang.Integer id;
	/**
	 * 支付类型1 支付宝 2 微信
	 */
	private java.lang.String type;
	/**
	 * 订单号
	 */
	private java.lang.String orderNo;
	/**
	 * 业务号
	 */
	private java.lang.String businessNo;
	/**
	 * 状态
	 */
	private java.lang.String status;
	/**
	 * 业务处理状态
	 */
	private java.lang.String businessStatus;
	/**
	 * 金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 回调的参数
	 */
	private java.lang.String reqParam;
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
	
	/**
	 * 主键，案件编号
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 主键，案件编号
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 支付类型1 支付宝 2 微信
	 */
	public java.lang.String getType(){
		return type;
	}
	
	/**
	 * 支付类型1 支付宝 2 微信
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	
	/**
	 * 订单号
	 */
	public java.lang.String getOrderNo(){
		return orderNo;
	}
	
	/**
	 * 订单号
	 */
	public void setOrderNo(java.lang.String orderNo){
		this.orderNo = orderNo;
	}
	
	/**
	 * 业务号
	 */
	public java.lang.String getBusinessNo(){
		return businessNo;
	}
	
	/**
	 * 业务号
	 */
	public void setBusinessNo(java.lang.String businessNo){
		this.businessNo = businessNo;
	}
	
	/**
	 * 状态
	 */
	public java.lang.String getStatus(){
		return status;
	}
	
	/**
	 * 状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	
	/**
	 * 业务处理状态
	 */
	public java.lang.String getBusinessStatus(){
		return businessStatus;
	}
	
	/**
	 * 业务处理状态
	 */
	public void setBusinessStatus(java.lang.String businessStatus){
		this.businessStatus = businessStatus;
	}
	
	/**
	 * 金额
	 */
	public java.math.BigDecimal getAmount(){
		return amount;
	}
	
	/**
	 * 金额
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}
	
	/**
	 * 回调的参数
	 */
	public java.lang.String getReqParam(){
		return reqParam;
	}
	
	/**
	 * 回调的参数
	 */
	public void setReqParam(java.lang.String reqParam){
		this.reqParam = reqParam;
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
