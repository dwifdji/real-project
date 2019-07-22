
package com._360pai.core.condition.comprador;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 *
 * @author Generator
 * @date 2018年09月03日 12时40分06秒
 */
public class TCompradorRequirementCondition implements DaoCondition {

    /**
     *
     */
    private Integer              id;
    /**
     * 委托人id
     */
    private Integer              accountId;
    private Integer              partyId;
    /**
     * 需求名称
     */
    private String               requirementName;
    /**
     * 需求单号
     */
    private String               requirementNo;
    /**
     * 建筑类型 10 住宅 20 商业 30 厂房 40 办公 50 其他
     */
    private String               buildingType;
    /**
     * 付款时间
     */
    private java.util.Date       payTime;
    /**
     * 付款id
     */
    private Integer              payId;
    /**
     * 佣金 %
     */
    private java.math.BigDecimal commissionPercent;
    /**
     * 状态 10 等待平台审核 11审核不通过  12审核通过置办中 20已完成 30撮合成功
     */
    private String               requirementStatus;
    /**
     * 价格区间 开始价格
     */
    private java.math.BigDecimal startPrice;
    /**
     * 价格区间 结束价格
     */
    private java.math.BigDecimal endPrice;
    /**
     * 面积区间 开始面积
     */
    private java.math.BigDecimal startArea;
    /**
     * 面积区间 结束面积
     */
    private java.math.BigDecimal endArea;
    /**
     * 区域
     */
    private Integer              cityId;
    /**
     * 交易方式 10 债权 20 物权 30 股权
     */
    private String               transactionMode;
    /**
     * 拟收购项目标准
     */
    private String               proposedAcquisition;
    /**
     * 其他描述
     */
    private String               description;
    /**
     * 需求单备注 审核不通过理由
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
    private Integer              operatorId;


    private Integer areaFrom;
    private Integer areaTo;
    private Integer priceFrom;
    private Integer priceTo;

    private String orderByPriceAsc;
    private String orderByPriceDesc;
    private String orderByAreaAsc;
    private String orderByAreaDesc;

    public Integer getAreaFrom() {
        return areaFrom;
    }

    public void setAreaFrom(Integer areaFrom) {
        this.areaFrom = areaFrom;
    }

    public Integer getAreaTo() {
        return areaTo;
    }

    public void setAreaTo(Integer areaTo) {
        this.areaTo = areaTo;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
    }


    public String getOrderByPriceDesc() {
        return orderByPriceDesc;
    }

    public void setOrderByPriceDesc(String orderByPriceDesc) {
        this.orderByPriceDesc = orderByPriceDesc;
    }

    public String getOrderByPriceAsc() {
        return orderByPriceAsc;
    }

    public void setOrderByPriceAsc(String orderByPriceAsc) {
        this.orderByPriceAsc = orderByPriceAsc;
    }

    public String getOrderByAreaAsc() {
        return orderByAreaAsc;
    }

    public void setOrderByAreaAsc(String orderByAreaAsc) {
        this.orderByAreaAsc = orderByAreaAsc;
    }

    public String getOrderByAreaDesc() {
        return orderByAreaDesc;
    }

    public void setOrderByAreaDesc(String orderByAreaDesc) {
        this.orderByAreaDesc = orderByAreaDesc;
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
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
     * 需求单号
     */
    public String getRequirementNo() {
        return requirementNo;
    }

    /**
     * 需求单号
     */
    public void setRequirementNo(String requirementNo) {
        this.requirementNo = requirementNo;
    }

    /**
     * 建筑类型 10 住宅 20 商业 30 厂房 40 办公 50 其他
     */
    public String getBuildingType() {
        return buildingType;
    }

    /**
     * 建筑类型 10 住宅 20 商业 30 厂房 40 办公 50 其他
     */
    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    /**
     * 付款时间
     */
    public java.util.Date getPayTime() {
        return payTime;
    }

    /**
     * 付款时间
     */
    public void setPayTime(java.util.Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 付款id
     */
    public Integer getPayId() {
        return payId;
    }

    /**
     * 付款id
     */
    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    /**
     * 佣金 %
     */
    public java.math.BigDecimal getCommissionPercent() {
        return commissionPercent;
    }

    /**
     * 佣金 %
     */
    public void setCommissionPercent(java.math.BigDecimal commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    /**
     * 状态 10 等待平台审核 11审核不通过  12审核通过置办中 20已完成 30撮合成功
     */
    public String getRequirementStatus() {
        return requirementStatus;
    }

    /**
     * 状态 10 等待平台审核 11审核不通过  12审核通过置办中 20已完成 30撮合成功
     */
    public void setRequirementStatus(String requirementStatus) {
        this.requirementStatus = requirementStatus;
    }

    /**
     * 价格区间 开始价格
     */
    public java.math.BigDecimal getStartPrice() {
        return startPrice;
    }

    /**
     * 价格区间 开始价格
     */
    public void setStartPrice(java.math.BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    /**
     * 价格区间 结束价格
     */
    public java.math.BigDecimal getEndPrice() {
        return endPrice;
    }

    /**
     * 价格区间 结束价格
     */
    public void setEndPrice(java.math.BigDecimal endPrice) {
        this.endPrice = endPrice;
    }

    /**
     * 面积区间 开始面积
     */
    public java.math.BigDecimal getStartArea() {
        return startArea;
    }

    /**
     * 面积区间 开始面积
     */
    public void setStartArea(java.math.BigDecimal startArea) {
        this.startArea = startArea;
    }

    /**
     * 面积区间 结束面积
     */
    public java.math.BigDecimal getEndArea() {
        return endArea;
    }

    /**
     * 面积区间 结束面积
     */
    public void setEndArea(java.math.BigDecimal endArea) {
        this.endArea = endArea;
    }

    /**
     * 区域
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * 区域
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * 交易方式 10 债权 20 物权 30 股权
     */
    public String getTransactionMode() {
        return transactionMode;
    }

    /**
     * 交易方式 10 债权 20 物权 30 股权
     */
    public void setTransactionMode(String transactionMode) {
        this.transactionMode = transactionMode;
    }

    /**
     * 拟收购项目标准
     */
    public String getProposedAcquisition() {
        return proposedAcquisition;
    }

    /**
     * 拟收购项目标准
     */
    public void setProposedAcquisition(String proposedAcquisition) {
        this.proposedAcquisition = proposedAcquisition;
    }

    /**
     * 其他描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 其他描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 需求单备注 审核不通过理由
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 需求单备注 审核不通过理由
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
    public Integer getOperatorId() {
        return operatorId;
    }

    /**
     * 操作人id
     */
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

}