
package com._360pai.core.model.order;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月30日 11时19分10秒
 */
@Data
public class TServiceAdjustedRecord implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 获得分润的用户id
	 */
	private Integer accountId;
	private Integer partyId;
	/**
	 * 分润率
	 */
	private Double adjustedRate;
	/**
	 *  10 : 可提现 20：待转账 30：已提现 
	 */
	private Integer adjustedStatus;
	/**
	 * 分润金额
	 */
	private java.math.BigDecimal adjustedAmount;
	/**
	 * 提现订单号
	 */
	private String withdrawNo;
	/**
	 * 金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 交易编号
	 */
	private String orderNum;
	/**
	 * 用户名称
	 */
	private String partyName;
	/**
	 * 订单类型 10 资产大买办 20 配资乐 30 处置服务 31尽调报告
	 */
	private Integer orderType;
	/**
	 * 交易日期
	 */
	private java.util.Date orderTime;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
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

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * 分润率
	 */
	public Double getAdjustedRate(){
		return adjustedRate;
	}
	
	/**
	 * 分润率
	 */
	public void setAdjustedRate(Double adjustedRate){
		this.adjustedRate = adjustedRate;
	}
	
	/**
	 *  10 : 可提现 20：待转账 30：已提现 
	 */
	public Integer getAdjustedStatus(){
		return adjustedStatus;
	}
	
	/**
	 *  10 : 可提现 20：待转账 30：已提现 
	 */
	public void setAdjustedStatus(Integer adjustedStatus){
		this.adjustedStatus = adjustedStatus;
	}
	
	/**
	 * 分润金额
	 */
	public java.math.BigDecimal getAdjustedAmount(){
		return adjustedAmount;
	}
	
	/**
	 * 分润金额
	 */
	public void setAdjustedAmount(java.math.BigDecimal adjustedAmount){
		this.adjustedAmount = adjustedAmount;
	}
	
	/**
	 * 提现订单号
	 */
	public String getWithdrawNo(){
		return withdrawNo;
	}
	
	/**
	 * 提现订单号
	 */
	public void setWithdrawNo(String withdrawNo){
		this.withdrawNo = withdrawNo;
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
	 * 交易编号
	 */
	public String getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 交易编号
	 */
	public void setOrderNum(String orderNum){
		this.orderNum = orderNum;
	}
	
	/**
	 * 用户名称
	 */
	public String getPartyName(){
		return partyName;
	}
	
	/**
	 * 用户名称
	 */
	public void setPartyName(String partyName){
		this.partyName = partyName;
	}
	
	/**
	 * 订单类型 10 资产大买办 20 配资乐 30 处置服务 31尽调报告
	 */
	public Integer getOrderType(){
		return orderType;
	}
	
	/**
	 * 订单类型 10 资产大买办 20 配资乐 30 处置服务 31尽调报告
	 */
	public void setOrderType(Integer orderType){
		this.orderType = orderType;
	}
	
	/**
	 * 交易日期
	 */
	public java.util.Date getOrderTime(){
		return orderTime;
	}
	
	/**
	 * 交易日期
	 */
	public void setOrderTime(java.util.Date orderTime){
		this.orderTime = orderTime;
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
	 * 修改时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 修改时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}
