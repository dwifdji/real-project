package com._360pai.core.vo.enrolling;

import java.io.Serializable;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 17:00
 */
public class EnrollingAuditListVo implements Serializable {

    private String useName;//报名人名称

    private String useMobile;//证件号码

    private String comMobile;//手机号码

    private String comName;//连拍机构

    private String createdAt;//报名时间

    private String name;//保证金状态

    private String code;//连拍机构

    private String participateProof;//报名时间

    private String amount;//保证金状态

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public String getUseMobile() {
        return useMobile;
    }

    public void setUseMobile(String useMobile) {
        this.useMobile = useMobile;
    }

    public String getComMobile() {
        return comMobile;
    }

    public void setComMobile(String comMobile) {
        this.comMobile = comMobile;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
