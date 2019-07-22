package com._360pai.partner.controller.account.resp;

/**
 * Created by RuQ on 2018/8/19 18:58
 */
public class AccountBaseInfo {

    private Integer accountId;
    private String mobile;
    private String name;
    private Integer defaultAgencyId;
    private Integer agencyId;
    private String fadadaId;
    private String dfftId;
    private boolean is_pay_bind;
    private boolean is_channel;
    //party表主键 -1 不存在
    private Integer partyPrimaryId;
    private String type;
    private String accountAuthTime;
    private boolean isAccountAuth;
    private boolean isBank;
    private boolean isAgencyAdmin;
    /**
     * 是否可以查看保留价
     */
    /**
     * 是否可以查看保留价
     */
    private java.lang.Boolean canCheckReservePrice;
   // private List<AccountSimpleVo> accountList;


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

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
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

    public boolean getIsAgencyAdmin() {
        return isAgencyAdmin;
    }

    public void setIsAgencyAdmin(boolean isAgencyAdmin) {
        this.isAgencyAdmin = isAgencyAdmin;
    }

    public Boolean getCanCheckReservePrice() {
        return canCheckReservePrice;
    }

    public void setCanCheckReservePrice(Boolean canCheckReservePrice) {
        this.canCheckReservePrice = canCheckReservePrice;
    }
}
