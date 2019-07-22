package com._360pai.core.vo.activity;

import com._360pai.core.common.constants.AccountEnum;

import java.io.Serializable;

public class TAccountViewRecordVO implements Serializable {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 账号id
     */
    private Integer accountId;
    /**
     * 活动id
     */
    private Integer activityId;
    /**
     * 活动类型auction拍卖enrolling招商disposal处置
     */
    private String type;
    /**
     * 创建时间
     */
    private String createAt;
    /**
     * 修改时间
     */
    private String updateAt;

    private String typeDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        this.typeDesc = AccountEnum.ViewType.getValueByKey(type);
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getTypeDesc() {
        return typeDesc;
    }


}
