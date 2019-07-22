package com._360pai.core.facade.disposal.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiaolei
 * @create 2018-09-14 13:41
 */
public class DisposalBiddingReq {

    @Data
    public static class GetBiddingInfoReq extends RequestModel {
        /**
         * 处置服务商id
         */
        private Integer partyId;
        /**
         * 投标信息id
         */
        private Integer biddingId;
        /**
         *处置类型: 10：尽调 11：评估 12：司法处置 13：执行处置 14：清房 15：催收  16：查找财产线索
         */
        private String disposalType;

    }

    public static class AddBiddingReq extends RequestModel {

        /**
         * 处置需求id
         */
        private Integer disposalId;
        /**
         * 需求单号
         */
        private String disposalNo;
        /**
         * 处置花费
         */
        private BigDecimal bidCost;
        /**
         * 需求说明
         */
        private String requireDescription;
        /**
         * 特别说明
         */
        private String specialDescription;
        /**
         * 处置服务商id
         */
        private Integer accountId;
        private Integer partyId;
        /**
         * 处置投标id
         */
        private Integer biddingId;
        /**
         * 沟通内容
         */
        private String communicateContent;
        /**
         * 操作员id
         */
        private Integer operatorId;

        public Integer getPartyId() {
            return partyId;
        }

        public void setPartyId(Integer partyId) {
            this.partyId = partyId;
        }

        public Integer getBiddingId() {
            return biddingId;
        }

        public void setBiddingId(Integer biddingId) {
            this.biddingId = biddingId;
        }

        public String getCommunicateContent() {
            return communicateContent;
        }

        public void setCommunicateContent(String communicateContent) {
            this.communicateContent = communicateContent;
        }

        public Integer getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(Integer operatorId) {
            this.operatorId = operatorId;
        }

        public String getDisposalNo() {
            return disposalNo;
        }

        public void setDisposalNo(String disposalNo) {
            this.disposalNo = disposalNo;
        }

        public BigDecimal getBidCost() {
            return bidCost;
        }

        public void setBidCost(BigDecimal bidCost) {
            this.bidCost = bidCost;
        }

        public String getRequireDescription() {
            return requireDescription;
        }

        public void setRequireDescription(String requireDescription) {
            this.requireDescription = requireDescription;
        }

        public String getSpecialDescription() {
            return specialDescription;
        }

        public void setSpecialDescription(String specialDescription) {
            this.specialDescription = specialDescription;
        }

        public Integer getAccountId() {
            return accountId;
        }

        public void setAccountId(Integer accountId) {
            this.accountId = accountId;
        }

        public Integer getDisposalId() {
            return disposalId;
        }

        public void setDisposalId(Integer disposalId) {
            this.disposalId = disposalId;
        }
    }

}
