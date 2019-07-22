package com._360pai.core.facade.asset.req;

import com._360pai.arch.common.RequestModel;

/**
 * @author zxiao
 * @Title: AssetCategoryGroups
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/16 9:45
 */
public class AssetCategoryGroupReq extends RequestModel {

    public static final String SELL = "SELL"; //销售类
    public static final String SERVICE = "SERVICE"; //服务类

    public static final String AUCTION = "AUCTION"; //拍卖
    public static final String ENROLLING = "ENROLLING"; //预招商

    private Integer businessType;

    private Integer dealMode;

    private Boolean enabled;

    private String name;

    private Integer groupId;

    private Integer id;

    private Integer currentCategoryId;

    private Integer resultId;

    private Integer[] fieldIds;

    public Integer getCurrentCategoryId() {
        return currentCategoryId;
    }

    public void setCurrentCategoryId(Integer currentCategoryId) {
        this.currentCategoryId = currentCategoryId;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public Integer[] getFieldIds() {
        return fieldIds;
    }

    public void setFieldIds(Integer[] fieldIds) {
        this.fieldIds = fieldIds;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getDealMode() {
        return dealMode;
    }

    public void setDealMode(Integer dealMode) {
        this.dealMode = dealMode;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
