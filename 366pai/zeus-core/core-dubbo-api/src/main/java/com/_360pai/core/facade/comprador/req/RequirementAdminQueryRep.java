package com._360pai.core.facade.comprador.req;

import com._360pai.arch.common.RequestModel;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/3 15:36
 */
public class RequirementAdminQueryRep extends RequestModel {

    private String buildingType;
    private String requirementStatus;
    private String requirementName;

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getRequirementStatus() {
        return requirementStatus;
    }

    public void setRequirementStatus(String requirementStatus) {
        this.requirementStatus = requirementStatus;
    }
}
