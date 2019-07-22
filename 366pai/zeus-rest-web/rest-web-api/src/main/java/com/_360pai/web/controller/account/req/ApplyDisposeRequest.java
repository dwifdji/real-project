package com._360pai.web.controller.account.req;

/**
 * Created by RuQ on 2018/8/28 9:13
 */
public class ApplyDisposeRequest {


    /**
     * 公司名称
     */
    private java.lang.String companyName;
    /**
     * 公司类型
     */
    private java.lang.String companyType;
    /**
     * 注册地址
     */
    private java.lang.String registerAddress;
    /**
     * 注册资本
     */
    private java.lang.Long registerCapital;
    /**
     * 联系人姓名
     */
    private java.lang.String contactName;
    /**
     * 联系方式
     */
    private java.lang.String contactMobile;
    /**
     * 资质证书
     */
    private java.lang.String qualificationUrl;
    /**
     * 工作期限
     */
    private java.lang.Integer workYear;
    /**
     * 自我介绍
     */
    private java.lang.String introduction;
    /**
     * 过往案例
     */
    private java.lang.String caseUrl;
    /**
     * 可提供服务
     */
    private java.lang.String provideService;
    /**
     * 处置类型
     */
    private java.lang.String disposeType;


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public Long getRegisterCapital() {
        return registerCapital;
    }

    public void setRegisterCapital(Long registerCapital) {
        this.registerCapital = registerCapital;
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

    public String getQualificationUrl() {
        return qualificationUrl;
    }

    public void setQualificationUrl(String qualificationUrl) {
        this.qualificationUrl = qualificationUrl;
    }

    public Integer getWorkYear() {
        return workYear;
    }

    public void setWorkYear(Integer workYear) {
        this.workYear = workYear;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCaseUrl() {
        return caseUrl;
    }

    public void setCaseUrl(String caseUrl) {
        this.caseUrl = caseUrl;
    }

    public String getProvideService() {
        return provideService;
    }

    public void setProvideService(String provideService) {
        this.provideService = provideService;
    }

    public String getDisposeType() {
        return disposeType;
    }

    public void setDisposeType(String disposeType) {
        this.disposeType = disposeType;
    }
}
