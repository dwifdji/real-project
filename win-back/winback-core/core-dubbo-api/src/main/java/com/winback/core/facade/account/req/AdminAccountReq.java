package com.winback.core.facade.account.req;

import com.winback.arch.common.AdminReq;
import lombok.Data;

import java.util.List;

/**
 * @author xdrodger
 * @Title: AdminAccountReq
 * @ProjectName winback
 * @Description:
 * @date 2019/1/28 13:54
 */
public class AdminAccountReq {

    @Data
    public static class LoginReq extends AdminReq {
        private String mobile;
        private String password;
        private String smsCode;
    }

    @Data
    public static class SmsReq extends AdminReq {
        private String mobile;
        //验证码类型
        private String smsType;
        // 图形验证码
        private String captcha;
    }

    @Data
    public static class AccountQueryReq extends AdminReq {
        private String q;
        private String mobile;
        private String registerSource;
        private String createdAtFrom;
        private String createdAtTo;
        private Integer id;
        private Integer accountId;
        private Boolean projectManagerFlag;
    }

    @Data
    public static class PartyQueryReq extends AdminReq {
        private String q;
        private String mobile;
        private String registerSource;
        private String createdAtFrom;
        private String createdAtTo;
        private Integer id;
        private Integer accountId;
    }

    @Data
    public static class LawyerQueryReq extends AdminReq {
        private String q;
        private String status;
        private String mobile;
        private String lawFirm;
        private String createdAtFrom;
        private String createdAtTo;
        private Integer id;
    }

    @Data
    public static class LawyerVerifyReq extends AdminReq {
        private Integer id;
        private String reason;
        /**
         * 姓名
         */
        private java.lang.String name;
        /**
         * 邮箱
         */
        private java.lang.String email;
        /**
         * 头像
         */
        private java.lang.String headImgUrl;
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
         * 身份证起始日期
         */
        private java.util.Date certificateBegin;
        /**
         * 身份证结束日期
         */
        private java.util.Date certificateEnd;
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
         * 收款银行
         */
        private java.lang.String bankName;
        /**
         * 收款银行账户
         */
        private java.lang.String bankAccountNumber;
        /**
         * 擅长的案由id列表
         */
        private List<Integer> caseBriefIdList;
    }

    @Data
    public static class LawyerUpdateReq extends AdminReq {
        private Integer id;
        /**
         * 邮箱
         */
        private java.lang.String email;
        /**
         * 姓名
         */
        private java.lang.String name;
        /**
         * 头像
         */
        private java.lang.String headImgUrl;
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
         * 身份证起始日期
         */
        private java.util.Date certificateBegin;
        /**
         * 身份证结束日期
         */
        private java.util.Date certificateEnd;
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
         * 业务区域市id
         */
        private java.lang.String businessCityCode;
        /**
         * 业务区区县code
         */
        private java.lang.String businessAreaCode;
        /**
         * 收款银行
         */
        private java.lang.String bankName;
        /**
         * 收款银行账户
         */
        private java.lang.String bankAccountNumber;
        /**
         * 擅长的案由id列表
         */
        private List<Integer> caseBriefIdList;
    }

    @Data
    public static class LawFirmQueryReq extends AdminReq {
        private String q;
        private String mobile;
        private String createdAtFrom;
        private String createdAtTo;
        private Integer id;
        private String type;
    }

    @Data
    public static class LawFirmAddReq extends AdminReq {
        /**
         * 律所名称
         */
        private java.lang.String name;
        /**
         * 律所类型
         */
        private java.lang.String type;
        /**
         * 营业执照图片
         */
        private java.lang.String licenseImg;
        /**
         * 营业执照号
         */
        private java.lang.String licenseNumber;
        /**
         * 法人
         */
        private java.lang.String legalPerson;
        /**
         * 联系人
         */
        private java.lang.String contactPerson;
        /**
         * 联系人手机号
         */
        private java.lang.String contactPhone;
        /**
         * 律所规模
         */
        private java.lang.String teamSize;
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
         * 擅长的案由id列表
         */
        private List<Integer> caseBriefIdList;
    }

    @Data
    public static class LawFirmUpdateReq extends AdminReq {
        private Integer id;
        /**
         * 律所名称
         */
        private java.lang.String name;
        /**
         * 律所类型
         */
        private java.lang.String type;
        /**
         * 营业执照图片
         */
        private java.lang.String licenseImg;
        /**
         * 营业执照号
         */
        private java.lang.String licenseNumber;
        /**
         * 法人
         */
        private java.lang.String legalPerson;
        /**
         * 联系人
         */
        private java.lang.String contactPerson;
        /**
         * 联系人手机号
         */
        private java.lang.String contactPhone;
        /**
         * 律所规模
         */
        private java.lang.String teamSize;
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
         * 擅长的案由id列表
         */
        private List<Integer> caseBriefIdList;
    }

    @Data
    public static class FranchiseeQueryReq extends AdminReq {
        private String q;
        private String type;
        private String status;
        private String mobile;
        private String createdAtFrom;
        private String createdAtTo;
        private Integer id;
        private Integer franchiseeId;
    }

    @Data
    public static class FranchiseeVerifyReq extends AdminReq {
        private Integer id;
        private String reason;
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
    public static class FranchiseeUpdateReq extends AdminReq {
        private Integer id;
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
    public static class AccountUpdateReq extends AdminReq {
        private Integer id;
        /**
         * 项目经理标志
         */
        private java.lang.Boolean projectManagerFlag;
    }
}
