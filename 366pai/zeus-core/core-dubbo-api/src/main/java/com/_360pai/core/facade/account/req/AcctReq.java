package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xdrodger
 * @Title: AcctReq
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/3 17:14
 */
public class AcctReq {

    @Getter
    @Setter
    public static class BaseReq extends RequestModel {
        private String id;
        private Integer partyId;
        private String partyType;
        private Integer acctId;
    }

    @Getter
    @Setter
    public static class QueryReq extends RequestModel {
        private String createdAtFrom;
        private String createdAtTo;
        private String q;
        private String mobile;
        private String inviteCode;
        private Integer partyId;
        private String partyType;
        private String type;
        private String status;
        private String orderId;
        private Integer acctId;
        private String invoiceType;
        private String batchId;
        private String bankAccountNo;
    }

    @Setter
    @Getter
    public static class ViewRecordRequest extends RequestModel{

        private Integer accountId;

        private String type;

        private Integer partyId;

    }
}
