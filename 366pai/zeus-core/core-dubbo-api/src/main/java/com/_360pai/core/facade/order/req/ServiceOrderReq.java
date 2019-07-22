package com._360pai.core.facade.order.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

import java.util.List;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/13 11:05
 */
public class ServiceOrderReq {
    @Data
    public static class PayReq extends RequestModel {
        private Integer requirementId;
        private Integer accountId;
        private Integer partyId;
    }

    @Data
    public static class QueryStatus extends RequestModel {
        private Integer serviceOrderId;
    }
    @Data
    public static class AdjustedPay extends RequestModel {
        private Integer activityId;
        private Integer accountId;
        private Integer partyId;
        private Integer requirementId;
        private String  reportType;
    }

    @Data
    public static class DisposalRequirementPay extends RequestModel {
        private List<Integer> disposalRequirementList;
        private Integer       accountId;
        private Integer partyId;
    }

    @Data
    public static class ProviderShowPay extends RequestModel {
        private Integer accountId;
        private Integer partyId;
        private Integer activityId;
    }
}
