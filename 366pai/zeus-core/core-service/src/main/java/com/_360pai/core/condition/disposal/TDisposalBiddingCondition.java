
package com._360pai.core.condition.disposal;

import com._360pai.arch.core.abs.DaoCondition;

import java.util.Date;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月14日 10时33分09秒
 */
public class TDisposalBiddingCondition implements DaoCondition{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 处置需求id
	 */
	private Integer disposalId;
	/**
	 * 投标人id
	 */
	private Integer accountId;
	private Integer partyId;
	/**
	 * 投标编号
	 */
	private String bidNo;
	/**
	 * 服务费用
	 */
	private String bidCost;
	/**
	 * 需求说明书
	 */
	private String requireDescription;
	/**
	 * 特别说明
	 */
	private String specialDescription;
	/**
	 * 操作人id
	 */
	private Integer operatorId;
	/**
	 * 沟通内容
	 */
	private String communicatContent;
	/**
	 * 处置状态
1、投标中 2、撮合成功 3、已完成
	 */
	private String bidStatus;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 删除标识  0：未删除  1：已删除
	 */
	private Integer isDel;

	private Date bidTime;
	private String partyName;
	private String companyName;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

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
	
	/**
	 * 处置需求id
	 */
	public Integer getDisposalId(){
		return disposalId;
	}
	
	/**
	 * 处置需求id
	 */
	public void setDisposalId(Integer disposalId){
		this.disposalId = disposalId;
	}

	public String getBidNo() {
		return bidNo;
	}

	public void setBidNo(String bidNo) {
		this.bidNo = bidNo;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * 服务费用
	 */
	public String getBidCost(){
		return bidCost;
	}
	
	/**
	 * 服务费用
	 */
	public void setBidCost(String bidCost){
		this.bidCost = bidCost;
	}
	
	/**
	 * 需求说明书
	 */
	public String getRequireDescription(){
		return requireDescription;
	}
	
	/**
	 * 需求说明书
	 */
	public void setRequireDescription(String requireDescription){
		this.requireDescription = requireDescription;
	}
	
	/**
	 * 特别说明
	 */
	public String getSpecialDescription(){
		return specialDescription;
	}
	
	/**
	 * 特别说明
	 */
	public void setSpecialDescription(String specialDescription){
		this.specialDescription = specialDescription;
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
	 * 沟通内容
	 */
	public String getCommunicatContent(){
		return communicatContent;
	}
	
	/**
	 * 沟通内容
	 */
	public void setCommunicatContent(String communicatContent){
		this.communicatContent = communicatContent;
	}

	public String getBidStatus() {
		return bidStatus;
	}

	public void setBidStatus(String bidStatus) {
		this.bidStatus = bidStatus;
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
	 * 删除标识  0：未删除  1：已删除
	 */
	public Integer getIsDel(){
		return isDel;
	}
	
	/**
	 * 删除标识  0：未删除  1：已删除
	 */
	public void setIsDel(Integer isDel){
		this.isDel = isDel;
	}

	public Date getBidTime() {
		return bidTime;
	}

	public void setBidTime(Date bidTime) {
		this.bidTime = bidTime;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
}