package com.winback.core.facade.contract.req;

import com.winback.arch.common.AdminReq;
import lombok.Data;

/**
 * @author xdrodger
 * @Title: AdminContractReq
 * @ProjectName winback
 * @Description:
 * @date 2019/2/14 10:57
 */
public class AdminContractReq {

    @Data
    public static class QueryReq extends AdminReq {
        private String q;
        private Integer contractId;
        private Integer contractTypeId;
        private Integer contractBigTypeId;
        /**
         * 是否上架（0 否 1 是）
         */
        private Boolean saleFlag;
        /**
         * 排序 “downloadCount_desc”：下载量降序排列，“purchaseCount_desc”:销量降序排列，“favoriteCount_desc”:收藏量降序排列
         */
        private String orderBy;
        /**
         * 抬头类型（user：个人，company：企业）
         */
        private String titleType;

        private String status;

        private String createdAtFrom;
        private String createdAtTo;
    }

    @Data
    public static class AddReq extends AdminReq {
        /**
         * 名称
         */
        private java.lang.String name;
        /**
         * 合同类型id
         */
        private Integer contractTypeId;
        /**
         * 金额
         */
        private java.math.BigDecimal amount;
        /**
         * 第一张图片
         */
        private java.lang.String firstImage;
        /**
         * 图片，逗号分隔
         */
        private java.lang.String images;
        /**
         * 使用说明
         */
        private java.lang.String manual;
        /**
         * 提示
         */
        private java.lang.String hint;
        /**
         * 篇幅
         */
        private java.lang.Integer length;
        /**
         * 推荐标志（0 否 1 是）
         */
        private java.lang.Boolean recommendFlag;
        /**
         * 是否上架（0 否 1 是）
         */
        private java.lang.Boolean saleFlag;
        /**
         * 下载链接
         */
        private java.lang.String downloadUrl;
    }

    @Data
    public static class EditReq extends AdminReq {
        private Integer id;
        /**
         * 名称
         */
        private java.lang.String name;
        /**
         * 合同类型id
         */
        private Integer contractTypeId;
        /**
         * 金额
         */
        private java.math.BigDecimal amount;
        /**
         * 第一张图片
         */
        private java.lang.String firstImage;
        /**
         * 图片，逗号分隔
         */
        private java.lang.String images;
        /**
         * 使用说明
         */
        private java.lang.String manual;
        /**
         * 提示
         */
        private java.lang.String hint;
        /**
         * 篇幅
         */
        private java.lang.Integer length;
        /**
         * 推荐标志（0 否 1 是）
         */
        private java.lang.Boolean recommendFlag;
        /**
         * 是否上架（0 否 1 是）
         */
        private java.lang.Boolean saleFlag;
        /**
         * 下载链接
         */
        private java.lang.String downloadUrl;
    }

    @Data
    public static class ResolveReq extends AdminReq {
        private Integer id;
    }

    @Data
    public static class InvoiceVerifyReq extends AdminReq {
        private Integer id;
        private String reason;
        private String invoiceNo;
        private String invoiceImgUrl;
    }
}
