
package com._360pai.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月29日 13时21分37秒
 */
public class TPersona implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 客户类型（10 个人 20 公司）
	 */
	private String userType;
	/**
	 * 用户状态 10 电话空号、20 电话无人接听、30 不愿意合作、40 已建立沟通、50 已展开合作、60 接听电话被拒绝
	 */
	private String userStatus;
	/**
	 * 联系人姓名
	 */
	private String contactName;
	/**
	 * 联系人手机号
	 */
	private String contactPhone;
	/**
	 * 联系人微信
	 */
	private String contactWechat;
	/**
	 * 职务
	 */
	private String title;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 公司负责人
	 */
	private String companyDirector;
	/**
	 * 公司地址
	 */
	private String companyAddress;
	/**
	 * 业务区域
	 */
	private String companyBusinessArea;
	/**
	 * 企业类型 10 民营 20 合资 30 国有企业 40 银行 50 个人
	 */
	private String companyType;
	/**
	 * 录入人员
	 */
	private String dataEntryStaff;
	/**
	 * 客户类型（10 竞买人 20 资产方 30 处置方 40 中介方 50 联拍机构 60 配资机构）
	 */
	private String customerType;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 是否删除（0 否 1 是）
	 */
	private Boolean isDelete = false;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;
	
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
	 * 客户类型（10 个人 20 公司）
	 */
	public String getUserType(){
		return userType;
	}
	
	/**
	 * 客户类型（10 个人 20 公司）
	 */
	public void setUserType(String userType){
		this.userType = userType;
	}
	
	/**
	 * 用户状态 10 已建立沟通 20 展开业务合作 30  不愿意合作
	 */
	public String getUserStatus(){
		return userStatus;
	}
	
	/**
	 * 用户状态 10 已建立沟通 20 展开业务合作 30  不愿意合作
	 */
	public void setUserStatus(String userStatus){
		this.userStatus = userStatus;
	}
	
	/**
	 * 联系人姓名
	 */
	public String getContactName(){
		return contactName;
	}
	
	/**
	 * 联系人姓名
	 */
	public void setContactName(String contactName){
		this.contactName = contactName;
	}
	
	/**
	 * 联系人手机号
	 */
	public String getContactPhone(){
		return contactPhone;
	}
	
	/**
	 * 联系人手机号
	 */
	public void setContactPhone(String contactPhone){
		this.contactPhone = contactPhone;
	}
	
	/**
	 * 联系人微信
	 */
	public String getContactWechat(){
		return contactWechat;
	}
	
	/**
	 * 联系人微信
	 */
	public void setContactWechat(String contactWechat){
		this.contactWechat = contactWechat;
	}
	
	/**
	 * 职务
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * 职务
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * 公司名称
	 */
	public String getCompanyName(){
		return companyName;
	}
	
	/**
	 * 公司名称
	 */
	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}
	
	/**
	 * 公司负责人
	 */
	public String getCompanyDirector(){
		return companyDirector;
	}
	
	/**
	 * 公司负责人
	 */
	public void setCompanyDirector(String companyDirector){
		this.companyDirector = companyDirector;
	}
	
	/**
	 * 公司地址
	 */
	public String getCompanyAddress(){
		return companyAddress;
	}
	
	/**
	 * 公司地址
	 */
	public void setCompanyAddress(String companyAddress){
		this.companyAddress = companyAddress;
	}
	
	/**
	 * 业务区域
	 */
	public String getCompanyBusinessArea(){
		return companyBusinessArea;
	}
	
	/**
	 * 业务区域
	 */
	public void setCompanyBusinessArea(String companyBusinessArea){
		this.companyBusinessArea = companyBusinessArea;
	}
	
	/**
	 * 企业类型 10 民营 20 合资 30 国有企业 40 银行 50 个人
	 */
	public String getCompanyType(){
		return companyType;
	}
	
	/**
	 * 企业类型 10 民营 20 合资 30 国有企业 40 银行 50 个人
	 */
	public void setCompanyType(String companyType){
		this.companyType = companyType;
	}
	
	/**
	 * 录入人员
	 */
	public String getDataEntryStaff(){
		return dataEntryStaff;
	}
	
	/**
	 * 录入人员
	 */
	public void setDataEntryStaff(String dataEntryStaff){
		this.dataEntryStaff = dataEntryStaff;
	}
	
	/**
	 * 客户类型（10 竞买人 20 资产方 30 处置方 40 中介方 50 联拍机构 60 配资机构）
	 */
	public String getCustomerType(){
		return customerType;
	}
	
	/**
	 * 客户类型（10 竞买人 20 资产方 30 处置方 40 中介方 50 联拍机构 60 配资机构）
	 */
	public void setCustomerType(String customerType){
		this.customerType = customerType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 是否删除（0 否 1 是）
	 */
	public Boolean getIsDelete(){
		return isDelete;
	}
	
	/**
	 * 是否删除（0 否 1 是）
	 */
	public void setIsDelete(Boolean isDelete){
		this.isDelete = isDelete;
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
