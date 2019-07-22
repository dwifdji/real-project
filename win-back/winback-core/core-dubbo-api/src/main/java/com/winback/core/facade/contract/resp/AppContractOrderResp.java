package com.winback.core.facade.contract.resp;

import com.winback.arch.common.RespModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: AppContractOrderResp
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 14:13
 */
public class AppContractOrderResp {

    @Data
    public static class PrepayResp extends RespModel {
        private String payOrderId;

        private String prepayId;//预付款订单号

        /*
         * WxAppletPayVo 小程序返回
         * WxAppPayVo app支付返回
         *
         * */
        private Object param;//返回的参数信息
    }

    @Data
    public static class PayStatusResp extends RespModel {
        private Integer payStatus;
    }

    @Data
    public static class OrderBuyResp extends RespModel {
        private Integer orderId;
        private BigDecimal amount;
    }
}
