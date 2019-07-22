package com._360pai.core.facade.comprador.req;

import com._360pai.arch.common.RequestModel;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/3 20:24
 */
public class RequirementRecommenderQueryReq extends RequestModel {

    private Integer requirementId;

    private String remarked;

    private String createdTime;
    /**
     * 联系人姓名或手机号
     */
    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(Integer requirementId) {
        this.requirementId = requirementId;
    }

    public String getRemarked() {
        return remarked;
    }

    public void setRemarked(String remarked) {
        this.remarked = remarked;
    }
}
