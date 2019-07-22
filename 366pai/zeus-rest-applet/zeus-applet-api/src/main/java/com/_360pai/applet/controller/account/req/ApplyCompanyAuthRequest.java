package com._360pai.applet.controller.account.req;

/**
 * Created by RuQ on 2018/8/23 13:55
 */
public class ApplyCompanyAuthRequest {

    /**
     * 公司名称
     */
    private String name;

    /**
     * 默认送拍机构
     */
    private Integer defaultAgencyId;

    /**
     * 办公地址
     */
    private String address;
    /**
     * 注册地址
     */
    private String registerAddress;
    /**
     * 城市Id
     */
    private String cityId;
    /**
     * 注册城市Id
     */
    private String registerCityId;
    /**
     * 营业执照
     */
    private String license;
    /**
     * 营业执照图片
     */
    private String licenseImg;
    /**
     * 法人
     */
    private String legalPerson;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 身份证正面照
     */
    private String idCardFrontImg;
    /**
     * 身份证背面照
     */
    private String idCardBackImg;
    /**
     * 授权书
     */
    private String authorizationImg;
    /**
     * 开户许可证
     */
    private String accountLicense;

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
