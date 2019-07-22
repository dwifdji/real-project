package com.winback.core.facade.contract.req;

import com.winback.arch.common.AppReq;
import lombok.Data;

/**
 * @author xdrodger
 * @Title: AppContractReq
 * @ProjectName winback
 * @Description:
 * @date 2019/1/31 14:42
 */
public class AppShoppingCartReq {
    @Data
    public static class QueryReq extends AppReq {

    }

    @Data
    public static class AddItemReq extends AppReq {
        private Integer contractId;
        private Integer num = 1;
    }

    @Data
    public static class DeleteItemReq extends AppReq {
        private Integer itemId;
    }
}
