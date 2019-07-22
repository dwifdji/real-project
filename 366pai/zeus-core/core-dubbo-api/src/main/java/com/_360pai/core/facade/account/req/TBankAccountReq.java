package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author xdrodger
 * @Title: BankReq
 * @ProjectName zeus
 * @Description:
 * @date 25/09/2018 13:01
 */
public class TBankAccountReq {

    @Getter
    @Setter
    public static class BaseReq extends RequestModel {
        private Integer id;
        private Integer partyId;
        private String partyType;
        private String status;
        private Integer acctId;
        private String smsCode;
    }

    @Getter
    @Setter
    public static class CreateReq extends RequestModel {
        private Integer partyId;
        private String partyType;
        /**
         *  银行代码
         */
        private String bankCode;
        /**
         * 是否自定义银行卡
         */
        private boolean bankOptional;
        /**
         *  用户自定义银行名称
         */
        private String bankName;
        /**
         * 开户名称
         */
        @NotBlank
        private String bankAccountName;
        /**
         * 银行账号
         */
        @NotBlank
        private String bankAccountNo;
        /**
         * 支行名称
         */
        private String subBankName;
        @NotBlank
        private String smsCode;
    }

    @Getter
    @Setter
    public static class PlatformCreateReq extends RequestModel {
        private Integer acctId;
        /**
         *  用户自定义银行名称
         */
        @NotBlank
        private String bankName;
        /**
         * 开户名称
         */
        @NotBlank
        private String bankAccountName;
        /**
         * 银行账号
         */
        @NotBlank
        private String bankAccountNo;
        /**
         * 支行名称
         */
        @NotBlank
        private String subBankName;
        /**
         * 是否启用（0 禁用 1 启用）
         */
        @NotBlank
        private String status;
    }

    @Getter
    @Setter
    public static class PlatformUpdateReq extends RequestModel {
        @NotNull
        private Integer id;
        /**
         *  用户自定义银行名称
         */
        private String bankName;
        /**
         * 开户名称
         */
        private String bankAccountName;
        /**
         * 银行账号
         */
        private String bankAccountNo;
        /**
         * 支行名称
         */
        private String subBankName;
        /**
         * 是否启用（0 禁用 1 启用）
         */
        private String status;
    }
}
