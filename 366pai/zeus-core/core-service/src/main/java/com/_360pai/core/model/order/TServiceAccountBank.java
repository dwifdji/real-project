
package com._360pai.core.model.order;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年10月06日 23时43分10秒
 */
public class TServiceAccountBank implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer accountId;
	private Integer partyId;
	/**
	 * 银行卡号
	 */
	private String bankNo;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 开户名称
	 */
	private String userName;
	/**
	 * 支行地址
	 */
	private String bankAddress;
	/**
	 * 当前绑定 0:未绑定  1:绑定
	 */
	private Boolean currentBiding;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;
	/**
	 * 删除标识 0:未删除 1：删除
	 */
	private Boolean delFlag;

	public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

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

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * 银行卡号
	 */
	public String getBankNo(){
		return bankNo;
	}
	
	/**
	 * 银行卡号
	 */
	public void setBankNo(String bankNo){
		this.bankNo = bankNo;
	}
	
	/**
	 * 银行名称
	 */
	public String getBankName(){
		return bankName;
	}
	
	/**
	 * 银行名称
	 */
	public void setBankName(String bankName){
		this.bankName = bankName;
	}
	
	/**
	 * 开户名称
	 */
	public String getUserName(){
		return userName;
	}
	
	/**
	 * 开户名称
	 */
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	/**
	 * 支行地址
	 */
	public String getBankAddress(){
		return bankAddress;
	}
	
	/**
	 * 支行地址
	 */
	public void setBankAddress(String bankAddress){
		this.bankAddress = bankAddress;
	}
	
	/**
	 * 当前绑定 0:未绑定  1:绑定
	 */
	public Boolean getCurrentBiding(){
		return currentBiding;
	}
	
	/**
	 * 当前绑定 0:未绑定  1:绑定
	 */
	public void setCurrentBiding(Boolean currentBiding){
		this.currentBiding = currentBiding;
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
	
	/**
	 * 删除标识 0:未删除 1：删除
	 */
	public Boolean getDelFlag(){
		return delFlag;
	}
	
	/**
	 * 删除标识 0:未删除 1：删除
	 */
	public void setDelFlag(Boolean delFlag){
		this.delFlag = delFlag;
	}

}
