
package com._360pai.core.model.disposal;

import java.util.Date;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月07日 13时51分18秒
 */
public class TDisposeLevel implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 服务商id
	 */
	private Integer providerId;
	/**
	 * 城市id
	 */
	private Integer cityId;
	/**
	 * 等级标志 10:一级合伙人
	 */
	private String levelFlag;
	/**
	 * 违约记录
	 */
	private Integer surveyRefuseNum;
	/**
	 * 操作员id
	 */
	private Integer operatorId;
	/**
	 * 合同日期
	 */
	private Date contractDate;
	/**
	 * 合同编号
	 */
	private String contractNo;
	/**
	 * 替换时间  0: 有效 time: 被替换时间
	 */
	private String replaceTime;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	private String provinceName;// 省名称
	private String cityName;// 市名称
	private String contactName;// 联系人
	private String contactMobile;// 联系电话
	private String companyName;//公司名称

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
	 * 服务商id
	 */
	public Integer getProviderId(){
		return providerId;
	}
	
	/**
	 * 服务商id
	 */
	public void setProviderId(Integer providerId){
		this.providerId = providerId;
	}
	
	/**
	 * 城市id
	 */
	public Integer getCityId(){
		return cityId;
	}
	
	/**
	 * 城市id
	 */
	public void setCityId(Integer cityId){
		this.cityId = cityId;
	}

	/**
	 * 等级标志 10:一级合伙人
	 */
	public String getLevelFlag(){
		return levelFlag;
	}
	
	/**
	 * 等级标志 10:一级合伙人
	 */
	public void setLevelFlag(String levelFlag){
		this.levelFlag = levelFlag;
	}
	
	/**
	 * 违约记录
	 */
	public Integer getSurveyRefuseNum(){
		return surveyRefuseNum;
	}
	
	/**
	 * 违约记录
	 */
	public void setSurveyRefuseNum(Integer surveyRefuseNum){
		this.surveyRefuseNum = surveyRefuseNum;
	}
	
	/**
	 * 操作员id
	 */
	public Integer getOperatorId(){
		return operatorId;
	}
	
	/**
	 * 操作员id
	 */
	public void setOperatorId(Integer operatorId){
		this.operatorId = operatorId;
	}
	
	/**
	 * 合同日期
	 */
	public Date getContractDate(){
		return contractDate;
	}
	
	/**
	 * 合同日期
	 */
	public void setContractDate(Date contractDate){
		this.contractDate = contractDate;
	}
	
	/**
	 * 合同编号
	 */
	public String getContractNo(){
		return contractNo;
	}
	
	/**
	 * 合同编号
	 */
	public void setContractNo(String contractNo){
		this.contractNo = contractNo;
	}
	
	/**
	 * 替换时间  0: 有效 time: 被替换时间
	 */
	public String getReplaceTime(){
		return replaceTime;
	}
	
	/**
	 * 替换时间  0: 有效 time: 被替换时间
	 */
	public void setReplaceTime(String replaceTime){
		this.replaceTime = replaceTime;
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

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
