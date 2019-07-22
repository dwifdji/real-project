package com._360pai.core.vo.asset;

import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.AssetEnum;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class AssetLeaseDataVO implements Serializable {


    /**
     * 状态
     */
    private Integer assetId;
    /**
     * 状态
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 标的编号
     */
    private String code;
    /**
     * 标的名称
     */
    private String name;
    /**
     * 标的所在省id
     */
    private String provinceId;
    /**
     * 标的所在地
     */
    private String cityId;
    /**
     * 标的所在区id
     */
    private String areaId;
    /**
     * 委托人ID
     */
    private Integer partyId;
    /**
     * 标的物可能由多个
     */
    private Integer quantity;
    /**
     * 剩余个数
     */
    private Integer remain;
    /**
     * 标的物类型
     */
    private String assetType;
    /**
     * 备注
     */
    private String remark;
    /**
     * 上传机构ID
     */
    private Integer agencyId;
    /**
     * 保留价
     */
    private String reservePrice;
    /**
     * 描述文件
     */
    private String descriptionDoc;
    /**
     * 标的详情
     */
    private String detail;
    /**
     * 联系人
     */
    private String contactName;
    /**
     * 联系人手机号
     */
    private String contactPhone;
    /**
     * 联系人QQ
     */
    private String contactQq;
    /**
     * 包含图片的JSON数据
     */
    private com.alibaba.fastjson.JSONObject extra;
    /**
     * 期望拍卖模式
     */
    private String expectedMode;
    /**
     * 参考价
     */
    private java.math.BigDecimal refPrice;
    /**
     * 起拍价
     */
    private java.math.BigDecimal startingPrice;
    /**
     * 期望拍卖开始时间
     */
    private Date beginAt;
    /**
     * 期望拍卖结束时间
     */
    private Date endAt;
    /**
     * 标的属性ID
     */
    private Integer propertyId;
    /**
     * 标的类型ID(模板ID)
     */
    private Integer categoryId;
    /**
     * 标的物选项
     */
    private String options;
    /**
     * 交接天数 仅作为 属性
     */
    private Integer handoverDays;
    /**
     * 支付天数 仅作为 属性
     */
    private Integer payDays;
    /**
     * 特别说明
     */
    private String specialDetail;
    /**
     * 银行账户名
     */
    private String bankAccountName;
    /**
     * 银行账户号码
     */
    private String bankAccountNumber;
    /**
     * 银行ID
     */
    private Integer bankId;
    /**
     * 用户手动输入的银行名称
     */
    private String bankName;
    /**
     * 尽调报告 金额
     */
    private java.math.BigDecimal tuneReport;
    /**
     * 尽调报告url
     */
    private com.alibaba.fastjson.JSONObject tuneReportUrl;
    /**
     * 尽调报告是否授权  false未授权  true授权
     */
    private Integer tuneReportAuthorization;
    /**
     * 评估报告 1:基础评估 2:完整评估
     */
    private java.math.BigDecimal evaluationReport;
    /**
     * 评估报告url
     */
    private com.alibaba.fastjson.JSONObject evaluationReportUrl;
    /**
     * 债券转让协议url
     */
    private String claimsTransferUrl;
    /**
     * 转让公告url
     */
    private String transferAnnouncementUrl;
    /**
     * 更新时间
     */
    private Date updatedAt;
    /**
     * 线上 线下操作 0:线下操作 1:线上操作
     */
    private Integer onlined;
    /**
     * 业务类型 0:拍卖 1:配置乐 2:处置服务商
     */
    private Integer busType;
    /**
     * 债权本金
     */
    private java.math.BigDecimal debtPrincipal;
    /**
     * 债权利息
     */
    private java.math.BigDecimal debtInterest;
    /**
     * 上拍来源：0 平台 1 机构
     */
    private String comeFrom;
    /**
     * 标的连拍状态 0 联拍 1不联拍
     */
    private Integer jointStatus;
    /**
     * 标识老数据 1:true老数据  0:false新数据
     */
    private Boolean oldData;
    /**
     * spvID  初始值为0
     */
    private Integer spvId;
    /**
     * 删除标志（0 否 1 是）
     */
    private Boolean deleteFlag;
    /**
     * 标的小状态
     */
    private String subStatus;
    /**
     * 租赁类型
     */
    private String leaseType;
    /**
     * 租赁类型描述
     */
    private String leaseTypeDesc;
    /**
     * 区域类型
     */
    private String areaType;
    /**
     * 区域类型描述
     */
    private String areaTypeDesc;
    /**
     * 租赁面积
     */
    private Integer leaseArea;
    /**
     * 租赁间数
     */
    private Integer leaseRoomNumber;
    /**
     * 出租物品地址
     */
    private String leaseAddress;
    /**
     * 权力情况
     */
    private String powerSituation;
    /**
     * 法律用途
     */
    private String legalPurposes;
    /**
     * 现状描述
     */
    private String currentDesc;
    /**
     * 出租房名称
     */
    private String lessorName;
    /**
     * 出租方地址
     */
    private String lessorAddress;
    /**
     * 出租方联系人
     */
    private String lessorContact;
    /**
     * 出租房联系方式
     */
    private String lessorContactNumber;
    /**
     * 竞租方资格
     */
    private String bidderQualification;
    /**
     * 竞租方资格中文描述
     */
    private String bidderQualificationDesc;
    /**
     * 是否有房产证
     */
    private Boolean deedFlag;
    /**
     * 资料预审方式
     */
    private String preAuditMethod;
    /**
     * 资料预审方式描述
     */
    private String preAuditMethodDesc;
    /**
     * 原租户名称
     */
    private String originalTenantName;
    /**
     * 原租户联系方式
     */
    private String originalTenantNumber;
    /**
     * 优先资格
     */
    private String priorityQualification;
    /**
     * 经营能力
     */
    private String managementCapacity;
    /**
     * 注册地址
     */
    private String registeredAddress;
    /**
     * 现在经营业务
     */
    private String businessNow;
    /**
     * 租赁开始时间
     */
    private Date leaseStartTime;
    /**
     * 租赁结束时间
     */
    private Date leaseEndTime;
    /**
     * 出租用途类型约定
     */
    private String operatingType;
    /**
     * 出租用途类型约定描述
     */
    private String operatingTypeDesc;
    /**
     * 业态约定
     */
    private String businessAgreement;
    /**
     * 租金约定
     */
    private String rentAgreement;
    /**
     * 房屋结构约定
     */
    private String houseStructureAgreement;
    /**
     * 维护约定
     */
    private String maintainAgreement;
    /**
     * 安全约定
     */
    private String safetyAgreement;
    /**
     * 其他约定
     */
    private String lesseeOtherAgreement;
    /**
     * 征收约定
     */
    private String levyAgreement;
    /**
     * 续租约定
     */
    private String renewalAgreement;
    /**
     * 终止合同约定
     */
    private String terminationContractAgreement;
    /**
     * 争议处理约定
     */
    private String disputeResolutionAgreement;
    /**
     * 违约责任约定
     */
    private String defaultLiabilityAgreement;
    /**
     * 合同其他约定
     */
    private String contractOtherAgreement;
    /**
     * 出租房佣金比例
     */
    private String lessorCommissionRate;
    /**
     * 承租方佣金比例
     */
    private String lesseeCommissionRate;
    /**
     * 报名结束时间
     */
    private Date applyEndTime;
    /**
     * 审核结束时间
     */
    private Date approvalEndTime;
    /**
     * 租金年递增比例
     */
    private String annualIncrementRate;
    /**
     * 加价幅度
     */
    private BigDecimal increment;
    /**
     * 减价幅度
     */
    private BigDecimal reduction;
    /**
     * 当前价
     */
    private BigDecimal currentPrice;
    /**
     * 降价周期
     */
    private Integer reductionPeriod;
    /**
     * 预展时间
     */
    private Date previewAt;
    /**
     * 保证金
     */
    private BigDecimal deposit;
    /**
     * 资产亮点
     */
    private String brightSpot;
    /**
     * 服务机构id
     */
    private Integer serverAgencyId;

    /**
     * 租金支付约定
     */
    private String paymentCycle;

    /**
     * 租金支付约定描述
     */
    private String paymentCycleDesc;

    /**
     * 费用承担方
     */
    private String costBearer;
    /**
     * 费用承担方描述
     */
    private String costBearerDesc;
    /**
     * 镇id
     */
    private String townId;
    /**
     * 拍卖方式描述
     */
    private String expectedModeDesc;
    /**
     * 服务机构名称
     */
    private String serverAgencyName;
    /**
     * 区域数组JSON对象
     */
    private com.alibaba.fastjson.JSONObject assetLeaseArea;
    /**
     * 小状态描述
     */
    private String subStatusDesc;
    /**
     * 购买人姓名
     */
    private String buyerName;
    /**
     * 房屋结构类型
     */
    private String houseStructure;
    /**
     * 房屋结构类型
     */
    private String houseStructureDesc;
    /**
     * 租赁房编码
     */
    private String leaseCode;
    /**
     * 实际用途
     */
    private String actualUse;
    /**
     * 带看开始时间
     */
    private Date viewBeginTime;

    /**
     * 带看结束时间
     */
    private Date viewEndTime;

    public String getActualUse() {
        return actualUse;
    }

    public void setActualUse(String actualUse) {
        this.actualUse = actualUse;
    }

    public Date getViewBeginTime() {
        return viewBeginTime;
    }

    public void setViewBeginTime(Date viewBeginTime) {
        this.viewBeginTime = viewBeginTime;
    }

    public Date getViewEndTime() {
        return viewEndTime;
    }

    public void setViewEndTime(Date viewEndTime) {
        this.viewEndTime = viewEndTime;
    }

    public String getLeaseCode() {
        return leaseCode;
    }

    public void setLeaseCode(String leaseCode) {
        this.leaseCode = leaseCode;
    }

    public String getHouseStructureDesc() {
        return houseStructureDesc;
    }

    public String getHouseStructure() {
        return houseStructure;
    }

    public void setHouseStructure(String houseStructure) {
        this.houseStructure = houseStructure;
        this.houseStructureDesc = AssetEnum.HouseStructure.getValueByKey(houseStructure);
    }

    public String getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(String reservePrice) {
        this.reservePrice = reservePrice;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getSubStatusDesc() {
        return subStatusDesc;
    }

    public String getServerAgencyName() {
        return serverAgencyName;
    }

    public void setServerAgencyName(String serverAgencyName) {
        this.serverAgencyName = serverAgencyName;
    }

    public String getExpectedModeDesc() {
        return expectedModeDesc;
    }


    public JSONObject getAssetLeaseArea() {
        return assetLeaseArea;
    }

    public void setAssetLeaseArea(JSONObject assetLeaseArea) {
        this.assetLeaseArea = assetLeaseArea;
    }

    public String getPaymentCycleDesc() {
        return paymentCycleDesc;
    }

    public String getCostBearerDesc() {
        return costBearerDesc;
    }

    public String getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(String paymentCycle) {
        this.paymentCycle = paymentCycle;
        this.paymentCycleDesc = AssetEnum.PaymentCycle.getValueByKey(paymentCycle);
    }

    public String getCostBearer() {
        return costBearer;
    }

    public void setCostBearer(String costBearer) {
        this.costBearer = costBearer;
        this.costBearerDesc = AssetEnum.CostBearer.getValueByKey(costBearer);
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getBrightSpot() {
        return brightSpot;
    }

    public void setBrightSpot(String brightSpot) {
        this.brightSpot = brightSpot;
    }

    public Integer getServerAgencyId() {
        return serverAgencyId;
    }

    public void setServerAgencyId(Integer serverAgencyId) {
        this.serverAgencyId = serverAgencyId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }


    public String getDescriptionDoc() {
        return descriptionDoc;
    }

    public void setDescriptionDoc(String descriptionDoc) {
        this.descriptionDoc = descriptionDoc;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getContactQq() {
        return contactQq;
    }

    public void setContactQq(String contactQq) {
        this.contactQq = contactQq;
    }

    public JSONObject getExtra() {
        return extra;
    }

    public void setExtra(JSONObject extra) {
        this.extra = extra;
    }

    public String getExpectedMode() {
        return expectedMode;
    }

    public void setExpectedMode(String expectedMode) {
        this.expectedMode = expectedMode;
        this.expectedModeDesc = AssetEnum.ExpectedMode.getKeyByValue(expectedMode);
    }

    public BigDecimal getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(BigDecimal refPrice) {
        this.refPrice = refPrice;
    }

    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }


    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Integer getHandoverDays() {
        return handoverDays;
    }

    public void setHandoverDays(Integer handoverDays) {
        this.handoverDays = handoverDays;
    }

    public Integer getPayDays() {
        return payDays;
    }

    public void setPayDays(Integer payDays) {
        this.payDays = payDays;
    }

    public String getSpecialDetail() {
        return specialDetail;
    }

    public void setSpecialDetail(String specialDetail) {
        this.specialDetail = specialDetail;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getTuneReport() {
        return tuneReport;
    }

    public void setTuneReport(BigDecimal tuneReport) {
        this.tuneReport = tuneReport;
    }

    public JSONObject getTuneReportUrl() {
        return tuneReportUrl;
    }

    public void setTuneReportUrl(JSONObject tuneReportUrl) {
        this.tuneReportUrl = tuneReportUrl;
    }

    public Integer getTuneReportAuthorization() {
        return tuneReportAuthorization;
    }

    public void setTuneReportAuthorization(Integer tuneReportAuthorization) {
        this.tuneReportAuthorization = tuneReportAuthorization;
    }

    public BigDecimal getEvaluationReport() {
        return evaluationReport;
    }

    public void setEvaluationReport(BigDecimal evaluationReport) {
        this.evaluationReport = evaluationReport;
    }

    public JSONObject getEvaluationReportUrl() {
        return evaluationReportUrl;
    }

    public void setEvaluationReportUrl(JSONObject evaluationReportUrl) {
        this.evaluationReportUrl = evaluationReportUrl;
    }

    public String getClaimsTransferUrl() {
        return claimsTransferUrl;
    }

    public void setClaimsTransferUrl(String claimsTransferUrl) {
        this.claimsTransferUrl = claimsTransferUrl;
    }

    public String getTransferAnnouncementUrl() {
        return transferAnnouncementUrl;
    }

    public void setTransferAnnouncementUrl(String transferAnnouncementUrl) {
        this.transferAnnouncementUrl = transferAnnouncementUrl;
    }

    public Integer getOnlined() {
        return onlined;
    }

    public void setOnlined(Integer onlined) {
        this.onlined = onlined;
    }

    public Integer getBusType() {
        return busType;
    }

    public void setBusType(Integer busType) {
        this.busType = busType;
    }

    public BigDecimal getDebtPrincipal() {
        return debtPrincipal;
    }

    public void setDebtPrincipal(BigDecimal debtPrincipal) {
        this.debtPrincipal = debtPrincipal;
    }

    public BigDecimal getDebtInterest() {
        return debtInterest;
    }

    public void setDebtInterest(BigDecimal debtInterest) {
        this.debtInterest = debtInterest;
    }

    public String getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    public Integer getJointStatus() {
        return jointStatus;
    }

    public void setJointStatus(Integer jointStatus) {
        this.jointStatus = jointStatus;
    }

    public Boolean getOldData() {
        return oldData;
    }

    public void setOldData(Boolean oldData) {
        this.oldData = oldData;
    }

    public Integer getSpvId() {
        return spvId;
    }

    public void setSpvId(Integer spvId) {
        this.spvId = spvId;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
        this.subStatusDesc = AssetEnum.LeaseStatus.getParentValueByKey(subStatus);
    }

    public String getLeaseType() {
        return leaseType;
    }

    public void setLeaseType(String leaseType) {
        this.leaseType = leaseType;
        this.leaseTypeDesc = AssetEnum.LeaseAssetType.getValueByKey(leaseType);
    }


    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
        this.areaTypeDesc = AssetEnum.LeaseAreaType.getValueByKey(areaType);
    }

    public Integer getLeaseArea() {
        return leaseArea;
    }

    public void setLeaseArea(Integer leaseArea) {
        this.leaseArea = leaseArea;
    }

    public Integer getLeaseRoomNumber() {
        return leaseRoomNumber;
    }

    public void setLeaseRoomNumber(Integer leaseRoomNumber) {
        this.leaseRoomNumber = leaseRoomNumber;
    }

    public String getLeaseAddress() {
        return leaseAddress;
    }

    public void setLeaseAddress(String leaseAddress) {
        this.leaseAddress = leaseAddress;
    }

    public String getPowerSituation() {
        return powerSituation;
    }

    public void setPowerSituation(String powerSituation) {
        this.powerSituation = powerSituation;
    }

    public String getLegalPurposes() {
        return legalPurposes;
    }

    public void setLegalPurposes(String legalPurposes) {
        this.legalPurposes = legalPurposes;
    }

    public String getCurrentDesc() {
        return currentDesc;
    }

    public void setCurrentDesc(String currentDesc) {
        this.currentDesc = currentDesc;
    }

    public String getLessorName() {
        return lessorName;
    }

    public void setLessorName(String lessorName) {
        this.lessorName = lessorName;
    }

    public String getLessorAddress() {
        return lessorAddress;
    }

    public void setLessorAddress(String lessorAddress) {
        this.lessorAddress = lessorAddress;
    }

    public String getLessorContact() {
        return lessorContact;
    }

    public void setLessorContact(String lessorContact) {
        this.lessorContact = lessorContact;
    }

    public String getLessorContactNumber() {
        return lessorContactNumber;
    }

    public void setLessorContactNumber(String lessorContactNumber) {
        this.lessorContactNumber = lessorContactNumber;
    }

    public String getBidderQualification() {
        return bidderQualification;
    }

    public void setBidderQualification(String bidderQualification) {
        this.bidderQualification = bidderQualification;
        this.bidderQualificationDesc = AssetEnum.BidderQualification.getValueByKey(bidderQualification);
    }

    public Boolean getDeedFlag() {
        return deedFlag;
    }

    public void setDeedFlag(Boolean deedFlag) {
        this.deedFlag = deedFlag;
    }

    public String getPreAuditMethod() {
        return preAuditMethod;
    }

    public void setPreAuditMethod(String preAuditMethod) {
        this.preAuditMethod = preAuditMethod;
        this.preAuditMethodDesc = AssetEnum.PreAuditMethod.getValueByKey(preAuditMethod);
    }

    public String getOriginalTenantName() {
        return originalTenantName;
    }

    public void setOriginalTenantName(String originalTenantName) {
        this.originalTenantName = originalTenantName;
    }

    public String getOriginalTenantNumber() {
        return originalTenantNumber;
    }

    public void setOriginalTenantNumber(String originalTenantNumber) {
        this.originalTenantNumber = originalTenantNumber;
    }

    public String getPriorityQualification() {
        return priorityQualification;
    }

    public void setPriorityQualification(String priorityQualification) {
        this.priorityQualification = priorityQualification;
    }

    public String getManagementCapacity() {
        return managementCapacity;
    }

    public void setManagementCapacity(String managementCapacity) {
        this.managementCapacity = managementCapacity;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getBusinessNow() {
        return businessNow;
    }

    public void setBusinessNow(String businessNow) {
        this.businessNow = businessNow;
    }


    public String getOperatingType() {
        return operatingType;
    }

    public void setOperatingType(String operatingType) {
        this.operatingType = operatingType;
        this.operatingTypeDesc = AssetEnum.OperatingTypeType.getValueByKey(operatingType);
    }

    public String getBusinessAgreement() {
        return businessAgreement;
    }

    public void setBusinessAgreement(String businessAgreement) {
        this.businessAgreement = businessAgreement;
    }

    public String getRentAgreement() {
        return rentAgreement;
    }

    public void setRentAgreement(String rentAgreement) {
        this.rentAgreement = rentAgreement;
    }

    public String getHouseStructureAgreement() {
        return houseStructureAgreement;
    }

    public void setHouseStructureAgreement(String houseStructureAgreement) {
        this.houseStructureAgreement = houseStructureAgreement;
    }

    public String getMaintainAgreement() {
        return maintainAgreement;
    }

    public void setMaintainAgreement(String maintainAgreement) {
        this.maintainAgreement = maintainAgreement;
    }

    public String getSafetyAgreement() {
        return safetyAgreement;
    }

    public void setSafetyAgreement(String safetyAgreement) {
        this.safetyAgreement = safetyAgreement;
    }

    public String getLesseeOtherAgreement() {
        return lesseeOtherAgreement;
    }

    public void setLesseeOtherAgreement(String lesseeOtherAgreement) {
        this.lesseeOtherAgreement = lesseeOtherAgreement;
    }

    public String getLevyAgreement() {
        return levyAgreement;
    }

    public void setLevyAgreement(String levyAgreement) {
        this.levyAgreement = levyAgreement;
    }

    public String getRenewalAgreement() {
        return renewalAgreement;
    }

    public void setRenewalAgreement(String renewalAgreement) {
        this.renewalAgreement = renewalAgreement;
    }

    public String getTerminationContractAgreement() {
        return terminationContractAgreement;
    }

    public void setTerminationContractAgreement(String terminationContractAgreement) {
        this.terminationContractAgreement = terminationContractAgreement;
    }

    public String getDisputeResolutionAgreement() {
        return disputeResolutionAgreement;
    }

    public void setDisputeResolutionAgreement(String disputeResolutionAgreement) {
        this.disputeResolutionAgreement = disputeResolutionAgreement;
    }

    public String getDefaultLiabilityAgreement() {
        return defaultLiabilityAgreement;
    }

    public void setDefaultLiabilityAgreement(String defaultLiabilityAgreement) {
        this.defaultLiabilityAgreement = defaultLiabilityAgreement;
    }

    public String getContractOtherAgreement() {
        return contractOtherAgreement;
    }

    public void setContractOtherAgreement(String contractOtherAgreement) {
        this.contractOtherAgreement = contractOtherAgreement;
    }

    public String getLessorCommissionRate() {
        return lessorCommissionRate;
    }

    public void setLessorCommissionRate(String lessorCommissionRate) {
        this.lessorCommissionRate = lessorCommissionRate;
    }

    public String getLesseeCommissionRate() {
        return lesseeCommissionRate;
    }

    public void setLesseeCommissionRate(String lesseeCommissionRate) {
        this.lesseeCommissionRate = lesseeCommissionRate;
    }

    public String getAnnualIncrementRate() {
        return annualIncrementRate;
    }

    public void setAnnualIncrementRate(String annualIncrementRate) {
        this.annualIncrementRate = annualIncrementRate;
    }

    public BigDecimal getIncrement() {
        return increment;
    }

    public void setIncrement(BigDecimal increment) {
        this.increment = increment;
    }

    public BigDecimal getReduction() {
        return reduction;
    }

    public void setReduction(BigDecimal reduction) {
        this.reduction = reduction;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getReductionPeriod() {
        return reductionPeriod;
    }

    public void setReductionPeriod(Integer reductionPeriod) {
        this.reductionPeriod = reductionPeriod;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(Date beginAt) {
        this.beginAt = beginAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setLeaseTypeDesc(String leaseTypeDesc) {
        this.leaseTypeDesc = leaseTypeDesc;
    }

    public Date getLeaseStartTime() {
        return leaseStartTime;
    }

    public void setLeaseStartTime(Date leaseStartTime) {
        this.leaseStartTime = leaseStartTime;
    }

    public Date getLeaseEndTime() {
        return leaseEndTime;
    }

    public void setLeaseEndTime(Date leaseEndTime) {
        this.leaseEndTime = leaseEndTime;
    }

    public Date getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(Date applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public Date getApprovalEndTime() {
        return approvalEndTime;
    }

    public void setApprovalEndTime(Date approvalEndTime) {
        this.approvalEndTime = approvalEndTime;
    }

    public Date getPreviewAt() {
        return previewAt;
    }

    public void setPreviewAt(Date previewAt) {
        this.previewAt = previewAt;
    }

    public String getLeaseTypeDesc() {
        return leaseTypeDesc;
    }



    public String getAreaTypeDesc() {
        return areaTypeDesc;
    }

    public void setAreaTypeDesc(String areaTypeDesc) {
        this.areaTypeDesc = areaTypeDesc;
    }

    public String getBidderQualificationDesc() {
        return bidderQualificationDesc;
    }

    public void setBidderQualificationDesc(String bidderQualificationDesc) {
        this.bidderQualificationDesc = bidderQualificationDesc;
    }

    public String getPreAuditMethodDesc() {
        return preAuditMethodDesc;
    }

    public void setPreAuditMethodDesc(String preAuditMethodDesc) {
        this.preAuditMethodDesc = preAuditMethodDesc;
    }

    public String getOperatingTypeDesc() {
        return operatingTypeDesc;
    }

    public void setOperatingTypeDesc(String operatingTypeDesc) {
        this.operatingTypeDesc = operatingTypeDesc;
    }
}
