package com._360pai.core.vo.enrolling;

import java.io.Serializable;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 17:00
 */
public class EnrollingAuditVo implements Serializable {

    private String name;//报名人名称

    private String amount;//证件号码

    private String code;//手机号码

    private String participateProof;//连拍机构

    private String mobile;//报名时间

    private String partyName;//保证金状态

    private String createdAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParticipateProof() {
        return participateProof;
    }

    public void setParticipateProof(String participateProof) {
        this.participateProof = participateProof;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
}
