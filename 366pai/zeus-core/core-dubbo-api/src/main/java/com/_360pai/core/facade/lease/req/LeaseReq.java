package com._360pai.core.facade.lease.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 租赁权拍卖请求参数
 */
public class LeaseReq {

    @Getter
    @Setter
    public static class LeaseStaff extends RequestModel {
        private String id;
        private String comId;
        private String name;
        private String mobile;
        private boolean agentFlag;
        private boolean trialFlag;
        private boolean finalFlag;
        private boolean forbidFlag;
    }


    @Getter
    @Setter
    public static class LeaseStaffInfo extends RequestModel {
        private String id;
        private String comId;
        private String auditNum;

    }



    @Getter
    @Setter
    public static class leaseAsset extends RequestModel {
        private String name;

        private String activityId;

        private String assetId;

        private String type;

        private String beginTime;

        private String endTime;

        private String reason;

        private Integer  id;

        private Integer  partyId;

        private Integer  accountId;

        private String  proveUrl;

        private String  subStatus;

        private String  partyIds;

        private List<String> partyIdList;

        private List<String> subStatusList;



    }


}
