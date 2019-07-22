package com.tzCloud.core.facade.order.req;

import com.tzCloud.arch.common.RequestModel;
import lombok.Data;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/13 11:05
 */
public class ServiceOrderReq {

    @Data
    public static class MembershipOpenReq extends RequestModel {
        private Integer accountId;
        private String membershipFeeType;
    }

    @Data
    public static class QueryStatus extends RequestModel {
        private Integer serviceOrderId;
    }
}
