package com._360pai.core.facade.disposal.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-09-14 13:51
 */
public class BiddingInfoResp implements Serializable {
    private static final long serialVersionUID = -5112676581721892794L;


    /**
     * 主键id
     */
    private Integer id;
    /**
     * 处置编号
     */
    private String disposalNo;
    /**
     * 需求名称
     */
    private String disposalName;
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
     * 投标编号
     */
    private String bidNo;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 处置状态
     * 10、投标中  20、已完成  30、撮合成功
     */
    private String bidStatus;

    /**
     * 服务费用
     */
    private String bidCost;
    /**
     * 特别说明
     */
    private String specialDescription;
    /**
     * 需求说明书
     */
    private String requireDescription;
    /**
     * 处置服务商名称
     */
    private String companyName;
    /**
     * 服务商联系方式
     */
    private String contactMobile;
    /**
     * 沟通内容
     */
    private String communicatContent;

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


    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBidCost() {
        return bidCost;
    }

    public void setBidCost(String bidCost) {
        this.bidCost = bidCost;
    }

    public String getSpecialDescription() {
        return specialDescription;
    }

    public void setSpecialDescription(String specialDescription) {
        this.specialDescription = specialDescription;
    }

    public String getRequireDescription() {
        return requireDescription;
    }

    public void setRequireDescription(String requireDescription) {
        this.requireDescription = requireDescription;
    }

    public String getDisposalNo() {
        return disposalNo;
    }

    public void setDisposalNo(String disposalNo) {
        this.disposalNo = disposalNo;
    }

    public String getDisposalName() {
        return disposalName;
    }

    public void setDisposalName(String disposalName) {
        this.disposalName = disposalName;
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

    public String getCommunicatContent() {
        return communicatContent;
    }

    public void setCommunicatContent(String communicatContent) {
        this.communicatContent = communicatContent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(Date biddingTime) {
        this.biddingTime = biddingTime;
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

    @Override
    public String toString() {
        return "BiddingInfoResp{" +
                "id=" + id +
                ", disposalNo='" + disposalNo + '\'' +
                ", disposalName='" + disposalName + '\'' +
                ", disposalType='" + disposalType + '\'' +
                ", isPlatform=" + isPlatform +
                ", bidNo='" + bidNo + '\'' +
                ", createTime=" + createTime +
                ", bidStatus=" + bidStatus +
                ", bidCost='" + bidCost + '\'' +
                ", specialDescription='" + specialDescription + '\'' +
                ", requireDescription='" + requireDescription + '\'' +
                ", companyName='" + companyName + '\'' +
                ", contactMobile='" + contactMobile + '\'' +
                ", communicatContent='" + communicatContent + '\'' +
                ", biddingTime=" + biddingTime +
                ", registerAddress='" + registerAddress + '\'' +
                ", registerCapital=" + registerCapital +
                ", contactName='" + contactName + '\'' +
                ", companyType='" + companyType + '\'' +
                ", qualificationUrl='" + qualificationUrl + '\'' +
                '}';
    }
}
