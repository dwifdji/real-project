package com._360pai.web.controller.account.req;

/**
 * @author RuQ
 * @Title: SearchAgencyReq
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/10 16:26
 */
public class SearchAgencyReq {
    private String cityId;
    private String q;
    private Integer agencyId;

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
