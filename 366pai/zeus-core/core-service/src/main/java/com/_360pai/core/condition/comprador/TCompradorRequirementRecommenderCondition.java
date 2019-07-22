
package com._360pai.core.condition.comprador;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 *
 * @author Generator
 * @date 2018年09月03日 12时40分06秒
 */
public class TCompradorRequirementRecommenderCondition implements DaoCondition {

    /**
     *
     */
    private Integer        id;
    /**
     * 单号
     */
    private String         recommenderNo;
    /**
     * 推荐人id
     */
    private Integer        accountId;
    private Integer        partyId;
    /**
     * 关联的 t_comprador_requirement 的id
     */
    private Integer        requirementId;
    /**
     * 描述
     */
    private String         description;
    /**
     * 状态
     */
    private String         recommenderStatus;
    /**
     * 备注 沟通内容
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
    public String getRecommenderNo() {
        return recommenderNo;
    }

    /**
     * 单号
     */
    public void setRecommenderNo(String recommenderNo) {
        this.recommenderNo = recommenderNo;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 关联的 t_comprador_requirement 的id
     */
    public Integer getRequirementId() {
        return requirementId;
    }

    /**
     * 关联的 t_comprador_requirement 的id
     */
    public void setRequirementId(Integer requirementId) {
        this.requirementId = requirementId;
    }

    /**
     * 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 状态
     */
    public String getRecommenderStatus() {
        return recommenderStatus;
    }

    /**
     * 状态
     */
    public void setRecommenderStatus(String recommenderStatus) {
        this.recommenderStatus = recommenderStatus;
    }

    /**
     * 备注 沟通内容
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注 沟通内容
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