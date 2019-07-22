package com.winback.core.facade.contract.req;

import com.winback.arch.common.AdminReq;
import com.winback.arch.common.AppReq;
import lombok.Data;

/**
 * @author xdrodger
 * @Title: AppContractOrderReq
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 13:11
 */
public class AdminContractOrderReq {

    @Data
    public static class QueryReq extends AdminReq {
        private Integer orderId;
        /**
         * 期限（近三月：3，近一年：12，全部：0）
         */

        private String q;
        private String mobile;

        private String period;
        private String orderSource;
        private String payType;
        private String payStatus;
    }

}
