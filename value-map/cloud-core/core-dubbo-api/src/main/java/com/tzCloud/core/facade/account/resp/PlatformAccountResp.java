package com.tzCloud.core.facade.account.resp;

import com.tzCloud.arch.common.RespModel;
import lombok.Data;

/**
 * @author xdrodger
 * @Title: LoginResp
 * @ProjectName winback
 * @Description:
 * @date 2019/1/24 16:07
 */
public class PlatformAccountResp {

    @Data
    public static class AccountResp extends RespModel {
        private Integer loginId;
        private String mobile;
        private boolean resetPassword;
    }

    @Data
    public static class ApplyResp extends RespModel {
        private Integer applyId;
    }
}
