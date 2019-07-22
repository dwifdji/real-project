
package com.winback.gateway.condition.pay;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月16日 19时25分32秒
 */
public class TGatewayPayOrderCondition implements DaoCondition{

	/**
	 * 主键，案件编号
	 */
	private java.lang.Integer id;
	/**
	 * 支付类型1 支付宝 2 微信
	 */
	private java.lang.String type;
	/**
	 * 业务code
	 */
	private java.lang.String businessCode;
	/**
	 * 业务订单号
	 */
	private java.lang.String businessNo;
	/**
	 * 支付订单编号
	 */
	private java.lang.String orderNo;
	/**
	 * 账户id
	 */
	private java.lang.Integer accountId;
	/**
	 * 订单金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 订单转态
	 */
	private java.lang.String status;
	/**
	 * 订单描述
	 */
	private java.lang.String descInfo;
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
	 * 业务code
	 */
	public java.lang.String getBusinessCode(){
		return businessCode;
	}
	
	/**
	 * 业务code
	 */
	public void setBusinessCode(java.lang.String businessCode){
		this.businessCode = businessCode;
	}
	
	/**
	 * 业务订单号
	 */
	public java.lang.String getBusinessNo(){
		return businessNo;
	}
	
	/**
	 * 业务订单号
	 */
	public void setBusinessNo(java.lang.String businessNo){
		this.businessNo = businessNo;
	}
	
	/**
	 * 支付订单编号
	 */
	public java.lang.String getOrderNo(){
		return orderNo;
	}
	
	/**
	 * 支付订单编号
	 */
	public void setOrderNo(java.lang.String orderNo){
		this.orderNo = orderNo;
	}
	
	/**
	 * 账户id
	 */
	public java.lang.Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 账户id
	 */
	public void setAccountId(java.lang.Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 订单金额
	 */
	public java.math.BigDecimal getAmount(){
		return amount;
	}
	
	/**
	 * 订单金额
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}
	
	/**
	 * 订单转态
	 */
	public java.lang.String getStatus(){
		return status;
	}
	
	/**
	 * 订单转态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}


	public String getDescInfo() {
		return descInfo;
	}

	public void setDescInfo(String descInfo) {
		this.descInfo = descInfo;
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