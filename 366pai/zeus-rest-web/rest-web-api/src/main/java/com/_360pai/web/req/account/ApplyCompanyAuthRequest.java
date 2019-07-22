package com._360pai.web.req.account;

/**
 * Created by RuQ on 2018/8/23 13:55
 */
public class ApplyCompanyAuthRequest {

    /**
     * 公司名称
     */
    private java.lang.String name;

    /**
     * 默认送拍机构
     */
    private java.lang.Integer defaultAgencyId;

    /**
     * 办公地址
     */
    private java.lang.String address;
    /**
     * 注册地址
     */
    private java.lang.String registerAddress;
    /**
     * 城市Id
     */
    private java.lang.String cityId;
    /**
     * 注册城市Id
     */
    private java.lang.String registerCityId;
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
     * 身份证背面照
     */
    private java.lang.String idCardBackImg;
    /**
     * 授权书
     */
    private java.lang.String authorizationImg;
    /**
     * 开户许可证
     */
    private java.lang.String accountLicense;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDefaultAgencyId() {
        return defaultAgencyId;
    }

    public void setDefaultAgencyId(Integer defaultAgencyId) {
        this.defaultAgencyId = defaultAgencyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getRegisterCityId() {
        return registerCityId;
    }

    public void setRegisterCityId(String registerCityId) {
        this.registerCityId = registerCityId;
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
}
