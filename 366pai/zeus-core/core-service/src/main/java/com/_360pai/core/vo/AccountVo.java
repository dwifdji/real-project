package com._360pai.core.vo;

import java.io.Serializable;
import java.util.Date;

public class AccountVo implements Serializable {

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String mobile;
    /**
     *
     */
    private Boolean isValid;
    /**
     *
     */
    private Date createdAt;
    /**
     *
     */
    private Date updatedAt;
    /**
     *
     */
    private String registerSource;
    /**
     *
     */
    private Integer defaultAgencyId;
    /**
     *
     */
    private Integer agencyId;
    /**
     *
     */
    private String passwordHash;
    /**
     *
     */
    private Integer currentPartyId;
    /**
     *
     */
    private Boolean agencyBind;
    /**
     *
     */
    private Boolean isAgencyAdmin;

    /**
     *
     */
    private String smsCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(String registerSource) {
        this.registerSource = registerSource;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Integer getCurrentPartyId() {
        return currentPartyId;
    }

    public void setCurrentPartyId(Integer currentPartyId) {
        this.currentPartyId = currentPartyId;
    }

    public Boolean getAgencyBind() {
        return agencyBind;
    }

    public void setAgencyBind(Boolean agencyBind) {
        this.agencyBind = agencyBind;
    }

    public Boolean getAgencyAdmin() {
        return isAgencyAdmin;
    }

    public void setAgencyAdmin(Boolean agencyAdmin) {
        isAgencyAdmin = agencyAdmin;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
