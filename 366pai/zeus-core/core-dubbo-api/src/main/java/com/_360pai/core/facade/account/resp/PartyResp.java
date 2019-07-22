package com._360pai.core.facade.account.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by RuQ on 2018/8/21 9:10
 */
public class PartyResp implements Serializable {

    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * user或company Id
     */
    private java.lang.Integer partyId;
    /**
     * user or company
     */
    private java.lang.String type;
    /**
     * 1:正常人，2:银行,3:AMC 资产管理公司,4:民营资管，5:普通公司,6:拍卖公司
     */
    private java.lang.String category;
    /**
     * 创建时间
     */
    private java.util.Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
