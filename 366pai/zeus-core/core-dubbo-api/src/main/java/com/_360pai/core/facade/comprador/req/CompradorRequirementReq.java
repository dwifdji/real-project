package com._360pai.core.facade.comprador.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 描述 资产大买办
 *
 * @author : whisky_vip
 * @date : 2018/8/30 10:18
 */
public class CompradorRequirementReq {

    @Data
    public static class CompradorRequirementAdd extends RequestModel {

        /**
         * 前端不需要传
         */
        private Integer accountId;
        private Integer partyId;

        /**
         * 需求名称
         */
        private String requirementName;

        /**
         * 建筑类型
         */
        private String buildingType;

        /**
         * 佣金 %
         */
        private java.math.BigDecimal commissionPercent;

        /**
         * 价格区间 开始价格
         */
        private java.math.BigDecimal startPrice;
        /**
         * 价格区间 结束价格
         */
        private java.math.BigDecimal endPrice;
        /**
         * 面积区间 开始面积
         */
        private java.math.BigDecimal startArea;
        /**
         * 面积区间 结束面积
         */
        private java.math.BigDecimal endArea;
        /**
         * 区域
         */
        private Integer              cityId;
        /**
         * 交易方式 10 债权 20 物权 30 股权
         */
        private String               transactionMode;
        /**
         * 拟收购项目标准
         */
        private String               proposedAcquisition;
        /**
         * 其他描述
         */
        private String               description;

    }

    public static class RequirementUpdate extends RequestModel {

        private Integer requirementId;

        /**
         * 需求名称
         */
        private String requirementName;

        /**
         * 建筑类型
         */
        private String buildingType;

        /**
         * 佣金 %
         */
        private java.math.BigDecimal commissionPercent;

        /**
         * 价格区间 开始价格
         */
        private java.math.BigDecimal startPrice;
        /**
         * 价格区间 结束价格
         */
        private java.math.BigDecimal endPrice;
        /**
         * 面积区间 开始面积
         */
        private java.math.BigDecimal startArea;
        /**
         * 面积区间 结束面积
         */
        private java.math.BigDecimal endArea;
        /**
         * 区域
         */
        private Integer              cityId;
        /**
         * 交易方式 10 债权 20 物权 30 股权
         */
        private String               transactionMode;
        /**
         * 拟收购项目标准
         */
        private String               proposedAcquisition;
        /**
         * 其他描述
         */
        private String               description;

        private Integer partyId;

        public Integer getPartyId() {
            return partyId;
        }

        public void setPartyId(Integer partyId) {
            this.partyId = partyId;
        }

        public Integer getRequirementId() {
            return requirementId;
        }

        public void setRequirementId(Integer requirementId) {
            this.requirementId = requirementId;
        }

        public String getRequirementName() {
            return requirementName;
        }

        public void setRequirementName(String requirementName) {
            this.requirementName = requirementName;
        }

        public String getBuildingType() {
            return buildingType;
        }

        public void setBuildingType(String buildingType) {
            this.buildingType = buildingType;
        }

        public BigDecimal getCommissionPercent() {
            return commissionPercent;
        }

        public void setCommissionPercent(BigDecimal commissionPercent) {
            this.commissionPercent = commissionPercent;
        }

        public BigDecimal getStartPrice() {
            return startPrice;
        }

        public void setStartPrice(BigDecimal startPrice) {
            this.startPrice = startPrice;
        }

        public BigDecimal getEndPrice() {
            return endPrice;
        }

        public void setEndPrice(BigDecimal endPrice) {
            this.endPrice = endPrice;
        }

        public BigDecimal getStartArea() {
            return startArea;
        }

        public void setStartArea(BigDecimal startArea) {
            this.startArea = startArea;
        }

        public BigDecimal getEndArea() {
            return endArea;
        }

        public void setEndArea(BigDecimal endArea) {
            this.endArea = endArea;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public String getTransactionMode() {
            return transactionMode;
        }

        public void setTransactionMode(String transactionMode) {
            this.transactionMode = transactionMode;
        }

        public String getProposedAcquisition() {
            return proposedAcquisition;
        }

        public void setProposedAcquisition(String proposedAcquisition) {
            this.proposedAcquisition = proposedAcquisition;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


    @Data
    public static class RequirementStatusUpdate extends RequestModel {
        /**
         * 关联的 t_comprador_requirement 的id
         */
        private Integer requirementId;

        /**
         * 审核不通过 备注
         */
        private String remark;

        //不需要传
        private String  status;
        private Integer operatorId;
    }

}
