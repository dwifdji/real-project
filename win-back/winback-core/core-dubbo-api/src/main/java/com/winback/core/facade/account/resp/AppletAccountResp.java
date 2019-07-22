package com.winback.core.facade.account.resp;

import com.winback.arch.common.RespModel;
import lombok.Data;

/**
 * @author xdrodger
 * @Title: AppletAccountResp
 * @ProjectName winback
 * @Description:
 * @date 2019/2/12 09:34
 */
public class AppletAccountResp {

    @Data
    public static class AccountResp extends RespModel {
        private Integer loginId;
        private String mobile;
    }

    @Data
    public static class LoginResp extends RespModel {
        private Integer loginId;
        private String openId;
    }
}
