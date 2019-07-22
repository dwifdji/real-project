package com._360pai.web.controller.account.resp;

import com._360pai.core.facade.assistant.vo.CityVo;

/**
 * Created by RuQ on 2018/8/24 10:59
 */
public class AccountSimpleVo {
    private String type;
    private String name;
    private String partyId;
    private boolean isDefault;
    private String disposerStatus;//处置服务商申请状态
    private String agencyStatus;
    private String fundStatus;

    private CityVo cityVo;
    private String address;

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


    public CityVo getCityVo() {
        return cityVo;
    }

    public void setCityVo(CityVo cityVo) {
        this.cityVo = cityVo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDisposerStatus() {
        return disposerStatus;
    }

    public void setDisposerStatus(String disposerStatus) {
        this.disposerStatus = disposerStatus;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
