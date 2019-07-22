package com.winback.core.facade.contract.req;

import com.winback.arch.common.AppReq;
import lombok.Data;

import java.util.List;

/**
 * @author xdrodger
 * @Title: AppContractReq
 * @ProjectName winback
 * @Description:
 * @date 2019/1/31 14:42
 */
public class AppContractReq {
    @Data
    public static class QueryReq extends AppReq {
        private String q;
        private Integer contractTypeId;
        private Integer contractBigTypeId;
        private String orderBy;
        private Integer contractId;
        private List<Integer> contractIds;
        /**
         * 期限（近三月：3，近一年：12，全部：0）
         */
        private String period;
        /**
         * 订单id
         */
        private Integer orderId;

        private Integer id;
    }

    @Data
    public static class DownloadReq extends AppReq {

        private List<Integer> contractIds;
    }

    @Data
    public static class PreInvoiceReq extends AppReq {
        private List<Integer> orderIds;
    }

    @Data
    public static class InvoiceReq extends AppReq {
        private List<Integer> orderIds;
        /**
         * 邮箱
         */
        private String email;
        /**
         * 纸质/电子（PAPER： 纸质，IMAGE：电子）
         */
        private String type;
        /**
         * 抬头
         */
        private String title;
        /**
         * 抬头类型（user：ger，company：企业）
         */
        private String titleType;
        /**
         * 发票内容
         */
        private String content;
        /**
         * 纳税人识别号
         */
        private String taxpayerIdentificationNumber;
        /**
         * 地址
         */
        private String address;
        /**
         * 联系电话
         */
        private String contactPhone;
        /**
         * 收款银行
         */
        private String bankName;
        /**
         * 收款银行账户
         */
        private String bankAccountNumber;
        /**
         * 备注信息
         */
        private String memo;
        /**
         * 地址和电话
         */
        private java.lang.String addressPhone;
        /**
         * 开户行和账号
         */
        private java.lang.String bankAccount;
    }
}
