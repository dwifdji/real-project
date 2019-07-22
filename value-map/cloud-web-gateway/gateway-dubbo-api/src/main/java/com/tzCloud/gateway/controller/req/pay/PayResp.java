package com.tzCloud.gateway.controller.req.pay;


import com.tzCloud.gateway.common.constants.PayResultEnums;
import lombok.Data;

import java.io.Serializable;

/**
 *统一支付返回参数
 */
@Data
public class PayResp implements Serializable {

    private String code;

    private String desc;

    private String orderId;//支付的订单号

    private String prepayId;//预付款订单号

    /*
     * WxAppletPayVo 小程序返回
     * WxAppPayVo app支付返回
     *
     * */
    private Object param;//返回的参数信息



    public static PayResp fail(PayResultEnums m) {
        PayResp ret = new PayResp();
        ret.setCode(m.getCode());
        ret.setDesc(m.getDesc());
         return ret;
    }


}
