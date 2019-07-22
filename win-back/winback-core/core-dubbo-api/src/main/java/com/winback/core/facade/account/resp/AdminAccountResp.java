package com.winback.core.facade.account.resp;

import com.winback.arch.common.RespModel;
import lombok.Data;

/**
 * @author xdrodger
 * @Title: AdminAccountResp
 * @ProjectName winback
 * @Description:
 * @date 2019/1/28 13:57
 */
public class AdminAccountResp {
    @Data
    public static class AccountResp extends RespModel {
        private Integer loginId;
        /**
         * 是否管理员
         */
        private Boolean adminFlag;
    }

}
