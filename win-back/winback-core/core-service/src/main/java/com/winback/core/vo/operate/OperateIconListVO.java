package com.winback.core.vo.operate;

import com.winback.core.commons.constants.OperateEnum;

import java.io.Serializable;

public class OperateIconListVO implements Serializable {

    private String id;

    private String type;

    private String groupType;

    private String groupTypeDesc;

    private String imgUrl;

    private String name;

    private Integer sort;

    private String updateTime;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getGroupTypeDesc() {
        return groupTypeDesc;
    }



    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
        this.groupTypeDesc = OperateEnum.OperateIconEnum.getDescByType(groupType);
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
