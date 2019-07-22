package com._360pai.gateway.controller.req.dfftpay;

import java.io.Serializable;

/**
 * 描述：批量返回信息
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 13:14
 */
public class BatchPayInfoVo implements Serializable {



    private String busId;//请求的业务参数

    private String payOrder;//订单id

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(String payOrder) {
        this.payOrder = payOrder;
    }
}
