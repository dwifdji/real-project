package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by RuQ on 2018/8/27 17:11
 */
public class DisposeProviderApplyReq implements Serializable {

    /**
     *
     */
    private java.lang.Integer id;
    /**
     *
     */
    private java.lang.Integer accountId;
    /**
     *
     */
    private java.lang.String mobile;
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
    private java.lang.Long registerCapital;
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
    private java.lang.Integer workYear;
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
    private java.lang.String provideService;
    /**
     * 处置类型
     */
    private java.lang.String disposeType;
    /**
     *
     */
    private java.util.Date createTime;
    /**
     *
     */
    private java.util.Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public Long getRegisterCapital() {
        return registerCapital;
    }

    public void setRegisterCapital(Long registerCapital) {
        this.registerCapital = registerCapital;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getQualificationUrl() {
        return qualificationUrl;
    }

    public void setQualificationUrl(String qualificationUrl) {
        this.qualificationUrl = qualificationUrl;
    }

    public Integer getWorkYear() {
        return workYear;
    }

    public void setWorkYear(Integer workYear) {
        this.workYear = workYear;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCaseUrl() {
        return caseUrl;
    }

    public void setCaseUrl(String caseUrl) {
        this.caseUrl = caseUrl;
    }

    public String getProvideService() {
        return provideService;
    }

    public void setProvideService(String provideService) {
        this.provideService = provideService;
    }

    public String getDisposeType() {
        return disposeType;
    }

    public void setDisposeType(String disposeType) {
        this.disposeType = disposeType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static class BaseReq extends RequestModel {
        private Integer accountId;
        private Integer partyId;
        private Integer applyId;
        private Integer operatorId;
        private String reason;
        /**
         * 开户人员
         */
        private java.lang.Integer openAccountOperatorId;
        /**
         * 业务对接人
         */
        private java.lang.Integer businessOperatorId;

        public Integer getAccountId() {
            return accountId;
        }

        public void setAccountId(Integer accountId) {
            this.accountId = accountId;
        }

        public Integer getPartyId() {
            return partyId;
        }

        public void setPartyId(Integer partyId) {
            this.partyId = partyId;
        }

        public Integer getApplyId() {
            return applyId;
        }

        public void setApplyId(Integer applyId) {
            this.applyId = applyId;
        }

        public Integer getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(Integer operatorId) {
            this.operatorId = operatorId;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public Integer getOpenAccountOperatorId() {
            return openAccountOperatorId;
        }

        public void setOpenAccountOperatorId(Integer openAccountOperatorId) {
            this.openAccountOperatorId = openAccountOperatorId;
        }

        public Integer getBusinessOperatorId() {
            return businessOperatorId;
        }

        public void setBusinessOperatorId(Integer businessOperatorId) {
            this.businessOperatorId = businessOperatorId;
        }
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

    @Getter
    @Setter
    public static class CreateReq extends RequestModel {
        private java.lang.Integer accountId;
        private java.lang.Integer partyId;
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
         * 工作年限
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
         * 区域
         */
        private String region;
        /**
         * 所属律所
         */
        private String lawOffice;
        /**
         * 身份证正面照
         */
        private String certificateFrontImg;
        /**
         * 身份证背面照
         */
        private String certificateBackImg;
        /**
         * 身份证号码
         */
        private String certificateNumber;

        private Integer operatorId;

        /**
         * 区域所在省
         */
        private String regionProvince;
        /**
         * 区域所在区县
         */
        private String regionArea;
    }

    public static class UpdateReq extends RequestModel {

    }
}
