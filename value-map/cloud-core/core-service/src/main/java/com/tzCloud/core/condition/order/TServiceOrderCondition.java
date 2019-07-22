
package com.tzCloud.core.condition.order;

import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年06月19日 15时47分59秒
 */
public class TServiceOrderCondition implements DaoCondition{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 支付订单号
	 */
	private String orderNum;
	/**
	 * 订单类型 10 会员费用
	 */
	private Integer orderType;
	/**
	 * t_account表主键
	 */
	private Integer accountId;
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
	 * 支付url
	 */
	private String payUrl;
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
	 * 订单类型 10 会员费用
	 */
	public Integer getOrderType(){
		return orderType;
	}
	
	/**
	 * 订单类型 10 会员费用
	 */
	public void setOrderType(Integer orderType){
		this.orderType = orderType;
	}
	
	/**
	 * t_account表主键
	 */
	public Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * t_account表主键
	 */
	public void setAccountId(Integer accountId){
		this.accountId = accountId;
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
	 * 支付url
	 */
	public String getPayUrl(){
		return payUrl;
	}
	
	/**
	 * 支付url
	 */
	public void setPayUrl(String payUrl){
		this.payUrl = payUrl;
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