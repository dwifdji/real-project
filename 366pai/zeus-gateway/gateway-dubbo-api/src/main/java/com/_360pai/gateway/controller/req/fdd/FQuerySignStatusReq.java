package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;

/**
 * 描述：用户签章状态查询
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 13:24
 */
public class FQuerySignStatusReq implements Serializable {

    private String contract_id;//合同id

    private String customer_id;//客户编号

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}
