package com.tzCloud.core.facade.order.resp;

import java.io.Serializable;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/14 15:50
 */
public class ServiceOrderStatusResp implements Serializable {
    private String payStatus;

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
}
