package com.winback.gateway.service.pay;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;

/**
 * 描述：支付宝服务类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/16 9:59
 */
public interface AliPayService {

    /**
     * 支付宝App支付
     *
     */
    AlipayTradeAppPayResponse aliPayTradeAppPay(AlipayTradeAppPayModel model);


    /**
     * 支付宝订单查询接口
     *
     */


    AlipayTradeQueryResponse aliPayQuery(AlipayTradeQueryModel model);
}
