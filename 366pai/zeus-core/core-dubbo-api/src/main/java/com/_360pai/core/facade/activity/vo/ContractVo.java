package com._360pai.core.facade.activity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author RuQ
 * @Title: ContractVo
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/12 9:35
 */
public class ContractVo implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Long orderId;
    /**
     *
     */
    private Boolean allSigned;
    /**
     *
     */
    private Boolean buyerSigned;
    /**
     *
     */
    private Boolean sellerSigned;
    /**
     *
     */
    private String downloadUrl;
    /**
     *
     */
    private Integer templateId;
    /**
     *
     */
    private String viewpdfUrl;
    /**
     *
     */
    private String contractId;
    /**
     *
     */
    private Date createdAt;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Boolean getAllSigned() {
        return allSigned;
    }

    public void setAllSigned(Boolean allSigned) {
        this.allSigned = allSigned;
    }

    public Boolean getBuyerSigned() {
        return buyerSigned;
    }

    public void setBuyerSigned(Boolean buyerSigned) {
        this.buyerSigned = buyerSigned;
    }

    public Boolean getSellerSigned() {
        return sellerSigned;
    }

    public void setSellerSigned(Boolean sellerSigned) {
        this.sellerSigned = sellerSigned;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getViewpdfUrl() {
        return viewpdfUrl;
    }

    public void setViewpdfUrl(String viewpdfUrl) {
        this.viewpdfUrl = viewpdfUrl;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
