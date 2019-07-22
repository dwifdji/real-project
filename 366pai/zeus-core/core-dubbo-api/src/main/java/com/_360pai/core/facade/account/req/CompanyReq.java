package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import com._360pai.core.facade.account.vo.CompanyVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by RuQ on 2018/8/24 15:17
 */
public class CompanyReq implements Serializable {
    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 公司名称
     */
    private java.lang.String name;
    /**
     * 账户Id
     */
    private java.lang.Integer accountId;
    /**
     * 企业认证送拍机构
     */
    private java.lang.Integer defaultAgencyId;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 法人
     */
    private java.lang.String legalPerson;
    /**
     * 营业执照号码
     */
    private java.lang.String license;
    /**
     * 营业执照图片
     */
    private java.lang.String licenseImg;
    /**
     * 注册城市
     */
    private java.lang.String registerCityId;
    /**
     * 办公城市
     */
    private java.lang.String cityId;
    /**
     * 注册地址
     */
    private java.lang.String registerAddress;
    /**
     * 办公地址
     */
    private java.lang.String address;
    /**
     * 机构id
     */
    private java.lang.Integer agencyId;
    /**
     * 营业起始
     */
    private java.util.Date qualifiedBegin;
    /**
     * 营业结束
     */
    private java.util.Date qualifiedEnd;
    /**
     * 授权书
     */
    private java.lang.String authorizationImg;
    /**
     * 开户许可证
     */
    private java.lang.String accountLicense;
    /**
     * 身份证号
     */
    private java.lang.String idCard;
    /**
     * 身份证正面照
     */
    private java.lang.String idCardFrontImg;
    /**
     * 身份证背面照
     */
    private java.lang.String idCardBackImg;
    /**
     * 状态(0:无效,1:有效)
     */
    private java.lang.Integer status;
    /**
     * 法大大Id
     */
    private java.lang.String fadadaId;
    /**
     * 法大大email
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
     * 是否为通道支付类企业
     */
    private java.lang.Integer channelPay;
    /**
     * 是否是代理商(0:否,1:是)
     */
    private java.lang.Integer isChannel;
    /**
     * 1:正常人，2:银行,3:AMC 资产管理公司,4:民营资管，5:普通公司,6:拍卖公司
     */
    private java.lang.String category;
    /**
     * 收款开户名
     */
    private java.lang.String bankAccountName;
    /**
     * 收款银行账户
     */
    private java.lang.String bankAccountNumber;
    /**
     * 银行Id
     */
    private java.lang.Integer bankId;
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getDefaultAgencyId() {
        return defaultAgencyId;
    }

    public void setDefaultAgencyId(Integer defaultAgencyId) {
        this.defaultAgencyId = defaultAgencyId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getChannelPay() {
        return channelPay;
    }

    public void setChannelPay(Integer channelPay) {
        this.channelPay = channelPay;
    }

    public Integer getIsChannel() {
        return isChannel;
    }

    public void setIsChannel(Integer isChannel) {
        this.isChannel = isChannel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
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

    @Getter
    @Setter
    public static class BaseReq extends RequestModel {
        private Integer partyId;
        private Integer accountId;
        private Integer companyId;
    }

    @Getter
    @Setter
    public static class QueryReq extends RequestModel {
        private String q;
        private String status;
        private Boolean isStaff;
        /**
         * 省id
         */
        private Integer provinceId;
        /**
         * 城市id
         */
        private Integer cityId;
        /**
         * 区县id
         */
        private Integer areaId;
        private Integer defaultAgencyId;
        private Boolean isChannelAgent;
        private Boolean isInBlackList;
        private Integer channelPay;
        private String category;
    }

    @Getter
    @Setter
    public static class UpdateReq extends RequestModel {
        /**
         * 主键
         */
        @NotNull
        private java.lang.Integer id;
        /**
         * 企业认证送拍机构
         */
        private java.lang.Integer defaultAgencyId;
        /**
         * 法人
         */
        private java.lang.String legalPerson;
        /**
         * 营业执照图片
         */
        private java.lang.String licenseImg;
        /**
         * 注册城市
         */
        private java.lang.String registerCityId;
        /**
         * 办公城市
         */
        private java.lang.String cityId;
        /**
         * 省id
         */
        private java.lang.String provinceId;
        /**
         * 注册省id
         */
        private java.lang.String registerProvinceId;
        /**
         * 区县id
         */
        private java.lang.String areaId;
        /**
         * 注册区县id
         */
        private java.lang.String registerAreaId;
        /**
         * 注册地址
         */
        private java.lang.String registerAddress;
        /**
         * 办公地址
         */
        private java.lang.String address;
        /**
         * 机构id
         */
        private java.lang.Integer agencyId;
        /**
         * 营业起始
         */
        private java.util.Date qualifiedBegin;
        /**
         * 营业结束
         */
        private java.util.Date qualifiedEnd;
        /**
         * 授权书
         */
        private java.lang.String authorizationImg;
        /**
         * 开户许可证
         */
        private java.lang.String accountLicense;
        /**
         * 身份证号
         */
        private java.lang.String idCard;
        /**
         * 身份证正面照
         */
        private java.lang.String idCardFrontImg;
        /**
         * 身份证背面照
         */
        private java.lang.String idCardBackImg;
        /**
         * 状态(0:无效,1:有效)
         */
        private java.lang.Integer status;
        /**
         * 1:正常人，2:银行,3:AMC 资产管理公司,4:民营资管，5:普通公司,6:拍卖公司
         */
        private java.lang.String category;
        /**
         * 是否允许未开通法大大下发布预招商 0 否 1 是
         */
        private java.lang.Boolean operWithoutFadada;
        /**
         * 开户人员
         */
        private java.lang.Integer openAccountOperatorId;
        /**
         * 业务对接人
         */
        private java.lang.Integer businessOperatorId;
    }

    @Getter
    @Setter
    public static class CreateChannelPayCompanyReq extends RequestModel {
        /**
         * 公司名称
         */
        @NotBlank
        private java.lang.String name;
        /**
         * 企业认证送拍机构
         */
        @NotNull
        private java.lang.Integer defaultAgencyId;
        /**
         * 手机号
         */
        @NotBlank
        private java.lang.String mobile;
        /**
         * 法人
         */
        @NotBlank
        private java.lang.String legalPerson;
        /**
         * 营业执照号码
         */
        @NotBlank
        private java.lang.String license;
        /**
         * 注册城市
         */
        @NotBlank
        private java.lang.String registerCityId;
        /**
         * 办公城市
         */
        @NotBlank
        private java.lang.String cityId;
        /**
         * 省id
         */
        private java.lang.String provinceId;
        /**
         * 注册省id
         */
        private java.lang.String registerProvinceId;
        /**
         * 区县id
         */
        private java.lang.String areaId;
        /**
         * 注册区县id
         */
        private java.lang.String registerAreaId;
        /**
         * 注册地址
         */
        @NotBlank
        private java.lang.String registerAddress;
        /**
         * 办公地址
         */
        @NotBlank
        private java.lang.String address;
        /**
         * 营业起始
         */
        @NotNull
        private java.util.Date qualifiedBegin;
        /**
         * 营业结束
         */
        @NotNull
        private java.util.Date qualifiedEnd;
        /**
         * 1:正常人，2:银行,3:AMC 资产管理公司,4:民营资管，5:普通公司,6:拍卖公司
         */
        @NotBlank
        private java.lang.String category;
    }

    @Getter
    @Setter
    public static class AddMemberReq extends RequestModel {
        private Integer accountId;
        private Integer partyId;
        @NotBlank
        private String mobile;
        @NotBlank
        private String name;
    }

    @Getter
    @Setter
    public static class DeleteMemberReq extends RequestModel {
        private Integer accountId;
        private Integer partyId;
        @NotNull
        private Integer memberId;
    }

    @Getter
    @Setter
    public static class ChangeAdminReq extends RequestModel {
        @NotNull
        private Integer companyId;
        @NotNull
        private Integer accountId;
    }
}
