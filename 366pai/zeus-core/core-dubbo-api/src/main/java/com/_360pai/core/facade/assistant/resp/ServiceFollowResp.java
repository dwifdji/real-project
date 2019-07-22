package com._360pai.core.facade.assistant.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/17 13:06
 */
public class ServiceFollowResp implements Serializable {
    private Integer id;
    private Date    createdTime;
    private String  contactName;
    private String  contactPhone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
