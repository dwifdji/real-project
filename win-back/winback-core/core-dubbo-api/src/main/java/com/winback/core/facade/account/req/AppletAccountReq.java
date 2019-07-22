package com.winback.core.facade.account.req;

import com.winback.arch.common.AppReq;
import lombok.Data;

/**
 * @author xdrodger
 * @Title: AppletAccountReq
 * @ProjectName winback
 * @Description:
 * @date 2019/2/12 09:34
 */
public class AppletAccountReq {

    @Data
    public static class LoginReq extends AppReq {
        private String code;
        private String nickName;
        private String headImgUrl;
    }

    @Data
    public static class SmsReq extends AppReq {
        private String mobile;
        //验证码类型
        private String smsType;
        // 图形验证码
        private String captcha;
    }

    @Data
    public static class BindAccountReq extends AppReq {
        private String mobile;
        private String smsCode;
    }

    @Data
    public static class MessageReq extends AppReq {
        private java.lang.Integer id;
    }
}
