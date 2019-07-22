package com._360pai.core.facade.comprador.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/5 14:28
 */
public class MyRequirementRecommenderResp implements Serializable {
    private Integer             id;
    private Date                createdTime;
    private String              recommenderNo;
    private String              recommenderStatus;
    private CompradorDetailResp requirestDetail;

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

    public String getRecommenderStatus() {
        return recommenderStatus;
    }

    public void setRecommenderStatus(String recommenderStatus) {
        this.recommenderStatus = recommenderStatus;
    }

    public CompradorDetailResp getRequirestDetail() {
        return requirestDetail;
    }

    public void setRequirestDetail(CompradorDetailResp requirestDetail) {
        this.requirestDetail = requirestDetail;
    }
}

