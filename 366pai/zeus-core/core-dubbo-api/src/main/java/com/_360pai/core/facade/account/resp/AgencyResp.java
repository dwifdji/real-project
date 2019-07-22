package com._360pai.core.facade.account.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.account.vo.AgencyVo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by RuQ on 2018/8/17 16:03
 */
public class AgencyResp implements Serializable {

    /**
     * 主键
     */
    private java.lang.Integer id;
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
     * 机构简称
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
     * 营业执照
     */
    private java.lang.String license;
    /**
     * 营业执照图片
     */
    private java.lang.String licenseImg;
    /**
     * 法人
     */
    private java.lang.String legalPerson;
    /**
     * 身份证号
     */
    private java.lang.String idCard;
    /**
     * 身份证正面照
     */
    private java.lang.String idCardFrontImg;
    /**
     * 身份证反面照
     */
    private java.lang.String idCardBackImg;
    /**
     * 许可证签发日期
     */
    private java.util.Date qualifiedBegin;
    /**
     * 许可证结束日期
     */
    private java.util.Date qualifiedEnd;
    /**
     * 许可证图片
     */
    private java.lang.String qualificationImg;
    /**
     * 许可证号
     */
    private java.lang.String qualificationNumber;
    /**
     * 授权书
     */
    private java.lang.String authorizationImg;
    /**
     * 开户许可证
     */
    private java.lang.String accountLicense;
    /**
     * 营业开始时间
     */
    private java.util.Date businessBegin;
    /**
     * 营业结束时间
     */
    private java.util.Date businessEnd;
    /**
     * 备注
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
     * 状态(0:无效,1:有效)
     */
    private java.lang.Integer status;
    /**
     * 机构自我介绍
     */
    private java.lang.String selfIntroduction;
    /**
     * 平台给机构的介绍
     */
    private java.lang.String introduction;
    /**
     * 法大大Id
     */
    private java.lang.String fadadaId;
    /**
     * 法大大邮箱
     */
    private java.lang.String fadadaEmail;
    /**
     * 东方付通Id
     */
    private java.lang.String dfftId;
    /**
     * 是否绑定东方付通
     */
    private java.lang.Integer payBind;
    /**
     * 是否是机构代理商
     */
    private java.lang.Integer isChannelAgent;
    /**
     * logo地址
     */
    private java.lang.String logoUrl;
    /**
     * 站点状态(ONLINE、CLOSED)
     */
    private java.lang.String websiteStatus;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 更新时间
     */
    private java.util.Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardFrontImg() {
        return idCardFrontImg;
    }

    public void setIdCardFrontImg(String idCardFrontImg) {
        this.idCardFrontImg = idCardFrontImg;
    }

    public String getIdCardBackImg() {
        return idCardBackImg;
    }

    public void setIdCardBackImg(String idCardBackImg) {
        this.idCardBackImg = idCardBackImg;
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

    public String getAuthorizationImg() {
        return authorizationImg;
    }

    public void setAuthorizationImg(String authorizationImg) {
        this.authorizationImg = authorizationImg;
    }

    public String getAccountLicense() {
        return accountLicense;
    }

    public void setAccountLicense(String accountLicense) {
        this.accountLicense = accountLicense;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getFadadaId() {
        return fadadaId;
    }

    public void setFadadaId(String fadadaId) {
        this.fadadaId = fadadaId;
    }

    public String getFadadaEmail() {
        return fadadaEmail;
    }

    public void setFadadaEmail(String fadadaEmail) {
        this.fadadaEmail = fadadaEmail;
    }

    public String getDfftId() {
        return dfftId;
    }

    public void setDfftId(String dfftId) {
        this.dfftId = dfftId;
    }

    public Integer getPayBind() {
        return payBind;
    }

    public void setPayBind(Integer payBind) {
        this.payBind = payBind;
    }

    public Integer getIsChannelAgent() {
        return isChannelAgent;
    }

    public void setIsChannelAgent(Integer isChannelAgent) {
        this.isChannelAgent = isChannelAgent;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getWebsiteStatus() {
        return websiteStatus;
    }

    public void setWebsiteStatus(String websiteStatus) {
        this.websiteStatus = websiteStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static class DetailResp extends BaseResp {
        private AgencyVo agency;

        public AgencyVo getAgency() {
            return agency;
        }

        public void setAgency(AgencyVo agency) {
            this.agency = agency;
        }
    }

    @Getter
    @Setter
    public static class DfftResp extends BaseResp {
        private String totalAmt;//全部金额

        private String freeAmt;//可用金额

        private String lockedAmt;//锁定金额

        private String totalAmt360;//全部金额

        private String freeAmt360;//可用金额

        private String lockedAmt360;//锁定金额
    }

    @Getter
    @Setter
    public static class ProfileResp extends BaseResp {
        private Integer accountId;
        private String mobile;
        private Boolean isAgencyAdmin;
        /**
         * 是否可以查看保留价
         */
        private java.lang.Boolean canCheckReservePrice;
    }
}
