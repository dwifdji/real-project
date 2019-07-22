package com._360pai.core.facade.account.resp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xdrodger
 * @Title: PersonaDetailResp
 * @ProjectName zeus-parent
 * @Description:
 * @date 01/09/2018 10:59
 */
public class PersonaDetailResp implements Serializable {
    private Integer personaId;
    /**
     * 客户类型（10 个人 20 公司）
     */
    private String userType = "10";
    /**
     * 用户状态 10 电话空号、20 电话无人接听、30 不愿意合作、40 已建立沟通、50 已展开合作、60 接听电话被拒绝
     */
    private String userStatus = "10";
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人手机号
     */
    private String contactPhone;
    /**
     * 联系人微信
     */
    private String contactWechat;
    /**
     * 职务
     */
    private String title;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 公司负责人
     */
    private String companyDirector;
    /**
     * 公司地址
     */
    private String companyAddress;
    /**
     * 业务区域
     */
    private List<Province> companyBusinessAreas;
    /**
     * 企业类型 10 民营 20 合资 30 国有企业 40 银行 50 个人
     */
    private String companyType = "10";
    /**
     * 录入人员
     */
    private String dataEntryStaff;
    /**
     * 客户类型（10 竞买人 20 资产方 30 处置方 40 中介方 50 联拍机构 60 配资机构）
     */
    private List<String> customerTypes;
    /**
     * 备注
     */
    private String memo;

    private PersonaDetailResp.PersonaAssetPartyResp personaAssetParty;

    private PersonaDetailResp.PersonaBidderResp personaBidder;

    private PersonaDetailResp.PersonaDisposalPartyResp personaDisposalParty;

    private PersonaDetailResp.PersonaFundingAgencyResp personaFundingAgency;

    private PersonaDetailResp.PersonaIntermediaryOrganResp personaIntermediaryOrgan;

    private PersonaDetailResp.PersonaUnionAuctionAgencyResp personaUnionAuctionAgency;

    public Integer getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactWechat() {
        return contactWechat;
    }

    public void setContactWechat(String contactWechat) {
        this.contactWechat = contactWechat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDirector() {
        return companyDirector;
    }

    public void setCompanyDirector(String companyDirector) {
        this.companyDirector = companyDirector;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public List<Province> getCompanyBusinessAreas() {
        return companyBusinessAreas;
    }

    public void setCompanyBusinessAreas(List<Province> companyBusinessAreas) {
        this.companyBusinessAreas = companyBusinessAreas;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getDataEntryStaff() {
        return dataEntryStaff;
    }

    public void setDataEntryStaff(String dataEntryStaff) {
        this.dataEntryStaff = dataEntryStaff;
    }

    public List<String> getCustomerTypes() {
        return customerTypes;
    }

    public void setCustomerTypes(List<String> customerTypes) {
        this.customerTypes = customerTypes;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public PersonaDetailResp.PersonaAssetPartyResp getPersonaAssetParty() {
        return personaAssetParty;
    }

    public void setPersonaAssetParty(PersonaDetailResp.PersonaAssetPartyResp personaAssetParty) {
        this.personaAssetParty = personaAssetParty;
    }

    public PersonaDetailResp.PersonaBidderResp getPersonaBidder() {
        return personaBidder;
    }

    public void setPersonaBidder(PersonaDetailResp.PersonaBidderResp personaBidder) {
        this.personaBidder = personaBidder;
    }

    public PersonaDetailResp.PersonaDisposalPartyResp getPersonaDisposalParty() {
        return personaDisposalParty;
    }

    public void setPersonaDisposalParty(PersonaDetailResp.PersonaDisposalPartyResp personaDisposalParty) {
        this.personaDisposalParty = personaDisposalParty;
    }

    public PersonaDetailResp.PersonaFundingAgencyResp getPersonaFundingAgency() {
        return personaFundingAgency;
    }

    public void setPersonaFundingAgency(PersonaDetailResp.PersonaFundingAgencyResp personaFundingAgency) {
        this.personaFundingAgency = personaFundingAgency;
    }

    public PersonaDetailResp.PersonaIntermediaryOrganResp getPersonaIntermediaryOrgan() {
        return personaIntermediaryOrgan;
    }

    public void setPersonaIntermediaryOrgan(PersonaDetailResp.PersonaIntermediaryOrganResp personaIntermediaryOrgan) {
        this.personaIntermediaryOrgan = personaIntermediaryOrgan;
    }

    public PersonaDetailResp.PersonaUnionAuctionAgencyResp getPersonaUnionAuctionAgency() {
        return personaUnionAuctionAgency;
    }

    public void setPersonaUnionAuctionAgency(PersonaDetailResp.PersonaUnionAuctionAgencyResp personaUnionAuctionAgency) {
        this.personaUnionAuctionAgency = personaUnionAuctionAgency;
    }

    public static class PersonaAssetPartyResp implements Serializable {
        private Integer id;
        private Integer personaId;
        /**
         * 持有资产类型（10 债权 20 物权 30 债权+物权）
         */
        private String assetType = "10";
        /**
         * 资产体量
         */
        private BigDecimal assetVolume;
        /**
         * 资产分布区域，城市名，逗号分隔
         */
        private List<Province> assetDistributionAreas;
        /**
         * 上年度不良资产处置规模
         */
        private BigDecimal lastYearDisposalAssetVolume;
        /**
         * 资产的平均价格区间（10 1000万以下 20 1000万到5000万 30 5000万到1亿 40 1亿以上）
         */
        private List<String> assetAveragePriceRanges;
        /**
         * 资产包来源（10 AMC 20 其他资管公司 30 当地政府 40 国有企业 50 银行）
         */
        private List<String> assetPackageSources;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPersonaId() {
            return personaId;
        }

        public void setPersonaId(Integer personaId) {
            this.personaId = personaId;
        }

        public String getAssetType() {
            return assetType;
        }

        public void setAssetType(String assetType) {
            this.assetType = assetType;
        }

        public BigDecimal getAssetVolume() {
            return assetVolume;
        }

        public void setAssetVolume(BigDecimal assetVolume) {
            this.assetVolume = assetVolume;
        }

        public List<Province> getAssetDistributionAreas() {
            return assetDistributionAreas;
        }

        public void setAssetDistributionAreas(List<Province> assetDistributionAreas) {
            this.assetDistributionAreas = assetDistributionAreas;
        }

        public BigDecimal getLastYearDisposalAssetVolume() {
            return lastYearDisposalAssetVolume;
        }

        public void setLastYearDisposalAssetVolume(BigDecimal lastYearDisposalAssetVolume) {
            this.lastYearDisposalAssetVolume = lastYearDisposalAssetVolume;
        }

        public List<String> getAssetAveragePriceRanges() {
            return assetAveragePriceRanges;
        }

        public void setAssetAveragePriceRanges(List<String> assetAveragePriceRanges) {
            this.assetAveragePriceRanges = assetAveragePriceRanges;
        }

        public List<String> getAssetPackageSources() {
            return assetPackageSources;
        }

        public void setAssetPackageSources(List<String> assetPackageSources) {
            this.assetPackageSources = assetPackageSources;
        }
    }

    public static class PersonaBidderResp implements Serializable {
        private Integer id;
        private Integer personaId;
        /**
         * 资金来源（10 自有资金 20 第三方融资）
         */
        private String fundSource = "10";
        /**
         * 是否需要配资（0 否 1 是）
         */
        private Boolean isNeedFunding = true;
        /**
         * 配资需求（10 优先 20 劣后 30 夹层）
         */
        private String fundingLevel = "10";
        /**
         * 配资可接受的成本
         */
        private BigDecimal acceptableFundingRate;
        /**
         * 购买资产类型（10 债权 20 物权 30 债权+物权）
         */
        private String assetType = "10";
        /**
         * 资产类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
         */
        private List<String> assetPropertyTypes;
        /**
         * 预期年化金收益
         */
        private BigDecimal prospectiveAnnualIncome;
        /**
         * 尽职调查（10 自行 20 第三方）
         */
        private String responsibleInvestigation = "10";
        /**
         * 处置调查（10 自行 20 第三方）
         */
        private String dispoalInvestigation = "10";
        /**
         * 回款周期（月）
         */
        private BigDecimal returnPeriod;
        /**
         * 今年还可投金额
         */
        private BigDecimal remainInvestAmount;
        /**
         * 目前持有的资产状况
         */
        private String currentHoldAssetStatus;
        /**
         * 今年主要的收购的计划
         */
        private String annualPurchasePlan;
        /**
         * 投资区域
         */
        private List<Province> investmentAreas;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPersonaId() {
            return personaId;
        }

        public void setPersonaId(Integer personaId) {
            this.personaId = personaId;
        }

        public String getFundSource() {
            return fundSource;
        }

        public void setFundSource(String fundSource) {
            this.fundSource = fundSource;
        }

        public Boolean getNeedFunding() {
            return isNeedFunding;
        }

        public void setNeedFunding(Boolean needFunding) {
            isNeedFunding = needFunding;
        }

        public String getFundingLevel() {
            return fundingLevel;
        }

        public void setFundingLevel(String fundingLevel) {
            this.fundingLevel = fundingLevel;
        }

        public BigDecimal getAcceptableFundingRate() {
            return acceptableFundingRate;
        }

        public void setAcceptableFundingRate(BigDecimal acceptableFundingRate) {
            this.acceptableFundingRate = acceptableFundingRate;
        }

        public String getAssetType() {
            return assetType;
        }

        public void setAssetType(String assetType) {
            this.assetType = assetType;
        }

        public List<String> getAssetPropertyTypes() {
            return assetPropertyTypes;
        }

        public void setAssetPropertyTypes(List<String> assetPropertyTypes) {
            this.assetPropertyTypes = assetPropertyTypes;
        }

        public BigDecimal getProspectiveAnnualIncome() {
            return prospectiveAnnualIncome;
        }

        public void setProspectiveAnnualIncome(BigDecimal prospectiveAnnualIncome) {
            this.prospectiveAnnualIncome = prospectiveAnnualIncome;
        }

        public String getResponsibleInvestigation() {
            return responsibleInvestigation;
        }

        public void setResponsibleInvestigation(String responsibleInvestigation) {
            this.responsibleInvestigation = responsibleInvestigation;
        }

        public String getDispoalInvestigation() {
            return dispoalInvestigation;
        }

        public void setDispoalInvestigation(String dispoalInvestigation) {
            this.dispoalInvestigation = dispoalInvestigation;
        }

        public BigDecimal getReturnPeriod() {
            return returnPeriod;
        }

        public void setReturnPeriod(BigDecimal returnPeriod) {
            this.returnPeriod = returnPeriod;
        }

        public BigDecimal getRemainInvestAmount() {
            return remainInvestAmount;
        }

        public void setRemainInvestAmount(BigDecimal remainInvestAmount) {
            this.remainInvestAmount = remainInvestAmount;
        }

        public String getCurrentHoldAssetStatus() {
            return currentHoldAssetStatus;
        }

        public void setCurrentHoldAssetStatus(String currentHoldAssetStatus) {
            this.currentHoldAssetStatus = currentHoldAssetStatus;
        }

        public String getAnnualPurchasePlan() {
            return annualPurchasePlan;
        }

        public void setAnnualPurchasePlan(String annualPurchasePlan) {
            this.annualPurchasePlan = annualPurchasePlan;
        }

        public List<Province> getInvestmentAreas() {
            return investmentAreas;
        }

        public void setInvestmentAreas(List<Province> investmentAreas) {
            this.investmentAreas = investmentAreas;
        }
    }

    public static class PersonaDisposalPartyResp implements Serializable {
        private Integer id;
        private Integer personaId;
        /**
         * 擅长业务类型（10 评估 20 尽职调查 30 司法处置 40 执行处置 50 清房 60 催收 70 查找资产线索）
         */
        private String businessType = "10";
        /**
         * 擅长业务区域，城市逗号分隔
         */
        private List<Province> businessAreas;
        /**
         * 熟悉的法院或执行庭
         */
        private String familiarCourt;
        /**
         * 已完成处置的规模
         */
        private BigDecimal completeDisposalVolume;
        /**
         * 正在处置的规模
         */
        private BigDecimal ongoingDisposalVolume;
        /**
         * 处置费用区间开始
         */
        private BigDecimal disposalCostRangeStart;
        /**
         * 处置费用区间结束
         */
        private BigDecimal disposalCostRangeEnd;
        /**
         * 尽调费用区间开始
         */
        private BigDecimal dueCostRangeStart;
        /**
         * 尽调费用区间结束
         */
        private BigDecimal dueCostRangeEnd;
        /**
         * 尽职调查周期（单位月）
         */
        private BigDecimal responsibleInvestigationPeriod;
        /**
         * 团队人数
         */
        private Integer teamNumber;
        /**
         * 处置周期（单位月）
         */
        private BigDecimal disposalPeriod;
        /**
         * 平均处置周期区间开始（单位月）
         */
        private BigDecimal averageDisposalPeriodStart;
        /**
         * 平均处置周期区间结束（单位月）
         */
        private BigDecimal averageDisposalPeriodEnd;
        /**
         * 是否会参与处置标的的投资
         */
        private Boolean isGoingToInvestDisposalAsset = true;
        /**
         * 是否做全风险代理
         */
        private Boolean isDoFullyRiskAgency = true;
        /**
         * 标的类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
         */
        private List<String> assetPropertyTypes;
        /**
         * 口估
         */
        private Boolean oralEvaluate;
        /**
         * 口估费用
         */
        private BigDecimal oralEvaluateFee;
        /**
         * 评估报告
         */
        private Boolean evaluateReport;
        /**
         * 评估报告费用
         */
        private BigDecimal evaluateReportFee;
        /**
         * 评估周期开始
         */
        private BigDecimal evaluateTermStart;
        /**
         * 评估周期结束
         */
        private BigDecimal evaluateTermEnd;
        /**
         * 评估机构等级
         */
        private String evaluateAgencyLevel;
        /**
         * 催收方式
         */
        private String collectionMode;
        /**
         * 催收费用开始
         */
        private BigDecimal collectionFeeStart;
        /**
         * 催收费用结束
         */
        private BigDecimal collectionFeeEnd;
        /**
         * 催收周期
         */
        private BigDecimal collectionPeriod;
        /**
         * 资产线索
         */
        private String assetClues;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPersonaId() {
            return personaId;
        }

        public void setPersonaId(Integer personaId) {
            this.personaId = personaId;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public List<Province> getBusinessAreas() {
            return businessAreas;
        }

        public void setBusinessAreas(List<Province> businessAreas) {
            this.businessAreas = businessAreas;
        }

        public String getFamiliarCourt() {
            return familiarCourt;
        }

        public void setFamiliarCourt(String familiarCourt) {
            this.familiarCourt = familiarCourt;
        }

        public BigDecimal getCompleteDisposalVolume() {
            return completeDisposalVolume;
        }

        public void setCompleteDisposalVolume(BigDecimal completeDisposalVolume) {
            this.completeDisposalVolume = completeDisposalVolume;
        }

        public BigDecimal getOngoingDisposalVolume() {
            return ongoingDisposalVolume;
        }

        public void setOngoingDisposalVolume(BigDecimal ongoingDisposalVolume) {
            this.ongoingDisposalVolume = ongoingDisposalVolume;
        }

        public BigDecimal getDisposalCostRangeStart() {
            return disposalCostRangeStart;
        }

        public void setDisposalCostRangeStart(BigDecimal disposalCostRangeStart) {
            this.disposalCostRangeStart = disposalCostRangeStart;
        }

        public BigDecimal getDisposalCostRangeEnd() {
            return disposalCostRangeEnd;
        }

        public void setDisposalCostRangeEnd(BigDecimal disposalCostRangeEnd) {
            this.disposalCostRangeEnd = disposalCostRangeEnd;
        }

        public BigDecimal getDueCostRangeStart() {
            return dueCostRangeStart;
        }

        public void setDueCostRangeStart(BigDecimal dueCostRangeStart) {
            this.dueCostRangeStart = dueCostRangeStart;
        }

        public BigDecimal getDueCostRangeEnd() {
            return dueCostRangeEnd;
        }

        public void setDueCostRangeEnd(BigDecimal dueCostRangeEnd) {
            this.dueCostRangeEnd = dueCostRangeEnd;
        }

        public BigDecimal getResponsibleInvestigationPeriod() {
            return responsibleInvestigationPeriod;
        }

        public void setResponsibleInvestigationPeriod(BigDecimal responsibleInvestigationPeriod) {
            this.responsibleInvestigationPeriod = responsibleInvestigationPeriod;
        }

        public Integer getTeamNumber() {
            return teamNumber;
        }

        public void setTeamNumber(Integer teamNumber) {
            this.teamNumber = teamNumber;
        }

        public BigDecimal getDisposalPeriod() {
            return disposalPeriod;
        }

        public void setDisposalPeriod(BigDecimal disposalPeriod) {
            this.disposalPeriod = disposalPeriod;
        }

        public BigDecimal getAverageDisposalPeriodStart() {
            return averageDisposalPeriodStart;
        }

        public void setAverageDisposalPeriodStart(BigDecimal averageDisposalPeriodStart) {
            this.averageDisposalPeriodStart = averageDisposalPeriodStart;
        }

        public BigDecimal getAverageDisposalPeriodEnd() {
            return averageDisposalPeriodEnd;
        }

        public void setAverageDisposalPeriodEnd(BigDecimal averageDisposalPeriodEnd) {
            this.averageDisposalPeriodEnd = averageDisposalPeriodEnd;
        }

        public Boolean getIsGoingToInvestDisposalAsset(){
            return isGoingToInvestDisposalAsset;
        }

        public void setIsGoingToInvestDisposalAsset(Boolean isGoingToInvestDisposalAsset){
            this.isGoingToInvestDisposalAsset = isGoingToInvestDisposalAsset;
        }
        public Boolean getIsDoFullyRiskAgency() {
            return isDoFullyRiskAgency;
        }

        public void setIsDoFullyRiskAgency(Boolean doFullyRiskAgency) {
            isDoFullyRiskAgency = doFullyRiskAgency;
        }

        public List<String> getAssetPropertyTypes() {
            return assetPropertyTypes;
        }

        public void setAssetPropertyTypes(List<String> assetPropertyTypes) {
            this.assetPropertyTypes = assetPropertyTypes;
        }

        public Boolean getOralEvaluate() {
            return oralEvaluate;
        }

        public void setOralEvaluate(Boolean oralEvaluate) {
            this.oralEvaluate = oralEvaluate;
        }

        public BigDecimal getOralEvaluateFee() {
            return oralEvaluateFee;
        }

        public void setOralEvaluateFee(BigDecimal oralEvaluateFee) {
            this.oralEvaluateFee = oralEvaluateFee;
        }

        public Boolean getEvaluateReport() {
            return evaluateReport;
        }

        public void setEvaluateReport(Boolean evaluateReport) {
            this.evaluateReport = evaluateReport;
        }

        public BigDecimal getEvaluateReportFee() {
            return evaluateReportFee;
        }

        public void setEvaluateReportFee(BigDecimal evaluateReportFee) {
            this.evaluateReportFee = evaluateReportFee;
        }

        public BigDecimal getEvaluateTermStart() {
            return evaluateTermStart;
        }

        public void setEvaluateTermStart(BigDecimal evaluateTermStart) {
            this.evaluateTermStart = evaluateTermStart;
        }

        public BigDecimal getEvaluateTermEnd() {
            return evaluateTermEnd;
        }

        public void setEvaluateTermEnd(BigDecimal evaluateTermEnd) {
            this.evaluateTermEnd = evaluateTermEnd;
        }

        public String getEvaluateAgencyLevel() {
            return evaluateAgencyLevel;
        }

        public void setEvaluateAgencyLevel(String evaluateAgencyLevel) {
            this.evaluateAgencyLevel = evaluateAgencyLevel;
        }

        public String getCollectionMode() {
            return collectionMode;
        }

        public void setCollectionMode(String collectionMode) {
            this.collectionMode = collectionMode;
        }

        public BigDecimal getCollectionFeeStart() {
            return collectionFeeStart;
        }

        public void setCollectionFeeStart(BigDecimal collectionFeeStart) {
            this.collectionFeeStart = collectionFeeStart;
        }

        public BigDecimal getCollectionFeeEnd() {
            return collectionFeeEnd;
        }

        public void setCollectionFeeEnd(BigDecimal collectionFeeEnd) {
            this.collectionFeeEnd = collectionFeeEnd;
        }

        public BigDecimal getCollectionPeriod() {
            return collectionPeriod;
        }

        public void setCollectionPeriod(BigDecimal collectionPeriod) {
            this.collectionPeriod = collectionPeriod;
        }

        public String getAssetClues() {
            return assetClues;
        }

        public void setAssetClues(String assetClues) {
            this.assetClues = assetClues;
        }
    }

    public static class PersonaFundingAgencyResp implements Serializable {
        private Integer id;
        private Integer personaId;
        /**
         * 资金来源
         */
        private String fundSource;
        /**
         * 配资风控要求
         */
        private String fundingRiskManagementDemand;
        /**
         * 配资标的类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
         */
        private List<String> assetPropertyTypes;
        /**
         * 配资区域，城市逗号分隔
         */
        private List<Province> businessAreas;
        /**
         * 配资级别（10 优先 20 劣后 30 夹层）
         */
        private List<String> fundingLevels;
        /**
         * 配资级别优先比例
         */
        private BigDecimal fundingLevelPriorRate;
        /**
         * 配资级别劣后比例
         */
        private BigDecimal fundingLevelInferiorRate;
        /**
         * 配资级别夹层比例
         */
        private BigDecimal fundingLevelInterbedRate;
        /**
         * 配资比列
         */
        private BigDecimal fundingPercent;
        /**
         * 多快配到资（配资时间，单位天）
         */
        private BigDecimal fundingTime;
        /**
         * 配资期限（单位月）
         */
        private BigDecimal fundingTerm;
        /**
         * 配资期限开始（单位月）
         */
        private BigDecimal fundingTermStart;
        /**
         * 配资期限结束（单位月）
         */
        private BigDecimal fundingTermEnd;
        /**
         * 配资收益要求
         */
        private BigDecimal fundingIncomeDemand;
        /**
         * 配置成本开始
         */
        private BigDecimal fundingRateStart;
        /**
         * 配置成本结束
         */
        private BigDecimal fundingRateEnd;
        /**
         * 是否参与处理（0 否 1 是）
         */
        private Boolean isPartakeDispose = true;
        /**
         * 对需求方特殊要求
         */
        private String specialRespuirementOnDemandSide;
        /**
         * 今年还可配资的体量
         */
        private BigDecimal remainingFundingVolume;
        /**
         * 其它收费
         */
        private BigDecimal otherFee;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPersonaId() {
            return personaId;
        }

        public void setPersonaId(Integer personaId) {
            this.personaId = personaId;
        }

        public String getFundSource() {
            return fundSource;
        }

        public void setFundSource(String fundSource) {
            this.fundSource = fundSource;
        }

        public String getFundingRiskManagementDemand() {
            return fundingRiskManagementDemand;
        }

        public void setFundingRiskManagementDemand(String fundingRiskManagementDemand) {
            this.fundingRiskManagementDemand = fundingRiskManagementDemand;
        }

        public List<String> getAssetPropertyTypes() {
            return assetPropertyTypes;
        }

        public void setAssetPropertyTypes(List<String> assetPropertyTypes) {
            this.assetPropertyTypes = assetPropertyTypes;
        }

        public List<Province> getBusinessAreas() {
            return businessAreas;
        }

        public void setBusinessAreas(List<Province> businessAreas) {
            this.businessAreas = businessAreas;
        }

        public List<String> getFundingLevels() {
            return fundingLevels;
        }

        public void setFundingLevels(List<String> fundingLevels) {
            this.fundingLevels = fundingLevels;
        }

        public BigDecimal getFundingLevelPriorRate() {
            return fundingLevelPriorRate;
        }

        public void setFundingLevelPriorRate(BigDecimal fundingLevelPriorRate) {
            this.fundingLevelPriorRate = fundingLevelPriorRate;
        }

        public BigDecimal getFundingLevelInferiorRate() {
            return fundingLevelInferiorRate;
        }

        public void setFundingLevelInferiorRate(BigDecimal fundingLevelInferiorRate) {
            this.fundingLevelInferiorRate = fundingLevelInferiorRate;
        }

        public BigDecimal getFundingLevelInterbedRate() {
            return fundingLevelInterbedRate;
        }

        public void setFundingLevelInterbedRate(BigDecimal fundingLevelInterbedRate) {
            this.fundingLevelInterbedRate = fundingLevelInterbedRate;
        }

        public BigDecimal getFundingPercent() {
            return fundingPercent;
        }

        public void setFundingPercent(BigDecimal fundingPercent) {
            this.fundingPercent = fundingPercent;
        }

        public BigDecimal getFundingTime() {
            return fundingTime;
        }

        public void setFundingTime(BigDecimal fundingTime) {
            this.fundingTime = fundingTime;
        }

        public BigDecimal getFundingTerm() {
            return fundingTerm;
        }

        public void setFundingTerm(BigDecimal fundingTerm) {
            this.fundingTerm = fundingTerm;
        }

        public BigDecimal getFundingIncomeDemand() {
            return fundingIncomeDemand;
        }

        public void setFundingIncomeDemand(BigDecimal fundingIncomeDemand) {
            this.fundingIncomeDemand = fundingIncomeDemand;
        }

        public BigDecimal getFundingTermStart() {
            return fundingTermStart;
        }

        public void setFundingTermStart(BigDecimal fundingTermStart) {
            this.fundingTermStart = fundingTermStart;
        }

        public BigDecimal getFundingTermEnd() {
            return fundingTermEnd;
        }

        public void setFundingTermEnd(BigDecimal fundingTermEnd) {
            this.fundingTermEnd = fundingTermEnd;
        }

        public BigDecimal getFundingRateStart() {
            return fundingRateStart;
        }

        public void setFundingRateStart(BigDecimal fundingRateStart) {
            this.fundingRateStart = fundingRateStart;
        }

        public BigDecimal getFundingRateEnd() {
            return fundingRateEnd;
        }

        public void setFundingRateEnd(BigDecimal fundingRateEnd) {
            this.fundingRateEnd = fundingRateEnd;
        }

        public Boolean getIsPartakeDispose() {
            return isPartakeDispose;
        }

        public void setIsPartakeDispose(Boolean partakeDispose) {
            isPartakeDispose = partakeDispose;
        }

        public String getSpecialRespuirementOnDemandSide() {
            return specialRespuirementOnDemandSide;
        }

        public void setSpecialRespuirementOnDemandSide(String specialRespuirementOnDemandSide) {
            this.specialRespuirementOnDemandSide = specialRespuirementOnDemandSide;
        }

        public BigDecimal getRemainingFundingVolume() {
            return remainingFundingVolume;
        }

        public void setRemainingFundingVolume(BigDecimal remainingFundingVolume) {
            this.remainingFundingVolume = remainingFundingVolume;
        }

        public BigDecimal getOtherFee() {
            return otherFee;
        }

        public void setOtherFee(BigDecimal otherFee) {
            this.otherFee = otherFee;
        }
    }

    public static class PersonaIntermediaryOrganResp implements Serializable {
        private Integer id;
        private Integer personaId;
        /**
         * 擅长业务类型（10 找买家 20 找卖家）
         */
        private String businessType = "10";
        /**
         * 擅长业务区域，城市逗号分隔
         */
        private List<Province> businessAreas;
        /**
         * 擅长寻找哪种资产类型客户，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
         */
        private List<String> assetPropertyTypes;
        /**
         * 客户需求类型（10 债权 20 物权 30 债权+物权）
         */
        private String assetType = "10";
        /**
         * 是否有成功案列（0 否 1 是）
         */
        private Boolean hasSuccessfulCase = true;
        /**
         * 是否愿意加盟店铺（0 否 1 是）
         */
        private Boolean isGoingToJoinShop = true;
        /**
         * 其他需求
         */
        private String otherDemand;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPersonaId() {
            return personaId;
        }

        public void setPersonaId(Integer personaId) {
            this.personaId = personaId;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public List<Province> getBusinessAreas() {
            return businessAreas;
        }

        public void setBusinessAreas(List<Province> businessAreas) {
            this.businessAreas = businessAreas;
        }

        public List<String> getAssetPropertyTypes() {
            return assetPropertyTypes;
        }

        public void setAssetPropertyTypes(List<String> assetPropertyTypes) {
            this.assetPropertyTypes = assetPropertyTypes;
        }

        public String getAssetType() {
            return assetType;
        }

        public void setAssetType(String assetType) {
            this.assetType = assetType;
        }

        public Boolean getHasSuccessfulCase() {
            return hasSuccessfulCase;
        }

        public void setHasSuccessfulCase(Boolean hasSuccessfulCase) {
            this.hasSuccessfulCase = hasSuccessfulCase;
        }

        public Boolean getIsGoingToJoinShop() {
            return isGoingToJoinShop;
        }

        public void setIsGoingToJoinShop(Boolean goingToJoinShop) {
            isGoingToJoinShop = goingToJoinShop;
        }

        public String getOtherDemand() {
            return otherDemand;
        }

        public void setOtherDemand(String otherDemand) {
            this.otherDemand = otherDemand;
        }
    }

    public static class PersonaUnionAuctionAgencyResp implements Serializable {
        private Integer id;
        private Integer personaId;
        /**
         * 擅长拍卖的类型（10 债权 20 物权 30 债权+物权）
         */
        private String assetType = "10";
        /**
         * 擅长拍卖的标的类型，逗号分隔( 00 住宅 10 商业 20 厂房 30 设备物资 40 商铺 50 土地 60 保证信用类 70 租赁权 80 股权 90 车辆)
         */
        private List<String> assetPropertyTypes;
        /**
         * 擅长拍卖区域，城市逗号分隔
         */
        private List<Province> businessAreas;
        /**
         * 投资人数量
         */
        private Integer investorNumber;
        /**
         * 竞买人数量
         */
        private Integer bidderNumber;
        /**
         * 委托人数量
         */
        private Integer consignorNumber;
        /**
         * 上年度不良资产处置规模
         */
        private BigDecimal lastYearDisposalAssetVolume;
        /**
         * 上年度总成交额
         */
        private BigDecimal lastYearTotalDealVolume;
        /**
         * 是否有线上拍卖经验（0 否 1 是）
         */
        private Boolean hasOnlineAuctionExperience = true;
        /**
         * 是否有长期合作服务商（0 否 1 是）
         */
        private Boolean hasFixedCooperateServiceProvider = true;
        /**
         * 其它业务需求
         */
        private String otherDemand;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPersonaId() {
            return personaId;
        }

        public void setPersonaId(Integer personaId) {
            this.personaId = personaId;
        }

        public String getAssetType() {
            return assetType;
        }

        public void setAssetType(String assetType) {
            this.assetType = assetType;
        }

        public List<String> getAssetPropertyTypes() {
            return assetPropertyTypes;
        }

        public void setAssetPropertyTypes(List<String> assetPropertyTypes) {
            this.assetPropertyTypes = assetPropertyTypes;
        }

        public List<Province> getBusinessAreas() {
            return businessAreas;
        }

        public void setBusinessAreas(List<Province> businessAreas) {
            this.businessAreas = businessAreas;
        }

        public Integer getInvestorNumber() {
            return investorNumber;
        }

        public void setInvestorNumber(Integer investorNumber) {
            this.investorNumber = investorNumber;
        }

        public Integer getBidderNumber() {
            return bidderNumber;
        }

        public void setBidderNumber(Integer bidderNumber) {
            this.bidderNumber = bidderNumber;
        }

        public Integer getConsignorNumber() {
            return consignorNumber;
        }

        public void setConsignorNumber(Integer consignorNumber) {
            this.consignorNumber = consignorNumber;
        }

        public BigDecimal getLastYearDisposalAssetVolume() {
            return lastYearDisposalAssetVolume;
        }

        public void setLastYearDisposalAssetVolume(BigDecimal lastYearDisposalAssetVolume) {
            this.lastYearDisposalAssetVolume = lastYearDisposalAssetVolume;
        }

        public BigDecimal getLastYearTotalDealVolume() {
            return lastYearTotalDealVolume;
        }

        public void setLastYearTotalDealVolume(BigDecimal lastYearTotalDealVolume) {
            this.lastYearTotalDealVolume = lastYearTotalDealVolume;
        }

        public Boolean getHasOnlineAuctionExperience() {
            return hasOnlineAuctionExperience;
        }

        public void setHasOnlineAuctionExperience(Boolean hasOnlineAuctionExperience) {
            this.hasOnlineAuctionExperience = hasOnlineAuctionExperience;
        }

        public Boolean getHasFixedCooperateServiceProvider() {
            return hasFixedCooperateServiceProvider;
        }

        public void setHasFixedCooperateServiceProvider(Boolean hasFixedCooperateServiceProvider) {
            this.hasFixedCooperateServiceProvider = hasFixedCooperateServiceProvider;
        }

        public String getOtherDemand() {
            return otherDemand;
        }

        public void setOtherDemand(String otherDemand) {
            this.otherDemand = otherDemand;
        }
    }

    public static class Province implements Serializable {
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
