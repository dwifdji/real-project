package com.tzCloud.core.facade.account.req;

import com.tzCloud.arch.common.PlatformReq;
import lombok.Data;

import java.util.List;

/**
 * @author xdrodger
 * @Title: LoginReq
 * @ProjectName winback
 * @Description:
 * @date 2019/1/24 16:03
 */
public class PlatformAccountReq {

    @Data
    public static class LoginReq extends PlatformReq {
        private String mobile;
        private String password;
        private String smsCode;
    }

    @Data
    public static class RegisterReq extends PlatformReq {
        private String mobile;
        private String password;
        private String smsCode;
        private String inviteCode;
        private String headImgUrl;
        private String nickName;
        /**
         * 注册来源
         */
        private String registerSource;
    }

    @Data
    public static class SmsReq extends PlatformReq {
        private String mobile;
        //验证码类型
        private String smsType;
        // 图形验证码
        private String captcha;
    }

    @Data
    public static class CheckMobileReq extends PlatformReq {
        private String mobile;
    }

    @Data
    public static class ForgetPasswordReq extends PlatformReq {
        private String mobile;
        private String password;
        private String smsCode;
    }

    @Data
    public static class ModifyPasswordReq extends PlatformReq {
        private Integer id;
        private String password;
    }

    @Data
    public static class EditProfileReq extends PlatformReq {
        private Integer id;
        /**
         * 昵称
         */
        private String nickName;
        /**
         * 头像
         */
        private String headImgUrl;
        /**
         * 姓名
         */
        private java.lang.String name;
        /**
         * 性别（U 未知 F 女 M 男）
         */
        private java.lang.String gender;
        /**
         * 公司
         */
        private java.lang.String company;
        /**
         * 行业
         */
        private java.lang.String industry;
        /**
         * 地址
         */
        private java.lang.String address;
        /**
         * 职务
         */
        private java.lang.String title;
    }
}
