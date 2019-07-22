
package com._360pai.core.model.assistant;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年09月17日 10时09分27秒
 */
public class TServiceFollow implements java.io.Serializable {

    /**
     *
     */
    private Integer        id;
    /**
     *
     */
    private Integer        accountId;
    private Integer        partyId;
    /**
     *
     */
    private String         relativeType;
    /**
     *
     */
    private Integer        relativeId;
    /**
     *
     */
    private java.util.Date createdTime;
    /**
     * 删除标识 0未删除 1删除
     */
    private Boolean        delFlag;

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
    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
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

    /**
     *
     */
    public String getRelativeType() {
        return relativeType;
    }

    /**
     *
     */
    public void setRelativeType(String relativeType) {
        this.relativeType = relativeType;
    }

    /**
     *
     */
    public Integer getRelativeId() {
        return relativeId;
    }

    /**
     *
     */
    public void setRelativeId(Integer relativeId) {
        this.relativeId = relativeId;
    }

    /**
     *
     */
    public java.util.Date getCreatedTime() {
        return createdTime;
    }

    /**
     *
     */
    public void setCreatedTime(java.util.Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 删除标识 0未删除 1删除
     */
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * 删除标识 0未删除 1删除
     */
    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

}
