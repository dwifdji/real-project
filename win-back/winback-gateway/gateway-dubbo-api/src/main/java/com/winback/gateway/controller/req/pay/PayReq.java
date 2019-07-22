package com.winback.gateway.controller.req.pay;

import com.winback.gateway.common.constants.PayEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * 统一支付请求参数
 *
 */
@Data
public class PayReq implements Serializable {


    private PayEnum.PAY_TYPE type; //支付的类型


    private PayEnum.BUSINESS_TYPE businessType; //业务支付类型


    private String businessId; //支付的业务ID


    private String openId; //微信小程序支付时必填


    private BigDecimal amount; //支付的金额


    private Integer accountId; //支付账号id


    private String orderId; //支付订单id


    private String orderDesc;//订单描述


}
