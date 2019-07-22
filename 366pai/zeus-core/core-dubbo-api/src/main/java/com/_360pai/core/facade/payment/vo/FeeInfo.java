package com._360pai.core.facade.payment.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: FeeInfo
 * @ProjectName zeus
 * @Description:
 * @date 07/09/2018 14:18
 */
public class FeeInfo implements Serializable {
    private BigDecimal amount;

    private String side;

    private String type;

    private Boolean status;

    public FeeInfo() {

    }

    public FeeInfo(String type, BigDecimal amount, String side, Boolean status) {
        this.type = type;
        this.amount = amount;
        this.side = side;
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
