package com.winback.core.facade.account.resp;

import com.winback.arch.common.RespModel;
import lombok.Data;

/**
 * @author xdrodger
 * @Title: AdminSysResp
 * @ProjectName winback
 * @Description:
 * @date 2019/1/30 14:10
 */
public class AdminSysResp {

    @Data
    public static class AccountResp extends RespModel {
        private Integer loginId;
    }
}
