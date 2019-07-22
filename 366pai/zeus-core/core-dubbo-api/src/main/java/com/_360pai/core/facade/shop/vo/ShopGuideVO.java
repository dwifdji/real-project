package com._360pai.core.facade.shop.vo;

import com._360pai.core.common.constants.ShopEnum;

import java.io.Serializable;

public class ShopGuideVO implements Serializable {

    private String status;

    private String statusDesc;

    private String type;

    private String typeDesc;

    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        this.typeDesc = ShopEnum.ShopGuideType.getTypeDesc(type);
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setStatus(String status) {
        this.status = status;
        this.statusDesc = ShopEnum.ShopGuideStatus.getStatusDesc(status);
    }
}
