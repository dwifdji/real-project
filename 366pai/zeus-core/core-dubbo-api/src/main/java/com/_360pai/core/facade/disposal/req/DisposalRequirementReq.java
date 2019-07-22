package com._360pai.core.facade.disposal.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-09-14 11:21
 */
public class DisposalRequirementReq {

    @Data
    public static class GetPublishInfoReq extends RequestModel {
        private String  todayFlag;
        private String  query;
        /**
         * 发布需求者id
         */
        private Integer partyId;
        /**
         * 需求id
         */
        private Integer disposalId;
        /**
         * 处置类型
         */
        private String  disposalType;
        /**
         * 省id
         */
        private java.lang.String provinceId;
        /**
         * 城市
         */
        private String cityId;
        /**
         * 区县id
         */
        private java.lang.String areaId;
        /**
         * 排序字段
         * cost_asc、period_asc
         */
        private String  orderBy;

        /**
         * 排序参数1
         */
        private String orderVar1;
        /**
         * 排序参数2
         */
        private String orderVar2;

        /**
         * 需求i名称
         */
        private String disposalName;

        /**
         * 审核内容
         */
        private String verifyContent;

        /**
         * 处置单状态
         */
        private String disposalStatus;

        private Integer accountId;
    }

    @Getter
    @Setter
    @ToString
    public static class AddRequirementInfo extends RequestModel {
        /**
         * 处置类型数组
         */
        private String[]   disposalTypes;
        /**
         * 处理周期
         */
        private Double     period;
        /**
         * 处置费用
         */
        private String cost;
        /**
         * 付款成功单号
         */
        private String     payOrder;
        /**
         * 需求描述
         */
        private String     requireDescription;
        /**
         * 案件描述
         */
        private String     caseDescription;
        /**
         * 商品id
         */
        private Integer    assetId;
        /**
         * 需求名称
         */
        private String     disposalName;
        /**
         * 是否是平台 0：非平台 1：平台
         */
        private Integer    isPlatform;
        /**
         * 发布处置需求用户id
         */
        private Integer    accountId;
        private Integer    partyId;
        /**
         * 截止日期
         */
        private Date       deadline;

        private City[] providerAreas;

        private String cityId;

        private String provinceId;

        private String areaId;

        private String[] extra;

    }


    public static class GetBiddingList extends RequestModel {
        private Integer disposalId;
        private Date    bidTime;
        private String  bidStatus;
        private String  partyName;

        public Integer getDisposalId() {
            return disposalId;
        }

        public void setDisposalId(Integer disposalId) {
            this.disposalId = disposalId;
        }

        public Date getBidTime() {
            return bidTime;
        }

        public void setBidTime(Date bidTime) {
            this.bidTime = bidTime;
        }

        public String getBidStatus() {
            return bidStatus;
        }

        public void setBidStatus(String bidStatus) {
            this.bidStatus = bidStatus;
        }

        public String getPartyName() {
            return partyName;
        }

        public void setPartyName(String partyName) {
            this.partyName = partyName;
        }
    }

    @Data
    public static class AdminOperateInfo extends RequestModel {
        private String  verifyContent;
        private Integer disposalId;
        private String  disposalStatus;
        private Integer operatorVerifyId;
        private String  biddingNotice;
    }


}
