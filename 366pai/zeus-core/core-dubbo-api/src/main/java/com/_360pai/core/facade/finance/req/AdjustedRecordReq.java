package com._360pai.core.facade.finance.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * @author xiaolei
 * @create 2018-10-03 14:58
 */
public class AdjustedRecordReq {

    @Data
    public static class GetAdjustedRecord extends RequestModel {
        private Integer partyId;
        private int adjustedStatus;
        private String withdrawNo;
    }


}
