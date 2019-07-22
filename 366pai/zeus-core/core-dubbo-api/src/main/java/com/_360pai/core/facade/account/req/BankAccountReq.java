package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author xdrodger
 * @Title: BankReq
 * @ProjectName zeus
 * @Description:
 * @date 25/09/2018 13:01
 */
public class BankAccountReq {

    @Getter
    @Setter
    public static class BaseReq extends RequestModel {
        private Integer id;
        private Integer partyId;
    }

    @Getter
    @Setter
    public static class CreateReq extends RequestModel {
        private Integer partyId;
        /**
         *  银行id
         */
        @NotNull
        private Integer bankId;
        /**
         * 开户名称
         */
        @NotBlank
        private String name;
        /**
         * 银行账号
         */
        @NotBlank
        private String number;
    }

    @Getter
    @Setter
    public static class UpdateReq extends RequestModel {
        private Integer partyId;
        /**
         * id
         */
        @NotNull
        private Integer id;
        /**
         *  银行id
         */
        @NotNull
        private Integer bankId;
        /**
         * 开户名称
         */
        @NotBlank
        private String name;
        /**
         * 银行账号
         */
        @NotBlank
        private String number;
    }
}
