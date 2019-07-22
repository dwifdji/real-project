package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xdrodger
 * @Title: PartyReq
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/11/26 09:52
 */
public class PartyReq extends RequestModel {
    @Data
    public static class OperateOfflineReq extends RequestModel {
        private Integer partyId;
        /**
         * 允许发布线下操作拍品 0 否 1 是
         */
        private java.lang.Boolean operOffline;
    }
}
