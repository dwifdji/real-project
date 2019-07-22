package com._360pai.core.facade.comprador.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/4 09:19
 */
public class RequirementRecommenderDetailResp implements Serializable {

    private Integer id;
    private Date    createdTime;
    private String  recommenderNo;
    private String  contactPhone;
    private String  contactName;
    private String  remark;
    private String  recommenderStatus;

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

    public String getRecommenderNo() {
        return recommenderNo;
    }

    public void setRecommenderNo(String recommenderNo) {
        this.recommenderNo = recommenderNo;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRecommenderStatus() {
        return recommenderStatus;
    }

    public void setRecommenderStatus(String recommenderStatus) {
        this.recommenderStatus = recommenderStatus;
    }
}
