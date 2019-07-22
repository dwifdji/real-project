package com._360pai.gateway.controller.req.fdd;


import com._360pai.arch.common.enums.ApiCallResult;

/**
 * 描述：法大大会员开户请求参数
 *
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 12:55
 */
public class FOpenMemberResp extends FCommResp {


    private String customer_id;//法大大的开户编号


    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }


    public static FOpenMemberResp fail(String msg){

        FOpenMemberResp resp = new FOpenMemberResp();
        resp.setCode(ApiCallResult.FAILURE.getCode());
        resp.setDesc(msg);

        return resp;
    }
}
