
package com._360pai.core.condition.withfudig;

import com._360pai.arch.core.abs.DaoCondition;
import com.alibaba.fastjson.JSONObject;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 *
 * @author Generator
 * @date 2018年09月06日 15时50分14秒
 */
public class TWithfudigRequirementCondition implements DaoCondition {

    /**
     *
     */
    private Integer              id;
    /**
     * 需求名称
     */
    private String               requirementName;
    /**
     * 配资需求单号
     */
    private String               requirementNo;
    /**
     * 标的类型 10 20 30
     */
    private String               assetType;
    /**
     * 交易对价
     */
    private java.math.BigDecimal assetPrice;
    /**
     * 融资金额 元
     */
    private java.math.BigDecimal requirementAmount;
    /**
     * 融资配比
     */
    private JSONObject           requirementMatchPercent;
    /**
     * 融资利息 开始利息 %
     */
    private java.math.BigDecimal requirementInterestPercentStart;
    /**
     * 融资利息 结束利息 %
     */
    private java.math.BigDecimal requirementInterestPercentEnd;
    /**
     * 融资期限 月
     */
    private java.math.BigDecimal requirementTerm;
    /**
     * 状态 0 未支付 10 等待平台审核 11审核不通过  12审核通过置办中 20已完成 30撮合成功
     */
    private String               requirementStatus;
    /**
     * 融资方企业介绍
     */
    private String               companyDescription;
    /**
     * 项目介绍
     */
    private String               itemDescription;
    /**
     * 还款来源介绍
     */
    private String               repaymentDescription;
    /**
     * 担保措施介绍
     */
    private String               guaranteeDescription;
    /**
     * 处置方介绍
     */
    private String               disposalPartyDescription;
    /**
     * 处置方案介绍
     */
    private String               disposalPlanDescription;
    /**
     * 关联的asset 的 id
     */
    private Integer              assetId;
    /**
     * 标的来源是否平台（0 否 1 是）
     */
    private Boolean              isPlatform;
    /**
     * 描述信息
     */
    private String               description;
    /**
     * 付款成功时间
     */
    private java.util.Date       payTime;
    /**
     * 付款成功payOrder单号
     */
    private String               payId;
    /**
     * 备注
     */
    private String               remark;
    /**
     * 是否删除（0 否 1 是）
     */
    private Boolean              isDelete;
    /**
     * 生成时间
     */
    private java.util.Date       createdTime;
    /**
     * 更新时间
     */
    private java.util.Date       updateTime;
    /**
     * 操作人id
     */
    private String               operatorId;

    private Integer accountId;
    private Integer partyId;

    private Integer requirementAmountFrom;
    private Integer requirementAmountTo;

    private Integer requirementTermFrom;
    private Integer requirementTermTo;
    /**
     * 融资利息 asc desc
     */
    private String  orderByInterestPercentAsc;
    private String  orderByInterestPercentDesc;
    /**
     * 融资金额 asc desc
     */
    private String  orderByRequirementAmount;

    private String orderBy;


    public Boolean getPlatform() {
        return isPlatform;
    }

    public void setPlatform(Boolean platform) {
        isPlatform = platform;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getRequirementAmountFrom() {
        return requirementAmountFrom;
    }

    public void setRequirementAmountFrom(Integer requirementAmountFrom) {
        this.requirementAmountFrom = requirementAmountFrom;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getRequirementAmountTo() {
        return requirementAmountTo;
    }

    public void setRequirementAmountTo(Integer requirementAmountTo) {
        this.requirementAmountTo = requirementAmountTo;
    }

    public Integer getRequirementTermFrom() {
        return requirementTermFrom;
    }

    public void setRequirementTermFrom(Integer requirementTermFrom) {
        this.requirementTermFrom = requirementTermFrom;
    }

    public Integer getRequirementTermTo() {
        return requirementTermTo;
    }

    public void setRequirementTermTo(Integer requirementTermTo) {
        this.requirementTermTo = requirementTermTo;
    }

    public String getOrderByInterestPercentAsc() {
        return orderByInterestPercentAsc;
    }

    public void setOrderByInterestPercentAsc(String orderByInterestPercentAsc) {
        this.orderByInterestPercentAsc = orderByInterestPercentAsc;
    }

    public String getOrderByInterestPercentDesc() {
        return orderByInterestPercentDesc;
    }

    public void setOrderByInterestPercentDesc(String orderByInterestPercentDesc) {
        this.orderByInterestPercentDesc = orderByInterestPercentDesc;
    }

    public String getOrderByRequirementAmount() {
        return orderByRequirementAmount;
    }

    public void setOrderByRequirementAmount(String orderByRequirementAmount) {
        this.orderByRequirementAmount = orderByRequirementAmount;
    }

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
     * 需求名称
     */
    public String getRequirementName() {
        return requirementName;
    }

    /**
     * 需求名称
     */
    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    /**
     * 配资需求单号
     */
    public String getRequirementNo() {
        return requirementNo;
    }

    /**
     * 配资需求单号
     */
    public void setRequirementNo(String requirementNo) {
        this.requirementNo = requirementNo;
    }

    /**
     * 标的类型 10 20 30
     */
    public String getAssetType() {
        return assetType;
    }

    /**
     * 标的类型 10 20 30
     */
    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    /**
     * 交易对价
     */
    public java.math.BigDecimal getAssetPrice() {
        return assetPrice;
    }

    /**
     * 交易对价
     */
    public void setAssetPrice(java.math.BigDecimal assetPrice) {
        this.assetPrice = assetPrice;
    }

    /**
     * 融资金额 元
     */
    public java.math.BigDecimal getRequirementAmount() {
        return requirementAmount;
    }

    /**
     * 融资金额 元
     */
    public void setRequirementAmount(java.math.BigDecimal requirementAmount) {
        this.requirementAmount = requirementAmount;
    }

    /**
     * 融资配比
     */
    public JSONObject getRequirementMatchPercent() {
        return requirementMatchPercent;
    }

    /**
     * 融资配比
     */
    public void setRequirementMatchPercent(JSONObject requirementMatchPercent) {
        this.requirementMatchPercent = requirementMatchPercent;
    }

    /**
     * 融资利息 开始利息 %
     */
    public java.math.BigDecimal getRequirementInterestPercentStart() {
        return requirementInterestPercentStart;
    }

    /**
     * 融资利息 开始利息 %
     */
    public void setRequirementInterestPercentStart(java.math.BigDecimal requirementInterestPercentStart) {
        this.requirementInterestPercentStart = requirementInterestPercentStart;
    }

    /**
     * 融资利息 结束利息 %
     */
    public java.math.BigDecimal getRequirementInterestPercentEnd() {
        return requirementInterestPercentEnd;
    }

    /**
     * 融资利息 结束利息 %
     */
    public void setRequirementInterestPercentEnd(java.math.BigDecimal requirementInterestPercentEnd) {
        this.requirementInterestPercentEnd = requirementInterestPercentEnd;
    }

    /**
     * 融资期限 月
     */
    public java.math.BigDecimal getRequirementTerm() {
        return requirementTerm;
    }

    /**
     * 融资期限 月
     */
    public void setRequirementTerm(java.math.BigDecimal requirementTerm) {
        this.requirementTerm = requirementTerm;
    }

    /**
     * 状态 0 未支付 10 等待平台审核 11审核不通过  12审核通过置办中 20已完成 30撮合成功
     */
    public String getRequirementStatus() {
        return requirementStatus;
    }

    /**
     * 状态 0 未支付 10 等待平台审核 11审核不通过  12审核通过置办中 20已完成 30撮合成功
     */
    public void setRequirementStatus(String requirementStatus) {
        this.requirementStatus = requirementStatus;
    }

    /**
     * 融资方企业介绍
     */
    public String getCompanyDescription() {
        return companyDescription;
    }

    /**
     * 融资方企业介绍
     */
    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    /**
     * 项目介绍
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * 项目介绍
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    /**
     * 还款来源介绍
     */
    public String getRepaymentDescription() {
        return repaymentDescription;
    }

    /**
     * 还款来源介绍
     */
    public void setRepaymentDescription(String repaymentDescription) {
        this.repaymentDescription = repaymentDescription;
    }

    /**
     * 担保措施介绍
     */
    public String getGuaranteeDescription() {
        return guaranteeDescription;
    }

    /**
     * 担保措施介绍
     */
    public void setGuaranteeDescription(String guaranteeDescription) {
        this.guaranteeDescription = guaranteeDescription;
    }

    /**
     * 处置方介绍
     */
    public String getDisposalPartyDescription() {
        return disposalPartyDescription;
    }

    /**
     * 处置方介绍
     */
    public void setDisposalPartyDescription(String disposalPartyDescription) {
        this.disposalPartyDescription = disposalPartyDescription;
    }

    /**
     * 处置方案介绍
     */
    public String getDisposalPlanDescription() {
        return disposalPlanDescription;
    }

    /**
     * 处置方案介绍
     */
    public void setDisposalPlanDescription(String disposalPlanDescription) {
        this.disposalPlanDescription = disposalPlanDescription;
    }

    /**
     * 关联的asset 的 id
     */
    public Integer getAssetId() {
        return assetId;
    }

    /**
     * 关联的asset 的 id
     */
    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    /**
     * 标的来源是否平台（0 否 1 是）
     */
    public Boolean getIsPlatform() {
        return isPlatform;
    }

    /**
     * 标的来源是否平台（0 否 1 是）
     */
    public void setIsPlatform(Boolean isPlatform) {
        this.isPlatform = isPlatform;
    }

    /**
     * 描述信息
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述信息
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 付款成功时间
     */
    public java.util.Date getPayTime() {
        return payTime;
    }

    /**
     * 付款成功时间
     */
    public void setPayTime(java.util.Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 付款成功payOrder单号
     */
    public String getPayId() {
        return payId;
    }

    /**
     * 付款成功payOrder单号
     */
    public void setPayId(String payId) {
        this.payId = payId;
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}