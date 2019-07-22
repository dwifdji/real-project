
package com.tzCloud.gateway.model.pay;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年06月19日 10时08分47秒
 */
public class TGatewayPayOrder implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 支付订单号
	 */
	private String orderNum;
	/**
	 * 原始支付订单号
	 */
	private String originalOrderNum;
	/**
	 * 内部订单号
	 */
	private String tradeOrderId;
	/**
	 * 平台用户id
	 */
	private Long partyId;
	/**
	 * 业务关键字
	 */
	private String busId;
	/**
	 * 支付类型
	 */
	private String payType;
	/**
	 * 支付的业务码
	 */
	private String payBusCode;
	/**
	 * 付款方会员号
	 */
	private String payMemCode;
	/**
	 * 付款方会员名称
	 */
	private String payMemName;
	/**
	 * 收款方会员号
	 */
	private String recMemCode;
	/**
	 * 是否是跳转支付 1 页面跳转支付 2 直接支付
	 */
	private String jumpPay;
	/**
	 * 锁定标识
	 */
	private String lockTag;
	/**
	 * 收款方会员名称
	 */
	private String recMemName;
	/**
	 * 支付金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 支付状态
	 */
	private String payStatus;
	/**
	 * 支付提示
	 */
	private String msg;
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
	 * 支付订单号
	 */
	public String getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 支付订单号
	 */
	public void setOrderNum(String orderNum){
		this.orderNum = orderNum;
	}
	
	/**
	 * 原始支付订单号
	 */
	public String getOriginalOrderNum(){
		return originalOrderNum;
	}
	
	/**
	 * 原始支付订单号
	 */
	public void setOriginalOrderNum(String originalOrderNum){
		this.originalOrderNum = originalOrderNum;
	}
	
	/**
	 * 内部订单号
	 */
	public String getTradeOrderId(){
		return tradeOrderId;
	}
	
	/**
	 * 内部订单号
	 */
	public void setTradeOrderId(String tradeOrderId){
		this.tradeOrderId = tradeOrderId;
	}
	
	/**
	 * 平台用户id
	 */
	public Long getPartyId(){
		return partyId;
	}
	
	/**
	 * 平台用户id
	 */
	public void setPartyId(Long partyId){
		this.partyId = partyId;
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
	 * 支付类型
	 */
	public String getPayType(){
		return payType;
	}
	
	/**
	 * 支付类型
	 */
	public void setPayType(String payType){
		this.payType = payType;
	}
	
	/**
	 * 支付的业务码
	 */
	public String getPayBusCode(){
		return payBusCode;
	}
	
	/**
	 * 支付的业务码
	 */
	public void setPayBusCode(String payBusCode){
		this.payBusCode = payBusCode;
	}
	
	/**
	 * 付款方会员号
	 */
	public String getPayMemCode(){
		return payMemCode;
	}
	
	/**
	 * 付款方会员号
	 */
	public void setPayMemCode(String payMemCode){
		this.payMemCode = payMemCode;
	}
	
	/**
	 * 付款方会员名称
	 */
	public String getPayMemName(){
		return payMemName;
	}
	
	/**
	 * 付款方会员名称
	 */
	public void setPayMemName(String payMemName){
		this.payMemName = payMemName;
	}
	
	/**
	 * 收款方会员号
	 */
	public String getRecMemCode(){
		return recMemCode;
	}
	
	/**
	 * 收款方会员号
	 */
	public void setRecMemCode(String recMemCode){
		this.recMemCode = recMemCode;
	}
	
	/**
	 * 是否是跳转支付 1 页面跳转支付 2 直接支付
	 */
	public String getJumpPay(){
		return jumpPay;
	}
	
	/**
	 * 是否是跳转支付 1 页面跳转支付 2 直接支付
	 */
	public void setJumpPay(String jumpPay){
		this.jumpPay = jumpPay;
	}
	
	/**
	 * 锁定标识
	 */
	public String getLockTag(){
		return lockTag;
	}
	
	/**
	 * 锁定标识
	 */
	public void setLockTag(String lockTag){
		this.lockTag = lockTag;
	}
	
	/**
	 * 收款方会员名称
	 */
	public String getRecMemName(){
		return recMemName;
	}
	
	/**
	 * 收款方会员名称
	 */
	public void setRecMemName(String recMemName){
		this.recMemName = recMemName;
	}
	
	/**
	 * 支付金额
	 */
	public java.math.BigDecimal getAmount(){
		return amount;
	}
	
	/**
	 * 支付金额
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}
	
	/**
	 * 支付状态
	 */
	public String getPayStatus(){
		return payStatus;
	}
	
	/**
	 * 支付状态
	 */
	public void setPayStatus(String payStatus){
		this.payStatus = payStatus;
	}
	
	/**
	 * 支付提示
	 */
	public String getMsg(){
		return msg;
	}
	
	/**
	 * 支付提示
	 */
	public void setMsg(String msg){
		this.msg = msg;
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
