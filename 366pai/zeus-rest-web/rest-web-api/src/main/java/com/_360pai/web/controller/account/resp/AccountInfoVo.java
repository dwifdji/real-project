package com._360pai.web.controller.account.resp;

import java.util.List;

/**
 * Created by RuQ on 2018/8/24 9:51
 */
public class AccountInfoVo {
    private boolean isAccountAuth;
    private String accountAuthName;
    private String accountAuthTime;
    private boolean isPayBind;
    private String accountAmount;
    private String availableAmount;
    private String lockAmount;

    private String accountAmount360;
    private String availableAmount360;
    private String lockAmount360;

    private boolean isFddBind;
    private String type;
    private Integer defaultAgencyId;
    private String defaultAgencyName;
    private String mobile;
    private String idOrLicenceNo;

    private boolean isFunder;
    private boolean isDisposer;

    private Integer agencyId;
    private boolean isBank;
    private String bankCode;
    private String bankName;
    private String bankAccountNo;  //卡号
    private String bankAccountName;//开户名
    /**
     * 允许发布线下操作拍品 0 否 1 是
     */
    private java.lang.Boolean operOffline;
    /**
     * 是否允许未开通法大大下发布预招商 0 否 1 是
     */
    private java.lang.Boolean operWithoutFadada;

    private List<AccountSimpleVo> accountList;

    private boolean isAdmin;

    private boolean isUserAuth;

    private Integer shopId;

    private String disposerStatus;//处置服务商申请状态

    private String agencyStatus;

    private String fundStatus;


    private boolean leaseReleaseFlag;//融资租赁发布权限

    private boolean leaseAuditFlag;//融资租赁审核权限

    private Integer leaseComId;//员工的公司id

    private Integer leaseStaffId;//员工id

    private String leaseComName;//公司名称

    private String leaseComAddress;//租赁公司地址


    public boolean isLeaseReleaseFlag() {
        return leaseReleaseFlag;
    }

    public void setLeaseReleaseFlag(boolean leaseReleaseFlag) {
        this.leaseReleaseFlag = leaseReleaseFlag;
    }

    public boolean isLeaseAuditFlag() {
        return leaseAuditFlag;
    }

    public void setLeaseAuditFlag(boolean leaseAuditFlag) {
        this.leaseAuditFlag = leaseAuditFlag;
    }

    public Integer getLeaseComId() {
        return leaseComId;
    }

    public void setLeaseComId(Integer leaseComId) {
        this.leaseComId = leaseComId;
    }

    public Integer getLeaseStaffId() {
        return leaseStaffId;
    }

    public void setLeaseStaffId(Integer leaseStaffId) {
        this.leaseStaffId = leaseStaffId;
    }

    public String getLeaseComName() {
        return leaseComName;
    }

    public void setLeaseComName(String leaseComName) {
        this.leaseComName = leaseComName;
    }

    public String getLeaseComAddress() {
        return leaseComAddress;
    }

    public void setLeaseComAddress(String leaseComAddress) {
        this.leaseComAddress = leaseComAddress;
    }

    public String getFundStatus() {
        return fundStatus;
    }

    public void setFundStatus(String fundStatus) {
        this.fundStatus = fundStatus;
    }

    public String getAgencyStatus() {
        return agencyStatus;
    }

    public void setAgencyStatus(String agencyStatus) {
        this.agencyStatus = agencyStatus;
    }

    public String getDisposerStatus() {
        return disposerStatus;
    }

    public void setDisposerStatus(String disposerStatus) {
        this.disposerStatus = disposerStatus;
    }

    public String getAccountAmount360() {
        return accountAmount360;
    }

    public void setAccountAmount360(String accountAmount360) {
        this.accountAmount360 = accountAmount360;
    }

    public String getAvailableAmount360() {
        return availableAmount360;
    }

    public void setAvailableAmount360(String availableAmount360) {
        this.availableAmount360 = availableAmount360;
    }

    public String getLockAmount360() {
        return lockAmount360;
    }

    public void setLockAmount360(String lockAmount360) {
        this.lockAmount360 = lockAmount360;
    }

    /**
     * 是否是一级合伙人
     */
    private boolean isFirstLevelProvider;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<AccountSimpleVo> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<AccountSimpleVo> accountList) {
        this.accountList = accountList;
    }

    public boolean isFunder() {
        return isFunder;
    }

    public void setFunder(boolean funder) {
        isFunder = funder;
    }

    public boolean isDisposer() {
        return isDisposer;
    }

    public void setDisposer(boolean disposer) {
        isDisposer = disposer;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public String getIdOrLicenceNo() {
        return idOrLicenceNo;
    }

    public void setIdOrLicenceNo(String idOrLicenceNo) {
        this.idOrLicenceNo = idOrLicenceNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDefaultAgencyName() {
        return defaultAgencyName;
    }

    public void setDefaultAgencyName(String defaultAgencyName) {
        this.defaultAgencyName = defaultAgencyName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAccountAuth() {
        return isAccountAuth;
    }

    public void setAccountAuth(boolean accountAuth) {
        isAccountAuth = accountAuth;
    }

    public String getAccountAuthName() {
        return accountAuthName;
    }

    public void setAccountAuthName(String accountAuthName) {
        this.accountAuthName = accountAuthName;
    }

    public String getAccountAuthTime() {
        return accountAuthTime;
    }

    public void setAccountAuthTime(String accountAuthTime) {
        this.accountAuthTime = accountAuthTime;
    }

    public boolean isPayBind() {
        return isPayBind;
    }

    public void setPayBind(boolean payBind) {
        isPayBind = payBind;
    }

    public String getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(String accountAmount) {
        this.accountAmount = accountAmount;
    }

    public String getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(String availableAmount) {
        this.availableAmount = availableAmount;
    }

    public String getLockAmount() {
        return lockAmount;
    }

    public void setLockAmount(String lockAmount) {
        this.lockAmount = lockAmount;
    }

    public boolean isFddBind() {
        return isFddBind;
    }

    public void setFddBind(boolean fddBind) {
        isFddBind = fddBind;
    }

    public boolean getIsBank() {
        return isBank;
    }

    public void setIsBank(boolean isBank) {
        this.isBank = isBank;
    }

    public Boolean getOperOffline() {
        return operOffline;
    }



    public void setOperOffline(Boolean operOffline) {
        this.operOffline = operOffline;
    }

    public Boolean getOperWithoutFadada() {
        return operWithoutFadada;
    }

    public void setOperWithoutFadada(Boolean operWithoutFadada) {
        this.operWithoutFadada = operWithoutFadada;
    }


    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public boolean getIsUserAuth() {
        return isUserAuth;
    }

    public void setIsUserAuth(boolean isUserAuth) {
        this.isUserAuth = isUserAuth;
    }

   public Integer getDefaultAgencyId() {
        return defaultAgencyId;
    }

    public void setDefaultAgencyId(Integer defaultAgencyId) {
        this.defaultAgencyId = defaultAgencyId;
    }
	public boolean isBank() {
        return isBank;
    }

    public void setBank(boolean bank) {
        isBank = bank;
    }

    public boolean isUserAuth() {
        return isUserAuth;
    }

    public void setUserAuth(boolean userAuth) {
        isUserAuth = userAuth;
    }

    public boolean getIsFirstLevelProvider() {
        return isFirstLevelProvider;
    }

    public void setIsFirstLevelProvider(boolean firstLevelProvider) {
        isFirstLevelProvider = firstLevelProvider;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}

