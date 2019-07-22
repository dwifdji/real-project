package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import com._360pai.core.facade.assistant.vo.CityVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author xdrodger
 * @Title: FundProviderReq
 * @ProjectName zeus
 * @Description:
 * @date 18/09/2018 18:51
 */
public class FundProviderReq {

    @Getter
    @Setter
    public static class BaseReq extends RequestModel {
        private Integer providerId;
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
        @NotNull
        private Integer id;
        /**
         * 公司名称
         */
        private java.lang.String companyName;
        /**
         * 公司类型
         */
        private java.lang.String companyType;
        /**
         * 自定义企业类型名称
         */
        private java.lang.String customCompanyType;
        /**
         * 注册地址
         */
        private java.lang.String registerAddress;
        /**
         * 注册资本
         */
        private java.math.BigDecimal registerCapital;
        /**
         * 配资区域
         */
        private List<CityVo> providerAreas;
        /**
         * 最小配资额度
         */
        private java.math.BigDecimal providerMinAmount;
        /**
         * 最大配资额度
         */
        private java.math.BigDecimal providerMaxAmount;
        /**
         * 配资级别
         */
        private com.alibaba.fastjson.JSONObject providerLevel;
        /**
         * 最小配资成本
         */
        private java.math.BigDecimal providerMinCost;
        /**
         * 最大配资成本
         */
        private java.math.BigDecimal providerMaxCost;
        /**
         * 服务费用
         */
        private java.math.BigDecimal providerFee;
        /**
         * 配资最短期限
         */
        private java.math.BigDecimal providerMinMonth;
        /**
         * 配资最长期限
         */
        private java.math.BigDecimal providerMaxMonth;
        /**
         * 配资模式
         */
        private java.lang.String providerPattern;
        /**
         * 资产包要求
         */
        private java.lang.String assetRequire;
        /**
         * 描述
         */
        private java.lang.String descrip;
        /**
         * 开户人员
         */
        private java.lang.Integer openAccountOperatorId;
        /**
         * 业务对接人
         */
        private java.lang.Integer businessOperatorId;
        /**
         * 联系方式（电话）
         */
        private java.lang.String contactPhone;
        /**
         * 联系人
         */
        private java.lang.String contactPerson;
        /**
         * 单笔配资额度开始
         */
        private java.math.BigDecimal singleMinAmount;
        /**
         * 单笔配资额度结束
         */
        private java.math.BigDecimal singleMaxAmount;
        /**
         * 年化收益率开始
         */
        private java.lang.String annuaReturnMin;
        /**
         * 年化收益率结束
         */
        private java.lang.String annuaReturnMax;
        /**
         * 其他费用
         */
        private java.lang.String otherFee;
        /**
         * 标的类型
         */
        private java.lang.String assetType;
        /**
         * 办公城市
         */
        private String cityId;
        /**
         * 办公地址
         */
        private String address;
        /**
         * 信用代码
         */
        private String license;
        /**
         * 营业执照
         */
        private String licenseImg;
        /**
         * 营业开始时间
         */
        private Date qualifiedBegin;
        /**
         * 营业结束时间
         */
        private Date qualifiedEnd;


        /**
         * 身份证号码
         */
        private String certificateNumber;
        /**
         * 身份证正面照
         */
        private String certificateFrontImg;
        /**
         * 身份证反面照
         */
        private String certificateBackImg;
        /**
         * 默认送拍机构
         */
        private Integer defaultAgencyId;

        private String name;

    }
}
