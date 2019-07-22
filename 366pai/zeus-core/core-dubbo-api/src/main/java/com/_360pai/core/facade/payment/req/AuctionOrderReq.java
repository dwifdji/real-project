package com._360pai.core.facade.payment.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/16 13:08
 */
public class AuctionOrderReq {

    @Getter
    @Setter
    public static class OrderIdReq extends RequestModel {
        private Long orderId;
        private Integer partyId;
        private Integer sellerId;
        private Integer buyerId;

        private String name;

        private String categoryId;

    }

    @Getter
    @Setter
    public static class QueryReq extends RequestModel {
        private String q;
        private String sellerAgencyName;
        private String status;
        private String finishAtFrom;
        private String finishAtTo;
        private String amountFrom;
        private String amountTo;
        private Integer agencyId;
        private Integer sellerAgencyId;
        private Integer buyerAgencyId;
        private Integer sellerId;
        private Integer buyerId;
        /**
         * 上拍来源：0 平台 1 机构
         */
        private java.lang.String comeFrom = "0";
        private String onlined;
        private String orderId;
        private String inviteCode;

        /**
         * 资产类型 1 债权 2 物权 3 租赁
         */
        private String type;

        private String name;

        //企业员工id
        private Integer leaseStaffId;


        private String categoryId;

    }
}
