package com._360pai.core.facade.finance.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * @author xiaolei
 * @create 2018-10-06 20:38
 */
public class BusinessRecordReq {

    @Data
    public static class GetBusinessRecord extends RequestModel {
        private Integer partyId;
    }
}
