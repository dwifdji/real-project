package com.winback.core.vo.operate;

import com.winback.core.commons.constants.OperateEnum;

import java.io.Serializable;

public class QuickReleaseVO implements Serializable {

    private String id;

    private String caseTypeId;

    private String groupType;

    private String groupTypeDesc;

    private String imgUrl;

    private String name;

    private Integer sort;

    private String updateTime;

    private String type;

    public String getType() {
        return type;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseTypeId() {
        return caseTypeId;
    }

    public void setCaseTypeId(String caseTypeId) {
        this.caseTypeId = caseTypeId;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupTypeDesc() {
        return groupTypeDesc;
    }

    public void setGroupTypeDesc(String groupTypeDesc) {
        this.groupTypeDesc = groupTypeDesc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.type = OperateEnum.QuickReleaseEnum.getTypeByDesc(name);
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
