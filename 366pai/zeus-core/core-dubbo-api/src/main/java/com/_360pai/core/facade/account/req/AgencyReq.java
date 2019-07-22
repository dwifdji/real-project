package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: AgencyReq
 * @ProjectName zeus
 * @Description:
 * @date 12/09/2018 15:25
 */
public class AgencyReq extends RequestModel {

    @Getter
    @Setter
    public static class BaseReq extends RequestModel {
        private Integer agencyId;
        private Integer channelAgentAgencyId;
        private Boolean isChannelAgent;
        private Integer accountId;
        /**
         * 是否可以查看保留价
         */
        private java.lang.Boolean canCheckReservePrice;
    }

    @Getter
    @Setter
    public static class QueryReq extends RequestModel {
        /**
         * 省id
         */
        private Integer provinceId;
        /**
         * 城市id
         */
        private Integer cityId;
        /**
         * 区县id
         */
        private Integer areaId;
        private String q;
        private Integer partyId;
        private Boolean isChannelAgent;
        private Integer payBind;
        private String openFadada;
        private String websiteStatus;
    }

    @Getter
    @Setter
    public static class UpdateReq extends RequestModel {
        /**
         * 主键
         */
        @NotNull
        private java.lang.Integer id;
        /**
         * 机构名
         */
        private java.lang.String name;
        /**
         * 名称拼音
         */
        private java.lang.String pinyin;
        /**
         * 机构简称
         */
        private java.lang.String shortName;
        /**
         * logo地址
         */
        private java.lang.String logoUrl;
        /**
         * 注册地址
         */
        private java.lang.String registerAddress;
        /**
         * 办公地址
         */
        private java.lang.String address;
        /**
         * 注册城市
         */
        private java.lang.String registerCityId;
        /**
         * 办公城市
         */
        private java.lang.String cityId;
        /**
         * 省id
         */
        private java.lang.String provinceId;
        /**
         * 注册省id
         */
        private java.lang.String registerProvinceId;
        /**
         * 区县id
         */
        private java.lang.String areaId;
        /**
         * 注册区县id
         */
        private java.lang.String registerAreaId;
        /**
         * 法人
         */
        private java.lang.String legalPerson;
        /**
         * 身份证号
         */
        private java.lang.String idCard;
        /**
         * 身份证正面照
         */
        private java.lang.String idCardFrontImg;
        /**
         * 身份证反面照
         */
        private java.lang.String idCardBackImg;

        /**
         * 营业执照图片
         */
        private java.lang.String licenseImg;
        /**
         * 授权书
         */
        private java.lang.String authorizationImg;
        /**
         * 开户许可证
         */
        private java.lang.String accountLicense;
        /**
         * 营业开始时间
         */
        private java.util.Date businessBegin;
        /**
         * 营业结束时间
         */
        private java.util.Date businessEnd;
        /**
         * 许可证号
         */
        private java.lang.String qualificationNumber;
        /**
         * 许可证图片
         */
        private java.lang.String qualificationImg;
        /**
         * 许可证签发日期
         */
        private java.util.Date qualifiedBegin;
        /**
         * 许可证结束日期
         */
        private java.util.Date qualifiedEnd;
        /**
         * 成拍分成
         */
        private java.lang.Integer serveBuyerPercent;
        /**
         * 送拍分成
         */
        private java.lang.Integer serveSellerPercent;
        /**
         * 机构自我介绍
         */
        private java.lang.String selfIntroduction;
        /**
         * 平台给机构的介绍
         */
        private java.lang.String introduction;
        /**
         * 小程序二维码链接
         */
        private java.lang.String appletQrCode;

        /**
         * 开户人员
         */
        private java.lang.Integer openAccountOperatorId;
        /**
         * 业务对接人
         */
        private java.lang.Integer businessOperatorId;
        /**
         * 受托人
         */
        private java.lang.String trustee;
        /**
         * 受托人联系方式
         */
        private java.lang.String trusteePhone;
    }

    @Getter
    @Setter
    public static class AgencyUpdateReq extends RequestModel {
        @NotNull
        private Integer id;
        // 机构介绍图片
        private String imgUrl;
        // 机构自我介绍
        private String selfIntroduction;
    }


    @Getter
    @Setter
    public static class UpdateDfftOrFadadaReq extends RequestModel {
        @NotNull
        private Integer id;
        private Integer payBind;
        private String fadadaId;
    }
}
