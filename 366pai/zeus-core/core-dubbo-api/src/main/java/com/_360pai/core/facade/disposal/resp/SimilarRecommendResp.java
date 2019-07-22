package com._360pai.core.facade.disposal.resp;

import com._360pai.core.facade.disposal.req.City;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2018-09-20 16:04
 */
public class SimilarRecommendResp implements Serializable {
    private static final long serialVersionUID = -2682723249421993875L;

    private Integer disposalId;
    private String disposalName;
    private String cost;
    private String cityId;
    private String extra;
    private City[] providerAreas;
    private Integer viewNum;

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public City[] getProviderAreas() {
        return providerAreas;
    }

    public void setProviderAreas(City[] providerAreas) {
        this.providerAreas = providerAreas;
    }

    public Integer getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Integer disposalId) {
        this.disposalId = disposalId;
    }

    public String getDisposalName() {
        return disposalName;
    }

    public void setDisposalName(String disposalName) {
        this.disposalName = disposalName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
