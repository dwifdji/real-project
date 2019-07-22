package com._360pai.core.facade.withfudig.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/10 12:48
 */
public class MyRequirementInvestResp implements Serializable {
    private Integer                        id;
    private Date                           createdTime;
    private String                         investNo;
    private String                         investStatus;
    private WithfudigRequirementDetailResp requirementDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getInvestNo() {
        return investNo;
    }

    public void setInvestNo(String investNo) {
        this.investNo = investNo;
    }

    public String getInvestStatus() {
        return investStatus;
    }

    public void setInvestStatus(String investStatus) {
        this.investStatus = investStatus;
    }

    public WithfudigRequirementDetailResp getRequirementDetail() {
        return requirementDetail;
    }

    public void setRequirementDetail(WithfudigRequirementDetailResp requirementDetail) {
        this.requirementDetail = requirementDetail;
    }
}
