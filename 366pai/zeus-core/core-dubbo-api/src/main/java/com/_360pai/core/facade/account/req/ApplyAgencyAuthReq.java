package com._360pai.core.facade.account.req;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by RuQ on 2018/8/23 14:24
 */
public class ApplyAgencyAuthReq implements Serializable {

    /**
     * 主键
     */
    private java.lang.Long id;
    /**
     * 账户Id
     */
    private java.lang.Integer accountId;
    /**
     * 机构名
     */
    private java.lang.String name;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 机构编号
     */
    private java.lang.String code;
    /**
     * 简称
     */
    private java.lang.String shortName;
    /**
     * 注册地址
     */
    private java.lang.String registerAddress;
    /**
     * 办公地址
     */
    private java.lang.String address;
    /**
     * 注册城市
     */
    private java.lang.String registerCityId;
    /**
     * 办公城市
     */
    private java.lang.String cityId;
    /**
     * 开户许可证
     */
    private java.lang.String accountLicense;
    /**
     * 授权书
     */
    private java.lang.String authorizationImg;
    /**
     * 营业开始时间
     */
    private java.util.Date businessBegin;
    /**
     * 营业结束时间
     */
    private java.util.Date businessEnd;
    /**
     * 身份证号
     */
    private java.lang.String idCard;
    /**
     * 身份证反面照
     */
    private java.lang.String idCardBackImg;
    /**
     * 身份证正面照
     */
    private java.lang.String idCardFrontImg;
    /**
     * 法人
     */
    private java.lang.String legalPerson;
    /**
     * 营业执照
     */
    private java.lang.String license;
    /**
     * 营业执照图片
     */
    private java.lang.String licenseImg;
    /**
     * logo图片
     */
    private java.lang.String logoUrl;
    /**
     * 许可证图片
     */
    private java.lang.String qualificationImg;
    /**
     * 许可证编号
     */
    private java.lang.String qualificationNumber;
    /**
     * 许可证签发日期
     */
    private java.util.Date qualifiedBegin;
    /**
     * 许可证结束日期
     */
    private java.util.Date qualifiedEnd;
    /**
     * 审核备注
     */
    private java.lang.String remark;
    /**
     * 成拍分成
     */
    private java.lang.Integer serveBuyerPercent;
    /**
     * 送拍分成
     */
    private java.lang.Integer serveSellerPercent;
    /**
     * 申请状态(PENDING = 1,APPROVED = 2,REJECT = 3)
     */
    private java.lang.String status;
    /**
     * 审核人员Id
     */
    private java.lang.Integer operatorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegisterCityId() {
        return registerCityId;
    }

    public void setRegisterCityId(String registerCityId) {
        this.registerCityId = registerCityId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAccountLicense() {
        return accountLicense;
    }

    public void setAccountLicense(String accountLicense) {
        this.accountLicense = accountLicense;
    }

    public String getAuthorizationImg() {
        return authorizationImg;
    }

    public void setAuthorizationImg(String authorizationImg) {
        this.authorizationImg = authorizationImg;
    }

    public Date getBusinessBegin() {
        return businessBegin;
    }

    public void setBusinessBegin(Date businessBegin) {
        this.businessBegin = businessBegin;
    }

    public Date getBusinessEnd() {
        return businessEnd;
    }

    public void setBusinessEnd(Date businessEnd) {
        this.businessEnd = businessEnd;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardBackImg() {
        return idCardBackImg;
    }

    public void setIdCardBackImg(String idCardBackImg) {
        this.idCardBackImg = idCardBackImg;
    }

    public String getIdCardFrontImg() {
        return idCardFrontImg;
    }

    public void setIdCardFrontImg(String idCardFrontImg) {
        this.idCardFrontImg = idCardFrontImg;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseImg() {
        return licenseImg;
    }

    public void setLicenseImg(String licenseImg) {
        this.licenseImg = licenseImg;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getQualificationImg() {
        return qualificationImg;
    }

    public void setQualificationImg(String qualificationImg) {
        this.qualificationImg = qualificationImg;
    }

    public String getQualificationNumber() {
        return qualificationNumber;
    }

    public void setQualificationNumber(String qualificationNumber) {
        this.qualificationNumber = qualificationNumber;
    }

    public Date getQualifiedBegin() {
        return qualifiedBegin;
    }

    public void setQualifiedBegin(Date qualifiedBegin) {
        this.qualifiedBegin = qualifiedBegin;
    }

    public Date getQualifiedEnd() {
        return qualifiedEnd;
    }

    public void setQualifiedEnd(Date qualifiedEnd) {
        this.qualifiedEnd = qualifiedEnd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getServeBuyerPercent() {
        return serveBuyerPercent;
    }

    public void setServeBuyerPercent(Integer serveBuyerPercent) {
        this.serveBuyerPercent = serveBuyerPercent;
    }

    public Integer getServeSellerPercent() {
        return serveSellerPercent;
    }

    public void setServeSellerPercent(Integer serveSellerPercent) {
        this.serveSellerPercent = serveSellerPercent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
}
