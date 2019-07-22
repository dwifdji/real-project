package com._360pai.core.facade.account.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: PartyBlackListActionVo
 * @ProjectName zeus
 * @Description:
 * @date 12/09/2018 19:00
 */
public class PartyBlackListActionVo implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String action;
    /**
     *
     */
    private Integer partyId;
    /**
     *
     */
    private String reason;
    /**
     *
     */
    private java.util.Date createdAt;
    /**
     *
     */
    private StaffVo operator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public StaffVo getOperator() {
        return operator;
    }

    public void setOperator(StaffVo operator) {
        this.operator = operator;
    }
}
