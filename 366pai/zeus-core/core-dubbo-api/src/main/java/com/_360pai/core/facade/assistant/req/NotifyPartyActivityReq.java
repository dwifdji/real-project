
package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;

import java.util.List;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月10日 18时47分27秒
 */
public class NotifyPartyActivityReq extends RequestModel {

    public static class notifyReq extends RequestModel {
        private String agencyCode;
        private Integer partyId;
        private Integer activityId;
        private Integer accountId;
        private Short   pathType;
        private List<Integer> ids;

        private String name;
        private String categoryId;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public Short getPathType() {
            return pathType;
        }

        public void setPathType(Short pathType) {
            this.pathType = pathType;
        }

        public List<Integer> getIds() {
            return ids;
        }

        public void setIds(List<Integer> ids) {
            this.ids = ids;
        }

        public String getAgencyCode() {
            return agencyCode;
        }

        public void setAgencyCode(String agencyCode) {
            this.agencyCode = agencyCode;
        }

        public Integer getPartyId() {
            return partyId;
        }

        public void setPartyId(Integer partyId) {
            this.partyId = partyId;
        }

        public Integer getActivityId() {
            return activityId;
        }

        public void setActivityId(Integer activityId) {
            this.activityId = activityId;
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
    private Boolean beginNotified;
    /**
     *
     */
    private Boolean endNotified;
    /**
     *
     */
    private java.util.Date createdAt;
    /**
     *
     */
    private Integer accountId;

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
    public Boolean getBeginNotified() {
        return beginNotified;
    }

    /**
     *
     */
    public void setBeginNotified(Boolean beginNotified) {
        this.beginNotified = beginNotified;
    }

    /**
     *
     */
    public Boolean getEndNotified() {
        return endNotified;
    }

    /**
     *
     */
    public void setEndNotified(Boolean endNotified) {
        this.endNotified = endNotified;
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

    /**
     *
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     *
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

}
