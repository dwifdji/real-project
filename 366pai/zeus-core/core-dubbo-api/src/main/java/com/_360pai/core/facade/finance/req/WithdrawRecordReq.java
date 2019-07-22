package com._360pai.core.facade.finance.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-10-07 20:23
 */
public class WithdrawRecordReq {

    @Getter
    @Setter
    public static class AddWithdrawRecord extends RequestModel {
        private Integer   accountId;
        private Integer   partyId;
        private Integer   bankId;
        private Integer[] adjustedId;
        private Integer   withdrawId;
        private String    accountType;
        private String    accountName;
        private String    bankNo;
    }

    @Getter
    @Setter
    public static class GetWithdrawRecord extends RequestModel {
        private String  withdrawNo;
        private String  verifyStatus;
        private Date    beginDate;
        private Date    endDate;
        private String  timeBy;
        private Integer accountId;
        private Integer partyId;
    }

    @Getter
    @Setter
    public static class GetAdminWithdrawRecord extends RequestModel {
        private String verifyStatus;
        private Date   beginDate;
        private Date   endDate;
        private String timeBy;
        private String accountType;
        private String searchStr;
    }
}
