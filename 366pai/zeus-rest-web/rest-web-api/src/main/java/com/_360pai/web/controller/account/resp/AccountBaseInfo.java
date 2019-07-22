package com._360pai.web.controller.account.resp;

import java.util.List;

/**
 * Created by RuQ on 2018/8/19 18:58
 */
public class AccountBaseInfo {

    private Integer accountId;
    private String mobile;
    private String name;
    private Integer defaultAgencyId;
    private String defaultAgencyName;
    private String fadadaId;
    private String fadadaEmail;
    private String dfftId;
    private boolean is_pay_bind;
    private boolean is_channel;
    //party表主键 -1 不存在
    private Integer partyPrimaryId;
    private String type;
    private String accountAuthTime;
    private boolean isAccountAuth;
    private boolean isBank;

    private String idOrLicenceNo;

    private boolean isFunder;
    private boolean isDisposer;

    private Integer agencyId;

    private Integer bankId;
    private String bankCode;
    private String bankName;
    private String bankAccountNo;  //卡号
    private String bankAccountName;//开户名
    private boolean isAdmin;
    private boolean isUserAuth;
    private Integer shopId;

    private String disposerStatus;//处置服务商申请状态
    private String agencyStatus;//联拍机构申请状态
    private String fundStatus;//资金服务商申请状态

    private boolean leaseReleaseFlag;//融资租赁发布权限

    private boolean leaseAuditFlag;//融资租赁审核权限

    private Integer leaseComId;//员工的公司id

    private Integer leaseStaffId;//员工id

    private String leaseComName;//公司名称

    private String leaseComAddress;//租赁公司地址

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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    /**
     * 允许发布线下操作拍品 0 否 1 是
     */
    private java.lang.Boolean operOffline;
    /**
     * 是否允许未开通法大大下发布预招商 0 否 1 是
     */
    private java.lang.Boolean operWithoutFadada;

    private List<AccountSimpleVo> accountList;

    public List<AccountSimpleVo> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<AccountSimpleVo> accountList) {
        this.accountList = accountList;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
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

    public Boolean getOperOffline() {
        if(operOffline==null){
            return false;
        }
        return operOffline;
    }

    public void setOperOffline(Boolean operOffline) {
        this.operOffline = operOffline;
    }

    public Boolean getOperWithoutFadada() {
        if(operWithoutFadada==null){
            return false;
        }
        return operWithoutFadada;
    }

    public void setOperWithoutFadada(Boolean operWithoutFadada) {
        this.operWithoutFadada = operWithoutFadada;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
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

    public String getIdOrLicenceNo() {
        return idOrLicenceNo;
    }

    public void setIdOrLicenceNo(String idOrLicenceNo) {
        this.idOrLicenceNo = idOrLicenceNo;
    }


    // private List<AccountSimpleVo> accountList;


    public String getDefaultAgencyName() {
        return defaultAgencyName;
    }

    public void setDefaultAgencyName(String defaultAgencyName) {
        this.defaultAgencyName = defaultAgencyName;
    }

    public boolean isBank() {
        return isBank;
    }

    public void setBank(boolean bank) {
        isBank = bank;
    }

    public boolean isAccountAuth() {

        return isAccountAuth;
    }

    public void setAccountAuth(boolean accountAuth) {
        isAccountAuth = accountAuth;
    }

    public String getAccountAuthTime() {
        return accountAuthTime;
    }

    public void setAccountAuthTime(String accountAuthTime) {
        this.accountAuthTime = accountAuthTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

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

    public String getFadadaId() {
        return fadadaId;
    }

    public void setFadadaId(String fadadaId) {
        this.fadadaId = fadadaId;
    }

    public String getDfftId() {
        return dfftId;
    }

    public void setDfftId(String dfftId) {
        this.dfftId = dfftId;
    }

    public boolean isIs_pay_bind() {
        return is_pay_bind;
    }

    public void setIs_pay_bind(boolean is_pay_bind) {
        this.is_pay_bind = is_pay_bind;
    }

    public boolean isIs_channel() {
        return is_channel;
    }

    public void setIs_channel(boolean is_channel) {
        this.is_channel = is_channel;
    }

    public Integer getPartyPrimaryId() {
        return partyPrimaryId;
    }

    public void setPartyPrimaryId(Integer partyPrimaryId) {
        this.partyPrimaryId = partyPrimaryId;
    }

    public String getFadadaEmail() {
        return fadadaEmail;
    }

    public void setFadadaEmail(String fadadaEmail) {
        this.fadadaEmail = fadadaEmail;
    }

    public boolean getIsUserAuth() {
        return isUserAuth;
    }

    public void setIsUserAuth(boolean isUserAuth) {
        this.isUserAuth = isUserAuth;
    }
}
