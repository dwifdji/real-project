package com._360pai.core.facade.account.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: PartyVo
 * @ProjectName zeus
 * @Description:
 * @date 05/09/2018 16:18
 */
public class PartyAccount implements Serializable {

    private java.lang.Integer id;

    private String type;

    private java.lang.String name;

    private String certificateNumber;

    private String mobile;

    private String contact;

    private String qq;

    private Date recordAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Date getRecordAt() {
        return recordAt;
    }

    public void setRecordAt(Date recordAt) {
        this.recordAt = recordAt;
    }
}
