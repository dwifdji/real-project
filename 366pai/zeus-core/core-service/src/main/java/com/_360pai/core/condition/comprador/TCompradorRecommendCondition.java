
package com._360pai.core.condition.comprador;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 *
 * @author Generator
 * @date 2018年09月03日 12时40分06秒
 */
public class TCompradorRecommendCondition implements DaoCondition {

    /**
     *
     */
    private Integer        id;
    /**
     * 单号
     */
    private String         recommendNo;
    /**
     * 联系电话
     */
    private String         contactPhone;
    /**
     * 联系地址
     */
    private String         contactAddress;
    /**
     * 联系人
     */
    private String         contactName;
    /**
     * 资产说明
     */
    private String         recommendDescription;
    /**
     * 推荐人id
     */
    private Integer        accountId;
    private Integer        partyId;
    /**
     * 10 推介中 20已完成 30撮合成功
     */
    private String         recommendStatus;
    /**
     * 备注
     */
    private String         remark;
    /**
     * 是否删除（0 否 1 是）
     */
    private Boolean        isDelete;
    /**
     * 生成时间
     */
    private java.util.Date createdTime;
    /**
     * 更新时间
     */
    private java.util.Date updateTime;
    /**
     * 操作人id
     */
    private String         operatorId;

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
     * 单号
     */
    public String getRecommendNo() {
        return recommendNo;
    }

    /**
     * 单号
     */
    public void setRecommendNo(String recommendNo) {
        this.recommendNo = recommendNo;
    }

    /**
     * 联系电话
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * 联系电话
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 联系地址
     */
    public String getContactAddress() {
        return contactAddress;
    }

    /**
     * 联系地址
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    /**
     * 联系人
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 联系人
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 资产说明
     */
    public String getRecommendDescription() {
        return recommendDescription;
    }

    /**
     * 资产说明
     */
    public void setRecommendDescription(String recommendDescription) {
        this.recommendDescription = recommendDescription;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 10 推介中 20已完成 30撮合成功
     */
    public String getRecommendStatus() {
        return recommendStatus;
    }

    /**
     * 10 推介中 20已完成 30撮合成功
     */
    public void setRecommendStatus(String recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    /**
     * 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 是否删除（0 否 1 是）
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 是否删除（0 否 1 是）
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 生成时间
     */
    public java.util.Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 生成时间
     */
    public void setCreatedTime(java.util.Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 更新时间
     */
    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 操作人id
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * 操作人id
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }
}