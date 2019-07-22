
package com._360pai.core.facade.activity.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月10日 18时47分27秒
 */
public class FavoriteActivityReq extends RequestModel {

    public static class Query extends RequestModel {
        private Integer categoryId;
        private String activityStatus;
        private String agencyCode;
        private Integer partyId;

        /**
         * 拍卖方式
         * ENGLISH = 1  # 增价 升价拍
         * DUTCH = 2  # 减价 降价拍模式
         * SEALED = 3  # 暗标 一口价暗标
         * PUBLIC = 4  # 明标 一口价明标
         * FREE = 5  # 自由报价 自由出价 择优成交
         * FLASH = 6  # 秒杀 限时秒杀
         */
        private String activityMode;
        private String q;
        private Integer accountId;

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }

        public Integer getPartyId() {
            return partyId;
        }

        public void setPartyId(Integer partyId) {
            this.partyId = partyId;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getActivityStatus() {
            return activityStatus;
        }

        public void setActivityStatus(String activityStatus) {
            this.activityStatus = activityStatus;
        }

        public String getActivityMode() {
            return activityMode;
        }

        public void setActivityMode(String activityMode) {
            this.activityMode = activityMode;
        }

        public String getAgencyCode() {
            return agencyCode;
        }

        public void setAgencyCode(String agencyCode) {
            this.agencyCode = agencyCode;
        }

        public Integer getAccountId() {
            return accountId;
        }

        public void setAccountId(Integer accountId) {
            this.accountId = accountId;
        }
    }


    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Integer partyId;
    /**
     *
     */
    private Integer activityId;
    /**
     *
     */
    private java.util.Date createdAt;

    /**
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     */
    public Integer getPartyId() {
        return partyId;
    }

    /**
     *
     */
    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    /**
     *
     */
    public Integer getActivityId() {
        return activityId;
    }

    /**
     *
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     *
     */
    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    /**
     *
     */
    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

}
