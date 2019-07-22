package com._360pai.core.facade.withfudig.req;

import com._360pai.arch.common.RequestModel;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/8/30 14:13
 */
public class WithfudigConfigReq {

    public static class DelConfigData extends RequestModel {
        /**
         * 成功数据id
         */
        private Integer configId;

        private Integer operatorId;

        public Integer getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(Integer operatorId) {
            this.operatorId = operatorId;
        }

        public Integer getConfigId() {
            return configId;
        }

        public void setConfigId(Integer configId) {
            this.configId = configId;
        }
    }

    public static class AddConfigData extends RequestModel {
        /**
         * 成功信息
         */
        private String description;

        private Integer operatorId;

        public Integer getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(Integer operatorId) {
            this.operatorId = operatorId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }


    public static class UpdateConfigData extends RequestModel {
        /**
         * 成功数据id
         */
        private Integer configId;

        /**
         * 成功信息
         */
        private String description;

        /**
         * 排序
         */
        private Integer rank;


        private Integer operatorId;

        public Integer getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(Integer operatorId) {
            this.operatorId = operatorId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        public Integer getConfigId() {
            return configId;
        }

        public void setConfigId(Integer configId) {
            this.configId = configId;
        }
    }

}
