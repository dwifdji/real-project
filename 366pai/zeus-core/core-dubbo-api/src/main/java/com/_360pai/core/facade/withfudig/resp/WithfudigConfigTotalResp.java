package com._360pai.core.facade.withfudig.resp;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/8/30 14:10
 */
public class WithfudigConfigTotalResp implements Serializable {

    private BigDecimal totalNum    = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    public BigDecimal getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(BigDecimal totalNum) {
        this.totalNum = totalNum;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
