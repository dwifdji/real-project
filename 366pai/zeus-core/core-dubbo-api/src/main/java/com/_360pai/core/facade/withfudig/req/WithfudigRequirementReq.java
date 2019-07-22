package com._360pai.core.facade.withfudig.req;

import com._360pai.arch.common.RequestModel;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;


/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/7 15:21
 */
public class WithfudigRequirementReq {

    @Setter
    @Getter
    public static class RequirementListForAdmin extends RequestModel {
        private String requirementStatus;
        private String requirementName;
    }

    @Getter
    @Setter
    public static class RequirementRelateAssertId extends RequestModel {
        private Integer assetId;
        private Integer requirementId;

        private Integer accountId;
        private Integer partyId;
    }

    @Getter
    @Setter
    public static class RequirementAdd extends RequestModel {
        /**
         * 需求名称
         */
        private String               requirementName;
        /**
         * 标的类型 10 债权 20 物权 30 债权包
         */
        private String               assetType;
        /**
         * 交易对价
         */
        private java.math.BigDecimal assetPrice;
        /**
         * 融资金额 元
         */
        private java.math.BigDecimal requirementAmount;
        /**
         * 融资配比
         */
        private JSONObject           requirementMatchPercent;
        /**
         * 融资利息 开始利息 %
         */
        private java.math.BigDecimal requirementInterestPercentStart;
        /**
         * 融资利息 结束利息 %
         */
        private java.math.BigDecimal requirementInterestPercentEnd;
        /**
         * 融资期限 月
         */
        private java.math.BigDecimal requirementTerm;
        private String               requirementStatus;
        /**
         * 融资方企业介绍
         */
        private String               companyDescription;
        /**
         * 项目介绍
         */
        private String               itemDescription;
        /**
         * 还款来源介绍
         */
        private String               repaymentDescription;
        /**
         * 担保措施介绍
         */
        private String               guaranteeDescription;
        /**
         * 处置方介绍
         */
        private String               disposalPartyDescription;
        /**
         * 处置方案介绍
         */
        private String               disposalPlanDescription;
        /**
         * 关联的asset 的 id
         */
        private Integer              assetId;
        /**
         * 标的来源是否平台（0 否 1 是）
         */
        private Boolean              isPlatform;
        /**
         * 描述信息
         */
        private String               description;

        /**
         * 备注
         */
        private String remark;

        private Integer accountId;
        private Integer partyId;

        private Integer activityId;

        /**
         * 预展图
         */
        private String[] extra;

    }

    @Getter
    @Setter
    public static class RequirementUpdate extends RequestModel {
        private Integer              requirementId;
        /**
         * 需求名称
         */
        private String               requirementName;
        /**
         * 标的类型 10 20 30
         */
        private String               assetType;
        /**
         * 交易对价
         */
        private java.math.BigDecimal assetPrice;
        /**
         * 融资金额 元
         */
        private java.math.BigDecimal requirementAmount;
        /**
         * 融资配比
         */
        private JSONObject           requirementMatchPercent;
        /**
         * 融资利息 开始利息 %
         */
        private java.math.BigDecimal requirementInterestPercentStart;
        /**
         * 融资利息 结束利息 %
         */
        private java.math.BigDecimal requirementInterestPercentEnd;
        /**
         * 融资期限 月
         */
        private java.math.BigDecimal requirementTerm;
        private String               requirementStatus;
        /**
         * 融资方企业介绍
         */
        private String               companyDescription;
        /**
         * 项目介绍
         */
        private String               itemDescription;
        /**
         * 还款来源介绍
         */
        private String               repaymentDescription;
        /**
         * 担保措施介绍
         */
        private String               guaranteeDescription;
        /**
         * 处置方介绍
         */
        private String               disposalPartyDescription;
        /**
         * 处置方案介绍
         */
        private String               disposalPlanDescription;
        /**
         * 关联的asset 的 id
         */
        private Integer              assetId;
        /**
         * 标的来源是否平台（0 否 1 是）
         */
        private Boolean              isPlatform;
        /**
         * 描述信息
         */
        private String               description;

        /**
         * 备注
         */
        private String remark;

        private Integer accountId;
        private Integer partyId;

    }

    @Getter
    @Setter
    public static class RequirementListForPlatform extends RequestModel {
        private String  query;
        private Integer requirementAmountFrom;
        private Integer requirementAmountTo;

        private Integer requirementTermFrom;
        private Integer requirementTermTo;
        /**
         * 融资利息 asc desc
         */
        private String  orderByInterestPercent;
        /**
         * 融资金额 asc desc
         */
        private String  orderByRequirementAmount;
        private Integer partyId;

        /**
         * 是否今日上新
         */
        private String todayFlag;
        private Integer accountId;
    }

    @Getter
    @Setter
    public static class MyRequirementList extends RequestModel {
        private Integer partyId;
    }

    @Getter
    @Setter
    public static class RequirementId extends RequestModel {
        private Integer requirementId;

        private Integer operatorId;
    }

    @Getter
    @Setter
    public static class RequirementDetailReq extends RequestModel {
        private Integer requirementId;
        private Integer partyId;
        private Integer accountId;
    }

    @Getter
    @Setter
    public static class SpecialNoticeUpdate extends RequestModel {
        /**
         * 关联的 t_comprador_requirement 的id
         */
        private Integer requirementId;
        private String  specialNotice;
        private Integer operatorId;
    }

    @Getter
    @Setter
    public static class RequirementStatusUpdate extends RequestModel {
        /**
         * 关联的 t_comprador_requirement 的id
         */
        private Integer requirementId;
        /**
         * 10 等待平台审核 11审核不通过  12审核通过置办中 20已完成 30撮合成功
         */
        private String  status;
        /**
         * 审核不通过 备注
         */
        private String  remark;

        private Integer operatorId;

    }

}
