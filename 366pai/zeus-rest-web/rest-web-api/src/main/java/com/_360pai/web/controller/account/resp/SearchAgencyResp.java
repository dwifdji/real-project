package com._360pai.web.controller.account.resp;

/**
 * @author RuQ
 * @Title: SearchAgencyResp
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/10 16:22
 */
public class SearchAgencyResp {

    private String agencyId;
    private String agencyName;
    private String logoUrl;

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
