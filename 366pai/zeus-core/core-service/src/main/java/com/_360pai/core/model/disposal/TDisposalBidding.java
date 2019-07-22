
package com._360pai.core.model.disposal;

import java.util.Date;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月14日 10时33分09秒
 */
public class TDisposalBidding implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private Integer id;
	private Integer biddingId;
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
	 * 10、投标中  20、已完成  30、撮合成功
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
	/**
	 * 需求名称
	 */
	private String disposalName;
	/**
	 * 处置单号
	 */
	private String disposalNo;
	/**
	 * 处置类型: 10：尽调 11：评估 12：司法处置
	 13：执行处置 14：清房 15：催收  16：查找财产线索
	 */
	private String disposalType;
	/**
	 * 平台标识  0：非平台 1：平台
	 */
	private Integer isPlatform;
	/**
	 * 处置服务商名称
	 */
	private String companyName;
	/**
	 * 服务商联系方式
	 */
	private String contactMobile;

	/**
	 * 投标时间
	 */
	private Date biddingTime;

    /**
     * 公司地址
     */
    private String registerAddress;

    /**
     * 注册资本
     */
    private Integer registerCapital;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 公司类型
     */
    private String companyType;

    /**
     * 资质证书
     */
    private String qualificationUrl;

    private String region;
    private String disposeType;

	public Integer getBiddingId() {
		return biddingId;
	}

	public void setBiddingId(Integer biddingId) {
		this.biddingId = biddingId;
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

	public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
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

	public String getDisposalName() {
		return disposalName;
	}

	public void setDisposalName(String disposalName) {
		this.disposalName = disposalName;
	}

	public String getDisposalNo() {
		return disposalNo;
	}

	public void setDisposalNo(String disposalNo) {
		this.disposalNo = disposalNo;
	}

	public String getDisposalType() {
		return disposalType;
	}

	public void setDisposalType(String disposalType) {
		this.disposalType = disposalType;
	}

	public Integer getIsPlatform() {
		return isPlatform;
	}

	public void setIsPlatform(Integer isPlatform) {
		this.isPlatform = isPlatform;
	}

	public String getBidNo() {
		return bidNo;
	}

	public void setBidNo(String bidNo) {
		this.bidNo = bidNo;
	}

	public String getBidStatus() {
		return bidStatus;
	}

	public void setBidStatus(String bidStatus) {
		this.bidStatus = bidStatus;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public Date getBiddingTime() {
		return biddingTime;
	}

	public void setBiddingTime(Date biddingTime) {
		this.biddingTime = biddingTime;
	}

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public Integer getRegisterCapital() {
        return registerCapital;
    }

    public void setRegisterCapital(Integer registerCapital) {
        this.registerCapital = registerCapital;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getQualificationUrl() {
        return qualificationUrl;
    }

    public void setQualificationUrl(String qualificationUrl) {
        this.qualificationUrl = qualificationUrl;
    }

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

    public String getDisposeType() {
        return disposeType;
    }

    public void setDisposeType(String disposeType) {
        this.disposeType = disposeType;
    }
}
