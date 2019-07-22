package com.winback.core.facade.contract.req;

import com.winback.arch.common.AppReq;
import lombok.Data;
import sun.dc.pr.PRError;

/**
 * @author xdrodger
 * @Title: AppContractOrderReq
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 13:11
 */
public class AppletContractOrderReq {

    @Data
    public static class QueryReq extends AppReq {
        private Integer orderId;
        /**
         * 订单状态（0 未支付 1 已支付）
         */
        private String payStatus;
    }

    @Data
    public static class BuyNowReq extends AppReq {
        private Integer contractId;
    }

    @Data
    public static class ShoppingCartBuyReq extends AppReq {
    }

    @Data
    public static class OrderBuyReq extends AppReq {
        private Integer orderId;
    }

    @Data
    public static class PrepayReq extends AppReq {
        /**
         * 订单id
         */
        private Integer orderId;
        /**
         * 支付类型（1 支付宝 2 微信）
         */
        private String payType = "2";
    }

    @Data
    public static class PayCallBackReq extends AppReq {
        /**
         * 订单编号
         */
        private Integer orderId;
        /**
         * 支付订单id
         */
        private String payOrderId;
    }
}
