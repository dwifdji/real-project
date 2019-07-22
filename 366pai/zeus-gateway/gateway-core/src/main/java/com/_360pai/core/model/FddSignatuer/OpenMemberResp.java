package com._360pai.core.model.FddSignatuer;

import com._360pai.arch.common.ResponseModel;

/**
 * 描述：法大大会员开户请求参数
 *
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 12:55
 */
public class OpenMemberResp extends ResponseModel {


    private String customer_id;//法大大的开户编号

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}
