package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;

import java.math.BigDecimal;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/7 10:08
 */
public class ServiceConfigReq {

    public static class UpdateTotalNum extends RequestModel {
        private BigDecimal totalNum;
        private Integer    operatorId;

        public Integer getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(Integer operatorId) {
            this.operatorId = operatorId;
        }

        public BigDecimal getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(BigDecimal totalNum) {
            this.totalNum = totalNum;
        }
    }

    public static class UpdateTotalAmount extends RequestModel {
        private BigDecimal totalAmount;
        private Integer    operatorId;

        public Integer getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(Integer operatorId) {
            this.operatorId = operatorId;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }
    }

    public static class ConfigList extends RequestModel {

    }
}
