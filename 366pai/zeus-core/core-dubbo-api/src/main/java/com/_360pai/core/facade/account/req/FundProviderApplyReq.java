package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import com._360pai.core.facade.assistant.vo.CityVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by RuQ on 2018/8/27 17:10
 */
public class FundProviderApplyReq implements Serializable {
    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     *
     */
    private java.lang.Integer accountId;
    /**
     *
     */
    private java.lang.String mobile;
    /**
     * 公司名称
     */
    private java.lang.String companyName;
    /**
     * 公司类型
     */
    private java.lang.String companyType;
    /**
     * 注册地址
     */
    private java.lang.String registerAddress;
    /**
     * 注册资本
     */
    private java.lang.Long registerCapital;
    /**
     * 配资区域
     */
    private java.lang.String providerArea;
    /**
     * 最小配资额度
     */
    private java.lang.Long providerMinAmount;
    /**
     * 最大配资额度
     */
    private java.lang.Long providerMaxAmount;
    /**
     * 配资级别
     */
    private java.lang.String providerLevel;
    /**
     * 配资比例
     */
    private java.lang.Integer providerRate;
    /**
     * 最小配资成本
     */
    private java.lang.Integer providerMinCost;
    /**
     * 最大配资成本
     */
    private java.lang.Integer providerMaxCost;
    /**
     * 服务费用
     */
    private java.lang.Long providerFee;
    /**
     * 配资最短期限
     */
    private java.lang.Integer providerMinMonth;
    /**
     * 配资最长期限
     */
    private java.lang.Integer providerMaxMonth;
    /**
     * 配资模式
     */
    private java.lang.String providerPattern;
    /**
     * 资产包要求
     */
    private java.lang.String assetRequire;
    /**
     * 描述
     */
    private java.lang.String descrip;
    /**
     * 申请状态(PENDING = 1,APPROVED = 2,REJECT = 3)
     */
    private java.lang.String status;
    /**
     * 原因
     */
    private java.lang.String reason;
    /**
     * 审核人Id
     */
    private java.lang.Integer operatorId;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 更新时间
     */
    private java.util.Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public Long getRegisterCapital() {
        return registerCapital;
    }

    public void setRegisterCapital(Long registerCapital) {
        this.registerCapital = registerCapital;
    }

    public String getProviderArea() {
        return providerArea;
    }

    public void setProviderArea(String providerArea) {
        this.providerArea = providerArea;
    }

    public Long getProviderMinAmount() {
        return providerMinAmount;
    }

    public void setProviderMinAmount(Long providerMinAmount) {
        this.providerMinAmount = providerMinAmount;
    }

    public Long getProviderMaxAmount() {
        return providerMaxAmount;
    }

    public void setProviderMaxAmount(Long providerMaxAmount) {
        this.providerMaxAmount = providerMaxAmount;
    }

    public String getProviderLevel() {
        return providerLevel;
    }

    public void setProviderLevel(String providerLevel) {
        this.providerLevel = providerLevel;
    }

    public Integer getProviderRate() {
        return providerRate;
    }

    public void setProviderRate(Integer providerRate) {
        this.providerRate = providerRate;
    }

    public Integer getProviderMinCost() {
        return providerMinCost;
    }

    public void setProviderMinCost(Integer providerMinCost) {
        this.providerMinCost = providerMinCost;
    }

    public Integer getProviderMaxCost() {
        return providerMaxCost;
    }

    public void setProviderMaxCost(Integer providerMaxCost) {
        this.providerMaxCost = providerMaxCost;
    }

    public Long getProviderFee() {
        return providerFee;
    }

    public void setProviderFee(Long providerFee) {
        this.providerFee = providerFee;
    }

    public Integer getProviderMinMonth() {
        return providerMinMonth;
    }

    public void setProviderMinMonth(Integer providerMinMonth) {
        this.providerMinMonth = providerMinMonth;
    }

    public Integer getProviderMaxMonth() {
        return providerMaxMonth;
    }

    public void setProviderMaxMonth(Integer providerMaxMonth) {
        this.providerMaxMonth = providerMaxMonth;
    }

    public String getProviderPattern() {
        return providerPattern;
    }

    public void setProviderPattern(String providerPattern) {
        this.providerPattern = providerPattern;
    }

    public String getAssetRequire() {
        return assetRequire;
    }

    public void setAssetRequire(String assetRequire) {
        this.assetRequire = assetRequire;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Getter
    @Setter
    public static class BaseReq extends RequestModel {
        private Integer accountId;
        private Integer partyId;
        private Integer applyId;
        private Integer operatorId;
        private String reason;
        /**
         * 开户人员
         */
        private java.lang.Integer openAccountOperatorId;
        /**
         * 业务对接人
         */
        private java.lang.Integer businessOperatorId;
    }

    @Getter
    @Setter
    public static class QueryReq extends RequestModel {
        private String q;
        private String status;
    }

    @Getter
    @Setter
    public static class CreateReq extends RequestModel {
        private Integer accountId;
        private java.lang.Integer partyId;
        /**
         * 公司名称
         */
        private java.lang.String companyName;
        /**
         * 公司类型
         */
        private java.lang.String companyType;
        /**
         * 自定义企业类型名称
         */
        private java.lang.String customCompanyType;
        /**
         * 注册地址
         */
        private java.lang.String registerAddress;
        /**
         * 注册资本
         */
        private java.math.BigDecimal registerCapital;
        /**
         * 配资区域
         */
        private List<CityVo> providerAreas;
        /**
         * 最小配资额度
         */
        private java.math.BigDecimal providerMinAmount;
        /**
         * 最大配资额度
         */
        private java.math.BigDecimal providerMaxAmount;
        /**
         * 配资级别
         */
        private com.alibaba.fastjson.JSONObject providerLevel;
        /**
         * 最小配资成本
         */
        private java.math.BigDecimal providerMinCost;
        /**
         * 最大配资成本
         */
        private java.math.BigDecimal providerMaxCost;
        /**
         * 服务费用
         */
        private java.math.BigDecimal providerFee;
        /**
         * 配资最短期限
         */
        private java.math.BigDecimal providerMinMonth;
        /**
         * 配资最长期限
         */
        private java.math.BigDecimal providerMaxMonth;
        /**
         * 配资模式
         */
        private java.lang.String providerPattern;
        /**
         * 资产包要求
         */
        private java.lang.String assetRequire;
        /**
         * 描述
         */
        private java.lang.String descrip;
        /**
         * 配资比例
         */
        private java.math.BigDecimal scale;
        /**
         * 资金服务商雷迅给
         */
        private java.lang.String fundType;
        /**
         * 联系方式（电话）
         */
        private java.lang.String contactPhone;
        /**
         * 联系人
         */
        private java.lang.String contactPerson;
        /**
         * 单笔配资额度开始
         */
        private java.math.BigDecimal singleMinAmount;
        /**
         * 单笔配资额度结束
         */
        private java.math.BigDecimal singleMaxAmount;
        /**
         * 年化收益率开始
         */
        private java.lang.String annuaReturnMin;
        /**
         * 年化收益率结束
         */
        private java.lang.String annuaReturnMax;
        /**
         * 其他费用
         */
        private java.lang.String otherFee;
        /**
         * 标的类型
         */
        private java.lang.String assetType;

    }

    @Data
    public static class UpdateReq extends RequestModel {
        @NotNull
        private Integer applyId;
        /**
         * 公司名称
         */
        private java.lang.String companyName;
        /**
         * 公司类型
         */
        private java.lang.String companyType;
        /**
         * 注册地址
         */
        private java.lang.String registerAddress;
        /**
         * 注册资本
         */
        private java.math.BigDecimal registerCapital;
        /**
         * 配资区域
         */
        private List<CityVo> providerAreas;
        /**
         * 最小配资额度
         */
        private java.math.BigDecimal providerMinAmount;
        /**
         * 最大配资额度
         */
        private java.math.BigDecimal providerMaxAmount;
        /**
         * 配资级别
         */
        private com.alibaba.fastjson.JSONObject providerLevel;
        /**
         * 最小配资成本
         */
        private java.math.BigDecimal providerMinCost;
        /**
         * 最大配资成本
         */
        private java.math.BigDecimal providerMaxCost;
        /**
         * 服务费用
         */
        private java.math.BigDecimal providerFee;
        /**
         * 配资最短期限
         */
        private java.math.BigDecimal providerMinMonth;
        /**
         * 配资最长期限
         */
        private java.math.BigDecimal providerMaxMonth;
        /**
         * 配资模式
         */
        private java.lang.String providerPattern;
        /**
         * 资产包要求
         */
        private java.lang.String assetRequire;
        /**
         * 描述
         */
        private java.lang.String descrip;
        /**
         * 开户人员
         */
        private java.lang.Integer openAccountOperatorId;
        /**
         * 业务对接人
         */
        private java.lang.Integer businessOperatorId;
        /**
         * 联系方式（电话）
         */
        private java.lang.String contactPhone;
        /**
         * 联系人
         */
        private java.lang.String contactPerson;
        /**
         * 单笔配资额度开始
         */
        private java.math.BigDecimal singleMinAmount;
        /**
         * 单笔配资额度结束
         */
        private java.math.BigDecimal singleMaxAmount;
        /**
         * 年化收益率开始
         */
        private java.lang.String annuaReturnMin;
        /**
         * 年化收益率结束
         */
        private java.lang.String annuaReturnMax;
        /**
         * 其他费用
         */
        private java.lang.String otherFee;
        /**
         * 标的类型
         */
        private java.lang.String assetType;

        private Integer operatorId;
        /**
         * 审核备注
         */
        private String reason;

    }
}
