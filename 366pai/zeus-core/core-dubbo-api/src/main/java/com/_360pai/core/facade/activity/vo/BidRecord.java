package com._360pai.core.facade.activity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: BidRecord
 * @ProjectName zeus
 * @Description:
 * @date 06/09/2018 18:14
 */
@Data
public class BidRecord implements Serializable {
    private Integer id;
    private Date createdAt;
    private String amount;
    private String agencyName;
    private String bidderName;
    private String status;
    private String code;
    private String meFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
