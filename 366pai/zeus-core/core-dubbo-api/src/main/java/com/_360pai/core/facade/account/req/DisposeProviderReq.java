package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xdrodger
 * @Title: FundProviderReq
 * @ProjectName zeus
 * @Description:
 * @date 18/09/2018 18:51
 */
public class DisposeProviderReq {

    @Getter
    @Setter
    public static class BaseReq extends RequestModel {
        private Integer providerId;
        private Integer accountId;
        private Integer partyId;
    }

    @Getter
    @Setter
    public static class QueryReq extends RequestModel {
        private String q;
        private String status;
    }

    @Getter
    @Setter
    public static class UpdateReq extends RequestModel {
        /**
         *
         */
        private java.lang.Integer id;
        /**
         * 公司名称
         */
        private java.lang.String companyName;
        /**
         * 公司类型
         */
        private java.lang.String companyType;
        /**
         * 注册地址
         */
        private java.lang.String registerAddress;
        /**
         * 注册资本
         */
        private java.math.BigDecimal registerCapital;
        /**
         * 联系人姓名
         */
        private java.lang.String contactName;
        /**
         * 联系方式
         */
        private java.lang.String contactMobile;
        /**
         * 资质证书
         */
        private java.lang.String qualificationUrl;
        /**
         * 工作期限
         */
        private BigDecimal workYear;
        /**
         * 自我介绍
         */
        private java.lang.String introduction;
        /**
         * 过往案例
         */
        private java.lang.String caseUrl;
        /**
         * 可提供服务
         */
        private List<String> provideServices;
        /**
         * 处置类型
         */
        private java.lang.String disposeType;
        /**
         * 开户人员
         */
        private java.lang.Integer openAccountOperatorId;
        /**
         * 业务对接人
         */
        private java.lang.Integer businessOperatorId;
        /**
         * 区域市id
         */
        private String region;
        /**
         * 省区域id
         */
        private java.lang.String regionProvince;
        /**
         * 区域-区县id
         */
        private java.lang.String regionArea;
    }

    @Setter
    @Getter
    public static class GetProviderList extends RequestModel{
        private Integer provinceId;
        private Integer cityId;
        private Integer areaId;
        private String companyName;
        private Integer providerId;
        private String queryStr;
        private String surveyType;
        private String disposeType;
        private Integer levelId;
    }

    @Getter
    @Setter
    public static class AddProvider extends RequestModel {
        private Integer levelId;
        private Integer newId;
        private Integer providerId;
    }

    @Getter
    @Setter
    public static class UpdateContractInfo extends RequestModel {
        private Integer levelId;
        private Integer operatorId;
        private Date contractDate;
        private String contractNo;
    }
}
