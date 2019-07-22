
package com._360pai.core.model.activity;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月28日 14时05分16秒
 */
public class TAuctionOfflineFinance implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * auction_order id
	 */
	private Long orderId;
	/**
	 * 尾款+佣金/佣金
	 */
	private String financeType;
	/**
	 * 拍品名称
	 */
	private String auctionName;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 用户手机
	 */
	private String userMobile;
	/**
	 * 角色类型
	 */
	private String roleType;
	/**
	 * 应收金额合计
	 */
	private String shouldReceiveTotalAmount;
	/**
	 * 实收金额合计
	 */
	private String actualReceiveTotalAmount;
	/**
	 * 尾款金额
	 */
	private String remainAmount;
	/**
	 * 我方收取/三方收取
	 */
	private String receiveRemainType;
	/**
	 * 尾款收取描述
	 */
	private String receiveRemainRemark;
	/**
	 * 应收佣金
	 */
	private String shouldReceiveCommissionAmount;
	/**
	 * 实收佣金
	 */
	private String actualReceiveCommissionAmount;
	/**
	 * 一致/线下协商
	 */
	private String receiveCommissionType;
	/**
	 * 已确认/未确认
	 */
	private String staus;
	/**
	 * 操作人id
	 */
	private Integer operatorId;
	/**
	 * 
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
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
	
	/**
	 * auction_order id
	 */
	public Long getOrderId(){
		return orderId;
	}
	
	/**
	 * auction_order id
	 */
	public void setOrderId(Long orderId){
		this.orderId = orderId;
	}
	
	/**
	 * 尾款+佣金/佣金
	 */
	public String getFinanceType(){
		return financeType;
	}
	
	/**
	 * 尾款+佣金/佣金
	 */
	public void setFinanceType(String financeType){
		this.financeType = financeType;
	}
	
	/**
	 * 拍品名称
	 */
	public String getAuctionName(){
		return auctionName;
	}
	
	/**
	 * 拍品名称
	 */
	public void setAuctionName(String auctionName){
		this.auctionName = auctionName;
	}
	
	/**
	 * 用户名称
	 */
	public String getUserName(){
		return userName;
	}
	
	/**
	 * 用户名称
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	/**
	 * 用户手机
	 */
	public String getUserMobile(){
		return userMobile;
	}
	
	/**
	 * 用户手机
	 */
	public void setUserMobile(String userMobile){
		this.userMobile = userMobile;
	}
	
	/**
	 * 角色类型
	 */
	public String getRoleType(){
		return roleType;
	}
	
	/**
	 * 角色类型
	 */
	public void setRoleType(String roleType){
		this.roleType = roleType;
	}
	
	/**
	 * 应收金额合计
	 */
	public String getShouldReceiveTotalAmount(){
		return shouldReceiveTotalAmount;
	}
	
	/**
	 * 应收金额合计
	 */
	public void setShouldReceiveTotalAmount(String shouldReceiveTotalAmount){
		this.shouldReceiveTotalAmount = shouldReceiveTotalAmount;
	}
	
	/**
	 * 实收金额合计
	 */
	public String getActualReceiveTotalAmount(){
		return actualReceiveTotalAmount;
	}
	
	/**
	 * 实收金额合计
	 */
	public void setActualReceiveTotalAmount(String actualReceiveTotalAmount){
		this.actualReceiveTotalAmount = actualReceiveTotalAmount;
	}
	
	/**
	 * 尾款金额
	 */
	public String getRemainAmount(){
		return remainAmount;
	}
	
	/**
	 * 尾款金额
	 */
	public void setRemainAmount(String remainAmount){
		this.remainAmount = remainAmount;
	}
	
	/**
	 * 我方收取/三方收取
	 */
	public String getReceiveRemainType(){
		return receiveRemainType;
	}
	
	/**
	 * 我方收取/三方收取
	 */
	public void setReceiveRemainType(String receiveRemainType){
		this.receiveRemainType = receiveRemainType;
	}
	
	/**
	 * 尾款收取描述
	 */
	public String getReceiveRemainRemark(){
		return receiveRemainRemark;
	}
	
	/**
	 * 尾款收取描述
	 */
	public void setReceiveRemainRemark(String receiveRemainRemark){
		this.receiveRemainRemark = receiveRemainRemark;
	}
	
	/**
	 * 应收佣金
	 */
	public String getShouldReceiveCommissionAmount(){
		return shouldReceiveCommissionAmount;
	}
	
	/**
	 * 应收佣金
	 */
	public void setShouldReceiveCommissionAmount(String shouldReceiveCommissionAmount){
		this.shouldReceiveCommissionAmount = shouldReceiveCommissionAmount;
	}
	
	/**
	 * 实收佣金
	 */
	public String getActualReceiveCommissionAmount(){
		return actualReceiveCommissionAmount;
	}
	
	/**
	 * 实收佣金
	 */
	public void setActualReceiveCommissionAmount(String actualReceiveCommissionAmount){
		this.actualReceiveCommissionAmount = actualReceiveCommissionAmount;
	}
	
	/**
	 * 一致/线下协商
	 */
	public String getReceiveCommissionType(){
		return receiveCommissionType;
	}
	
	/**
	 * 一致/线下协商
	 */
	public void setReceiveCommissionType(String receiveCommissionType){
		this.receiveCommissionType = receiveCommissionType;
	}
	
	/**
	 * 已确认/未确认
	 */
	public String getStaus(){
		return staus;
	}
	
	/**
	 * 已确认/未确认
	 */
	public void setStaus(String staus){
		this.staus = staus;
	}
	
	/**
	 * 操作人id
	 */
	public Integer getOperatorId(){
		return operatorId;
	}
	
	/**
	 * 操作人id
	 */
	public void setOperatorId(Integer operatorId){
		this.operatorId = operatorId;
	}
	
	/**
	 * 
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 
	 */
	public void setDeleteFlag(Boolean deleteFlag){
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
