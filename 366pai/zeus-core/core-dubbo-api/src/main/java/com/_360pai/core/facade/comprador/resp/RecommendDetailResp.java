package com._360pai.core.facade.comprador.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/4 12:47
 */
public class RecommendDetailResp implements Serializable {
    private Integer id;
    private Date    createdTime;
    private String  recommendNo;
    private String  contactPhone;
    private String  contactName;
    private String  contactAddress;
    private String  recommendDescription;
    private String  recommendStatus;
    private String  remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getRecommendNo() {
        return recommendNo;
    }

    public void setRecommendNo(String recommendNo) {
        this.recommendNo = recommendNo;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getRecommendDescription() {
        return recommendDescription;
    }

    public void setRecommendDescription(String recommendDescription) {
        this.recommendDescription = recommendDescription;
    }

    public String getRecommendStatus() {
        return recommendStatus;
    }

    public void setRecommendStatus(String recommendStatus) {
        this.recommendStatus = recommendStatus;
    }
}
