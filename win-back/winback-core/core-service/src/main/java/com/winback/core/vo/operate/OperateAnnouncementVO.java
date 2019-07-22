package com.winback.core.vo.operate;

import com.winback.core.commons.constants.OperateEnum;

import java.io.Serializable;

public class OperateAnnouncementVO implements Serializable {

    private String id;

    private String name;

    private String type;

    private String typeDesc;

    private String createTime;

    private String beginTime;

    private String endTime;

    public String getTypeDesc() {
        return typeDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        this.typeDesc = OperateEnum.AnnouncementType.getDescByType(type);
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
