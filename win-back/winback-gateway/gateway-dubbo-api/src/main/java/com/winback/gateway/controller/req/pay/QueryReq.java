package com.winback.gateway.controller.req.pay;

import com.winback.gateway.common.constants.PayEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * 统一查询请求参数
 *
 */
@Data
public class QueryReq implements Serializable {


    private String type; //支付的类型


    private String businessType; //业务支付类型



    private String orderId; //支付订单id


}
