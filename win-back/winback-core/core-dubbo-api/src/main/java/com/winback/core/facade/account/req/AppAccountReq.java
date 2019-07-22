package com.winback.core.facade.account.req;

import com.winback.arch.common.AppReq;
import com.winback.core.facade.account.vo.Contacts;
import lombok.Data;

import java.util.List;

/**
 * @author xdrodger
 * @Title: LoginReq
 * @ProjectName winback
 * @Description:
 * @date 2019/1/24 16:03
 */
public class AppAccountReq {

    @Data
    public static class LoginReq extends AppReq {
        private String mobile;
        private String password;
        private String smsCode;
    }

    @Data
    public static class RegisterReq extends AppReq {
        private String mobile;
        private String password;
        private String smsCode;
        private String inviteCode;
        private String headImgUrl;
        private String nickName;
        /**
         * 注册来源
         */
        private java.lang.String registerSource;
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
    public static class CheckMobileReq extends AppReq {
        private String mobile;
    }

    @Data
    public static class ForgetPasswordReq extends AppReq {
        private String mobile;
        private String password;
        private String smsCode;
    }

    @Data
    public static class ModifyPasswordReq extends AppReq {
        private Integer id;
        private String password;
    }

    @Data
    public static class LawyerApplyReq extends AppReq {
        /**
         * 姓名
         */
        private java.lang.String name;
        /**
         * 律师证
         */
        private java.lang.String lawyerLicenseImg;
        /**
         * 律师执业证号
         */
        private java.lang.String lawyerLicenseNumber;
        /**
         * 所属律所
         */
        private java.lang.String lawFirm;
        /**
         * 从业年限
         */
        private java.lang.String workYear;
        /**
         * 业务区域省code
         */
        private java.lang.String businessProvinceCode;
        /**
         * 业务区域市code
         */
        private java.lang.String businessCityCode;
        /**
         * 业务区区县code
         */
        private java.lang.String businessAreaCode;
        /**
         * 头像
         */
        private java.lang.String headImgUrl;
    }

    @Data
    public static class FranchiseeApplyReq extends AppReq {
        /**
         * 账户id
         */
        private java.lang.Integer accountId;
        /**
         * 姓名
         */
        private java.lang.String name;
        /**
         * 加盟商类型(user：个人，company：企业)
         */
        private java.lang.String type;
        /**
         * 身份证号码
         */
        private java.lang.String certificateNumber;
        /**
         * 身份证正面照
         */
        private java.lang.String certificateFrontImg;
        /**
         * 身份证背面照
         */
        private java.lang.String certificateBackImg;
        /**
         * 营业执照图片
         */
        private java.lang.String licenseImg;
        /**
         * 营业执照号
         */
        private java.lang.String licenseNumber;
        /**
         * 自我介绍
         */
        private java.lang.String selfIntroduction;
    }

    @Data
    public static class AccountUpdateReq extends AppReq {
        /**
         * 昵称
         */
        private java.lang.String nickName;
        /**
         * 头像
         */
        private java.lang.String headImgUrl;

    }

    @Data
    public static class FranchiseeQueryReq extends AppReq {
        /**
         * 加盟商id
         */
        private java.lang.Integer franchiseeId;
    }

    @Data
    public static class MessageReq extends AppReq {
        private java.lang.Integer id;
    }

    @Data
    public static class UploadContactsReq extends AppReq {
        private List<Contacts> list;
    }
}
