package com._360pai.core.facade.asset.req;

import com._360pai.arch.common.RequestModel;

/**
 * @author zxiao
 * @Title: AssetCategoryReq
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/16 13:59
 */
public class AssetCategoryReq extends RequestModel {
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private Integer groupId;
    /**
     *
     */
    private Boolean isGroupDefault;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Boolean getIsGroupDefault() {
        return isGroupDefault;
    }

    public void setIsGroupDefault(Boolean groupDefault) {
        isGroupDefault = groupDefault;
    }
}
