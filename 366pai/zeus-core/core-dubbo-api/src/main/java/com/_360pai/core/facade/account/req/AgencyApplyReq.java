package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: AgencyApplyReq
 * @ProjectName zeus
 * @Description:
 * @date 15/09/2018 19:44
 */
public class AgencyApplyReq extends RequestModel {

    public static class BaseReq extends RequestModel {
        private Long id;
        private Integer operatorId;
        private Integer accountId;
        private String remark;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(Integer operatorId) {
            this.operatorId = operatorId;
        }

        public Integer getAccountId() {
            return accountId;
        }

        public void setAccountId(Integer accountId) {
            this.accountId = accountId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    @Setter
    @Getter
    public static class CreateReq extends RequestModel {
        /**
         * 账户Id
         */
        private java.lang.Integer accountId;
        /**
         * 机构名
         */
        @NotBlank
        private java.lang.String name;
        /**
         * 注册地址
         */
        @NotBlank
        private java.lang.String registerAddress;
        /**
         * 办公地址
         */
        @NotBlank
        private java.lang.String address;
        /**
         * 注册城市
         */
        @NotNull
        private java.lang.String registerCityId;
        /**
         * 办公城市
         */
        @NotNull
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
         * 开户许可证
         */
        @NotBlank
        private java.lang.String accountLicense;
        /**
         * 授权书
         */
        @NotBlank
        private java.lang.String authorizationImg;
        /**
         * 身份证号
         */
        @NotBlank
        private java.lang.String idCard;
        /**
         * 身份证反面照
         */
        @NotBlank
        private java.lang.String idCardBackImg;
        /**
         * 身份证正面照
         */
        @NotBlank
        private java.lang.String idCardFrontImg;
        /**
         * 法人
         */
        @NotBlank
        private java.lang.String legalPerson;
        /**
         * 营业执照号
         */
        @NotBlank
        private java.lang.String license;

        /**
         * 营业执照图片
         */
        @NotBlank
        private java.lang.String licenseImg;
        /**
         * logo图片
         */
        @NotBlank
        private java.lang.String logoUrl;
        /**
         * 许可证图片
         */
        @NotBlank
        private java.lang.String qualificationImg;
    }

    public static class QueryReq extends RequestModel {
        private String q;
        private String status;

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    @Setter
    @Getter
    public static class UpdateReq extends RequestModel {
        /**
         * 主键
         */
        @NotNull
        private java.lang.Long id;
        /**
         * 机构编号
         */
        private java.lang.String code;
        /**
         * 简称
         */
        private java.lang.String shortName;
        /**
         * 许可证编号
         */
        private java.lang.String qualificationNumber;
        /**
         * 许可证签发日期
         */
        private java.util.Date qualifiedBegin;
        /**
         * 许可证结束日期
         */
        private java.util.Date qualifiedEnd;
        /**
         * 营业开始时间
         */
        private java.util.Date businessBegin;
        /**
         * 营业结束时间
         */
        private java.util.Date businessEnd;
        /**
         * 成拍分成
         */
        private java.lang.Integer serveBuyerPercent;
        /**
         * 送拍分成
         */
        private java.lang.Integer serveSellerPercent;
        /**
         * 审核备注
         */
        private java.lang.String remark;
        /**
         * 开户人员
         */
        private java.lang.Integer openAccountOperatorId;
        /**
         * 业务对接人
         */
        private java.lang.Integer businessOperatorId;
        /**
         * 省Id
         */
        private java.lang.String provinceId;
        /**
         * 城市Id
         */
        private java.lang.String cityId;
        /**
         * 区县id
         */
        private java.lang.String areaId;
        /**
         * 注册城市Id
         */
        private java.lang.String registerCityId;
        /**
         * 注册省id
         */
        private java.lang.String registerProvinceId;
        /**
         * 注册区县id
         */
        private java.lang.String registerAreaId;
        /**
         * 受托人
         */
        private java.lang.String trustee;
        /**
         * 受托人联系方式
         */
        private java.lang.String trusteePhone;
    }
}
