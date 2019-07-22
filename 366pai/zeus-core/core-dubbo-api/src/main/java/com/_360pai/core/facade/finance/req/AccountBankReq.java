package com._360pai.core.facade.finance.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xiaolei
 * @create 2018-10-06 23:51
 */
public class AccountBankReq {

    @Getter
    @Setter
    public static class AddAccountBank extends RequestModel {
        private Integer accountId;
        private Integer partyId;
        private String bankNo;
        private String bankName;
        private String userName;
        private String bankAddress;
    }

}
