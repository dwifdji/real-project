package com.tzCloud.gateway.controller.req.pay;

import com.tzCloud.gateway.common.constants.PayEnum;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 统一查询请求参数
 *
 */
@Data
public class QueryReq implements Serializable {


    private PayEnum.PAY_TYPE type; //支付的类型


    private PayEnum.BUSINESS_TYPE businessType; //业务支付类型



    private String orderId; //支付订单id


}
