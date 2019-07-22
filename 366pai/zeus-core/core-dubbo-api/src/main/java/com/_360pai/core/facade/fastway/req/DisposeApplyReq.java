package com._360pai.core.facade.fastway.req;

import com._360pai.arch.common.RequestModel;
import com._360pai.core.facade.fastway.resp.DisposeLawOfficeVO;
import com._360pai.core.facade.fastway.resp.DisposeLawyerVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2018-11-26 11:05
 */
public class DisposeApplyReq {

    @Data
    public static class LawyerApplyReq implements Serializable {
        /**
         * 身份证姓名
         */
        private String cardName;
        /**
         * 	身份证号码
         */
        private String cardNo;
        /**
         * 	身份证正面照
         */
        private String cardImg1;
        /**
         * 	身份证反面照
         */
        private String cardImg2;
        /**
         * 常驻城市
         */
        private String residentCity;
        /**
         * 	联系地址
         */
        private String contactAddress;
        /**
         * 	手机号
         */
        private String contactMobile;
        /**
         * 	所属律所
         */
        private String lawOffice;
        /**
         * 	工作年限
         */
        private String workedYear;
        /**
         * 	自我介绍
         */
        private String introduction;
        /**
         * 	律师资格证
         */
        private String LawyerCredential;
        /**
         * 	过往案例
         */
        private String pastCases;
        /**
         * 10:h5   20:主站
         */
        private String source;
        /**
         * 认证信息id
         */
        private Integer partyId;
    }


    @Data
    public static class LawOfficeApplyReq implements Serializable{
        /**
         * 律所名称
         */
        private String lawOffice;
        /**
         * 社会信用代码
         */
        private String socialCreditCode;
        /**
         * 营业执照
         */
        private String businessLicense;
        /**
         * 注册城市
         */
        private String registeredCity;
        /**
         * 注册地址
         */
        private String registeredAddress;
        /**
         * 工作城市
         */
        private String workCity;
        /**
         * 工作地址
         */
        private String workAddress;
        /**
         * 联系人姓名
         */
        private String contactName;
        /**
         * 联系人手机号
         */
        private String contactMobile;
        /**
         * 自我介绍
         */
        private String introduction;
        /**
         * 事务所资格证
         */
        private String LawOfficeCredential;
        /**
         * 事务所资格证副本
         */
        private String LawOfficeCredentialCopy;
        /**
         * 	是	string
         */
        private String adminAuthFile;
        /**
         * 10:h5   20:主站
         */
        private String source;
        /**
         * 认证id
         */
        private Integer partyId;
    }

    @Data
    public static class DisposeFindReq extends RequestModel {
        /**
         * 申请状态
         */
        private String applyStatus;
        /**
         * 手机号/用户名
         */
        private String query;

        private String[] emailAddress;
    }

    @Data
    public static class DisposeApplyVerify extends RequestModel {
        /**
         * 申请状态
         */
        private String applyStatus;
        /**
         * applyId
         */
        private Integer applyId;
        private DisposeLawyerVO lawyerVO;
        private DisposeLawOfficeVO lawOfficeVO;
        private String refuseReason;
        /**
         * 开户人员
         */
        private java.lang.Integer openAccountOperatorId;
        /**
         * 业务对接人
         */
        private java.lang.Integer businessOperatorId;
    }


}
