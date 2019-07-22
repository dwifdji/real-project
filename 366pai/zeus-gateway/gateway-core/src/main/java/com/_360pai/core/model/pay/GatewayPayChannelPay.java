
package com._360pai.core.model.pay;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月15日 16时47分12秒
 */
public class GatewayPayChannelPay implements java.io.Serializable{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 支付的接口编号
	 */
	private java.lang.String payType;
	/**
	 * 支付订单的唯一编号
	 */
	private java.lang.String payId;
	/**
	 * 关联支付id 关闭支付时必填
	 */
	private java.lang.String originalPayId;
	/**
	 * 付款方会员号
	 */
	private java.lang.String payMemCode;
	/**
	 * 付款方会员名称
	 */
	private java.lang.String payMemName;
	/**
	 * 付款金额
	 */
	private java.lang.String payAmt;
	/**
	 * 银行code
	 */
	private java.lang.String bankCode;
	/**
	 * 收款方会员名称
	 */
	private java.lang.String recMemName;
	/**
	 * 开户名称
	 */
	private java.lang.String accName;
	/**
	 * 银行账号
	 */
	private java.lang.String accNo;
	/**
	 * 加急标识 T+0 付款标识，0 表示T+0 付款，空或其他为T+1 付款
	 */
	private java.lang.String instCash;
	/**
	 * 付款银行账号
	 */
	private java.lang.String bankAccount;
	/**
	 * 付款银行用途
	 */
	private java.lang.String bankUse;
	/**
	 * 支付状态
	 */
	private java.lang.String status;
	/**
	 * 支付返回信息
	 */
	private java.lang.String msg;
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
	 * 支付的接口编号
	 */
	public java.lang.String getPayType(){
		return payType;
	}
	
	/**
	 * 支付的接口编号
	 */
	public void setPayType(java.lang.String payType){
		this.payType = payType;
	}
	
	/**
	 * 支付订单的唯一编号
	 */
	public java.lang.String getPayId(){
		return payId;
	}
	
	/**
	 * 支付订单的唯一编号
	 */
	public void setPayId(java.lang.String payId){
		this.payId = payId;
	}
	
	/**
	 * 关联支付id 关闭支付时必填
	 */
	public java.lang.String getOriginalPayId(){
		return originalPayId;
	}
	
	/**
	 * 关联支付id 关闭支付时必填
	 */
	public void setOriginalPayId(java.lang.String originalPayId){
		this.originalPayId = originalPayId;
	}
	
	/**
	 * 付款方会员号
	 */
	public java.lang.String getPayMemCode(){
		return payMemCode;
	}
	
	/**
	 * 付款方会员号
	 */
	public void setPayMemCode(java.lang.String payMemCode){
		this.payMemCode = payMemCode;
	}
	
	/**
	 * 付款方会员名称
	 */
	public java.lang.String getPayMemName(){
		return payMemName;
	}
	
	/**
	 * 付款方会员名称
	 */
	public void setPayMemName(java.lang.String payMemName){
		this.payMemName = payMemName;
	}
	
	/**
	 * 付款金额
	 */
	public java.lang.String getPayAmt(){
		return payAmt;
	}
	
	/**
	 * 付款金额
	 */
	public void setPayAmt(java.lang.String payAmt){
		this.payAmt = payAmt;
	}
	
	/**
	 * 银行code
	 */
	public java.lang.String getBankCode(){
		return bankCode;
	}
	
	/**
	 * 银行code
	 */
	public void setBankCode(java.lang.String bankCode){
		this.bankCode = bankCode;
	}
	
	/**
	 * 收款方会员名称
	 */
	public java.lang.String getRecMemName(){
		return recMemName;
	}
	
	/**
	 * 收款方会员名称
	 */
	public void setRecMemName(java.lang.String recMemName){
		this.recMemName = recMemName;
	}
	
	/**
	 * 开户名称
	 */
	public java.lang.String getAccName(){
		return accName;
	}
	
	/**
	 * 开户名称
	 */
	public void setAccName(java.lang.String accName){
		this.accName = accName;
	}
	
	/**
	 * 银行账号
	 */
	public java.lang.String getAccNo(){
		return accNo;
	}
	
	/**
	 * 银行账号
	 */
	public void setAccNo(java.lang.String accNo){
		this.accNo = accNo;
	}
	
	/**
	 * 加急标识 T+0 付款标识，0 表示T+0 付款，空或其他为T+1 付款
	 */
	public java.lang.String getInstCash(){
		return instCash;
	}
	
	/**
	 * 加急标识 T+0 付款标识，0 表示T+0 付款，空或其他为T+1 付款
	 */
	public void setInstCash(java.lang.String instCash){
		this.instCash = instCash;
	}
	
	/**
	 * 付款银行账号
	 */
	public java.lang.String getBankAccount(){
		return bankAccount;
	}
	
	/**
	 * 付款银行账号
	 */
	public void setBankAccount(java.lang.String bankAccount){
		this.bankAccount = bankAccount;
	}
	
	/**
	 * 付款银行用途
	 */
	public java.lang.String getBankUse(){
		return bankUse;
	}
	
	/**
	 * 付款银行用途
	 */
	public void setBankUse(java.lang.String bankUse){
		this.bankUse = bankUse;
	}
	
	/**
	 * 支付状态
	 */
	public java.lang.String getStatus(){
		return status;
	}
	
	/**
	 * 支付状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	
	/**
	 * 支付返回信息
	 */
	public java.lang.String getMsg(){
		return msg;
	}
	
	/**
	 * 支付返回信息
	 */
	public void setMsg(java.lang.String msg){
		this.msg = msg;
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
