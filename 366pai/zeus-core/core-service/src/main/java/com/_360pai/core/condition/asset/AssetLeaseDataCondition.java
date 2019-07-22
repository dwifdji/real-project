
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月23日 15时22分01秒
 */
public class AssetLeaseDataCondition implements DaoCondition{

	/**
	 * 自增ID
	 */
	private Integer id;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private java.util.Date createdAt;
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
	private java.math.BigDecimal reservePrice;
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
	private java.util.Date beginAt;
	/**
	 * 期望拍卖结束时间
	 */
	private java.util.Date endAt;
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
	private java.util.Date updatedAt;
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
	 * 区域类型
	 */
	private String areaType;
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
	 * 出租房地址
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
	 * 是否有房产证
	 */
	private Boolean deedFlag;
	/**
	 * 资料预审方式
	 */
	private String preAuditMethod;
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
	private java.util.Date leaseStartTime;
	/**
	 * 租赁结束时间
	 */
	private java.util.Date leaseEndTime;
	/**
	 * 出租用途类型约定
	 */
	private String operatingType;
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
	private java.util.Date applyEndTime;
	/**
	 * 审核结束时间
	 */
	private java.util.Date approvalEndTime;
	/**
	 * 租金年递增比例
	 */
	private String annualIncrementRate;
	/**
	 * 标id
	 */
	private Integer assetId;
	/**
	 * 保证金
	 */
	private java.math.BigDecimal deposit;
	/**
	 * 加价幅度
	 */
	private java.math.BigDecimal increment;
	/**
	 * 降价幅度
	 */
	private java.math.BigDecimal reduction;
	/**
	 * 降价周期
	 */
	private Integer reductionPeriod;
	/**
	 * 开始报名时间
	 */
	private java.util.Date previewAt;
	/**
	 * 资产亮点
	 */
	private String brightSpot;
	/**
	 * 服务机构id
	 */
	private Integer serverAgencyId;
	/**
	 * 拒绝原因
	 */
	private String rejectReason;
	/**
	 * 镇id
	 */
	private String townId;

	/**
	 * 租金支付约定
	 */
	private String paymentCycle;

	/**
	 * 费用承担方
	 */
	private String costBearer;

	/**
	 * 区域数组JSON对象
	 */
	private com.alibaba.fastjson.JSONObject assetLeaseArea;

	/**
	 * 房屋结构
	 */
	private String houseStructure;

	/**
	 * 租赁权编码
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

	public String getHouseStructure() {
		return houseStructure;
	}

	public void setHouseStructure(String houseStructure) {
		this.houseStructure = houseStructure;
	}

	public JSONObject getAssetLeaseArea() {
		return assetLeaseArea;
	}

	public void setAssetLeaseArea(JSONObject assetLeaseArea) {
		this.assetLeaseArea = assetLeaseArea;
	}

	public String getPaymentCycle() {
		return paymentCycle;
	}

	public void setPaymentCycle(String paymentCycle) {
		this.paymentCycle = paymentCycle;
	}

	public String getCostBearer() {
		return costBearer;
	}

	public void setCostBearer(String costBearer) {
		this.costBearer = costBearer;
	}


	public String getTownId() {
		return townId;
	}

	public void setTownId(String townId) {
		this.townId = townId;
	}


	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	/**
	 * 自增ID
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 自增ID
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 状态
	 */
	public String getStatus(){
		return status;
	}
	
	/**
	 * 状态
	 */
	public void setStatus(String status){
		this.status = status;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreatedAt(){
		return createdAt;
	}
	
	/**
	 * 创建时间
	 */
	public void setCreatedAt(java.util.Date createdAt){
		this.createdAt = createdAt;
	}
	
	/**
	 * 标的编号
	 */
	public String getCode(){
		return code;
	}
	
	/**
	 * 标的编号
	 */
	public void setCode(String code){
		this.code = code;
	}
	
	/**
	 * 标的名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 标的名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 标的所在省id
	 */
	public String getProvinceId(){
		return provinceId;
	}
	
	/**
	 * 标的所在省id
	 */
	public void setProvinceId(String provinceId){
		this.provinceId = provinceId;
	}
	
	/**
	 * 标的所在地
	 */
	public String getCityId(){
		return cityId;
	}
	
	/**
	 * 标的所在地
	 */
	public void setCityId(String cityId){
		this.cityId = cityId;
	}
	
	/**
	 * 标的所在区id
	 */
	public String getAreaId(){
		return areaId;
	}
	
	/**
	 * 标的所在区id
	 */
	public void setAreaId(String areaId){
		this.areaId = areaId;
	}
	
	/**
	 * 委托人ID
	 */
	public Integer getPartyId(){
		return partyId;
	}
	
	/**
	 * 委托人ID
	 */
	public void setPartyId(Integer partyId){
		this.partyId = partyId;
	}
	
	/**
	 * 标的物可能由多个
	 */
	public Integer getQuantity(){
		return quantity;
	}
	
	/**
	 * 标的物可能由多个
	 */
	public void setQuantity(Integer quantity){
		this.quantity = quantity;
	}
	
	/**
	 * 剩余个数
	 */
	public Integer getRemain(){
		return remain;
	}
	
	/**
	 * 剩余个数
	 */
	public void setRemain(Integer remain){
		this.remain = remain;
	}
	
	/**
	 * 标的物类型
	 */
	public String getAssetType(){
		return assetType;
	}
	
	/**
	 * 标的物类型
	 */
	public void setAssetType(String assetType){
		this.assetType = assetType;
	}
	
	/**
	 * 备注
	 */
	public String getRemark(){
		return remark;
	}
	
	/**
	 * 备注
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	/**
	 * 上传机构ID
	 */
	public Integer getAgencyId(){
		return agencyId;
	}
	
	/**
	 * 上传机构ID
	 */
	public void setAgencyId(Integer agencyId){
		this.agencyId = agencyId;
	}
	
	/**
	 * 保留价
	 */
	public java.math.BigDecimal getReservePrice(){
		return reservePrice;
	}
	
	/**
	 * 保留价
	 */
	public void setReservePrice(java.math.BigDecimal reservePrice){
		this.reservePrice = reservePrice;
	}
	
	/**
	 * 描述文件
	 */
	public String getDescriptionDoc(){
		return descriptionDoc;
	}
	
	/**
	 * 描述文件
	 */
	public void setDescriptionDoc(String descriptionDoc){
		this.descriptionDoc = descriptionDoc;
	}
	
	/**
	 * 标的详情
	 */
	public String getDetail(){
		return detail;
	}
	
	/**
	 * 标的详情
	 */
	public void setDetail(String detail){
		this.detail = detail;
	}
	
	/**
	 * 联系人
	 */
	public String getContactName(){
		return contactName;
	}
	
	/**
	 * 联系人
	 */
	public void setContactName(String contactName){
		this.contactName = contactName;
	}
	
	/**
	 * 联系人手机号
	 */
	public String getContactPhone(){
		return contactPhone;
	}
	
	/**
	 * 联系人手机号
	 */
	public void setContactPhone(String contactPhone){
		this.contactPhone = contactPhone;
	}
	
	/**
	 * 联系人QQ
	 */
	public String getContactQq(){
		return contactQq;
	}
	
	/**
	 * 联系人QQ
	 */
	public void setContactQq(String contactQq){
		this.contactQq = contactQq;
	}
	
	/**
	 * 包含图片的JSON数据
	 */
	public com.alibaba.fastjson.JSONObject getExtra(){
		return extra;
	}
	
	/**
	 * 包含图片的JSON数据
	 */
	public void setExtra(com.alibaba.fastjson.JSONObject extra){
		this.extra = extra;
	}
	
	/**
	 * 期望拍卖模式
	 */
	public String getExpectedMode(){
		return expectedMode;
	}
	
	/**
	 * 期望拍卖模式
	 */
	public void setExpectedMode(String expectedMode){
		this.expectedMode = expectedMode;
	}
	
	/**
	 * 参考价
	 */
	public java.math.BigDecimal getRefPrice(){
		return refPrice;
	}
	
	/**
	 * 参考价
	 */
	public void setRefPrice(java.math.BigDecimal refPrice){
		this.refPrice = refPrice;
	}
	
	/**
	 * 起拍价
	 */
	public java.math.BigDecimal getStartingPrice(){
		return startingPrice;
	}
	
	/**
	 * 起拍价
	 */
	public void setStartingPrice(java.math.BigDecimal startingPrice){
		this.startingPrice = startingPrice;
	}
	
	/**
	 * 期望拍卖开始时间
	 */
	public java.util.Date getBeginAt(){
		return beginAt;
	}
	
	/**
	 * 期望拍卖开始时间
	 */
	public void setBeginAt(java.util.Date beginAt){
		this.beginAt = beginAt;
	}
	
	/**
	 * 期望拍卖结束时间
	 */
	public java.util.Date getEndAt(){
		return endAt;
	}
	
	/**
	 * 期望拍卖结束时间
	 */
	public void setEndAt(java.util.Date endAt){
		this.endAt = endAt;
	}
	
	/**
	 * 标的属性ID
	 */
	public Integer getPropertyId(){
		return propertyId;
	}
	
	/**
	 * 标的属性ID
	 */
	public void setPropertyId(Integer propertyId){
		this.propertyId = propertyId;
	}
	
	/**
	 * 标的类型ID(模板ID)
	 */
	public Integer getCategoryId(){
		return categoryId;
	}
	
	/**
	 * 标的类型ID(模板ID)
	 */
	public void setCategoryId(Integer categoryId){
		this.categoryId = categoryId;
	}
	
	/**
	 * 标的物选项
	 */
	public String getOptions(){
		return options;
	}
	
	/**
	 * 标的物选项
	 */
	public void setOptions(String options){
		this.options = options;
	}
	
	/**
	 * 交接天数 仅作为 属性
	 */
	public Integer getHandoverDays(){
		return handoverDays;
	}
	
	/**
	 * 交接天数 仅作为 属性
	 */
	public void setHandoverDays(Integer handoverDays){
		this.handoverDays = handoverDays;
	}
	
	/**
	 * 支付天数 仅作为 属性
	 */
	public Integer getPayDays(){
		return payDays;
	}
	
	/**
	 * 支付天数 仅作为 属性
	 */
	public void setPayDays(Integer payDays){
		this.payDays = payDays;
	}
	
	/**
	 * 特别说明
	 */
	public String getSpecialDetail(){
		return specialDetail;
	}
	
	/**
	 * 特别说明
	 */
	public void setSpecialDetail(String specialDetail){
		this.specialDetail = specialDetail;
	}
	
	/**
	 * 银行账户名
	 */
	public String getBankAccountName(){
		return bankAccountName;
	}
	
	/**
	 * 银行账户名
	 */
	public void setBankAccountName(String bankAccountName){
		this.bankAccountName = bankAccountName;
	}
	
	/**
	 * 银行账户号码
	 */
	public String getBankAccountNumber(){
		return bankAccountNumber;
	}
	
	/**
	 * 银行账户号码
	 */
	public void setBankAccountNumber(String bankAccountNumber){
		this.bankAccountNumber = bankAccountNumber;
	}
	
	/**
	 * 银行ID
	 */
	public Integer getBankId(){
		return bankId;
	}
	
	/**
	 * 银行ID
	 */
	public void setBankId(Integer bankId){
		this.bankId = bankId;
	}
	
	/**
	 * 用户手动输入的银行名称
	 */
	public String getBankName(){
		return bankName;
	}
	
	/**
	 * 用户手动输入的银行名称
	 */
	public void setBankName(String bankName){
		this.bankName = bankName;
	}
	
	/**
	 * 尽调报告 金额
	 */
	public java.math.BigDecimal getTuneReport(){
		return tuneReport;
	}
	
	/**
	 * 尽调报告 金额
	 */
	public void setTuneReport(java.math.BigDecimal tuneReport){
		this.tuneReport = tuneReport;
	}
	
	/**
	 * 尽调报告url
	 */
	public com.alibaba.fastjson.JSONObject getTuneReportUrl(){
		return tuneReportUrl;
	}
	
	/**
	 * 尽调报告url
	 */
	public void setTuneReportUrl(com.alibaba.fastjson.JSONObject tuneReportUrl){
		this.tuneReportUrl = tuneReportUrl;
	}
	
	/**
	 * 尽调报告是否授权  false未授权  true授权
	 */
	public Integer getTuneReportAuthorization(){
		return tuneReportAuthorization;
	}
	
	/**
	 * 尽调报告是否授权  false未授权  true授权
	 */
	public void setTuneReportAuthorization(Integer tuneReportAuthorization){
		this.tuneReportAuthorization = tuneReportAuthorization;
	}
	
	/**
	 * 评估报告 1:基础评估 2:完整评估
	 */
	public java.math.BigDecimal getEvaluationReport(){
		return evaluationReport;
	}
	
	/**
	 * 评估报告 1:基础评估 2:完整评估
	 */
	public void setEvaluationReport(java.math.BigDecimal evaluationReport){
		this.evaluationReport = evaluationReport;
	}
	
	/**
	 * 评估报告url
	 */
	public com.alibaba.fastjson.JSONObject getEvaluationReportUrl(){
		return evaluationReportUrl;
	}
	
	/**
	 * 评估报告url
	 */
	public void setEvaluationReportUrl(com.alibaba.fastjson.JSONObject evaluationReportUrl){
		this.evaluationReportUrl = evaluationReportUrl;
	}
	
	/**
	 * 债券转让协议url
	 */
	public String getClaimsTransferUrl(){
		return claimsTransferUrl;
	}
	
	/**
	 * 债券转让协议url
	 */
	public void setClaimsTransferUrl(String claimsTransferUrl){
		this.claimsTransferUrl = claimsTransferUrl;
	}
	
	/**
	 * 转让公告url
	 */
	public String getTransferAnnouncementUrl(){
		return transferAnnouncementUrl;
	}
	
	/**
	 * 转让公告url
	 */
	public void setTransferAnnouncementUrl(String transferAnnouncementUrl){
		this.transferAnnouncementUrl = transferAnnouncementUrl;
	}
	
	/**
	 * 更新时间
	 */
	public java.util.Date getUpdatedAt(){
		return updatedAt;
	}
	
	/**
	 * 更新时间
	 */
	public void setUpdatedAt(java.util.Date updatedAt){
		this.updatedAt = updatedAt;
	}
	
	/**
	 * 线上 线下操作 0:线下操作 1:线上操作
	 */
	public Integer getOnlined(){
		return onlined;
	}
	
	/**
	 * 线上 线下操作 0:线下操作 1:线上操作
	 */
	public void setOnlined(Integer onlined){
		this.onlined = onlined;
	}
	
	/**
	 * 业务类型 0:拍卖 1:配置乐 2:处置服务商
	 */
	public Integer getBusType(){
		return busType;
	}
	
	/**
	 * 业务类型 0:拍卖 1:配置乐 2:处置服务商
	 */
	public void setBusType(Integer busType){
		this.busType = busType;
	}
	
	/**
	 * 债权本金
	 */
	public java.math.BigDecimal getDebtPrincipal(){
		return debtPrincipal;
	}
	
	/**
	 * 债权本金
	 */
	public void setDebtPrincipal(java.math.BigDecimal debtPrincipal){
		this.debtPrincipal = debtPrincipal;
	}
	
	/**
	 * 债权利息
	 */
	public java.math.BigDecimal getDebtInterest(){
		return debtInterest;
	}
	
	/**
	 * 债权利息
	 */
	public void setDebtInterest(java.math.BigDecimal debtInterest){
		this.debtInterest = debtInterest;
	}
	
	/**
	 * 上拍来源：0 平台 1 机构
	 */
	public String getComeFrom(){
		return comeFrom;
	}
	
	/**
	 * 上拍来源：0 平台 1 机构
	 */
	public void setComeFrom(String comeFrom){
		this.comeFrom = comeFrom;
	}
	
	/**
	 * 标的连拍状态 0 联拍 1不联拍
	 */
	public Integer getJointStatus(){
		return jointStatus;
	}
	
	/**
	 * 标的连拍状态 0 联拍 1不联拍
	 */
	public void setJointStatus(Integer jointStatus){
		this.jointStatus = jointStatus;
	}
	
	/**
	 * 标识老数据 1:true老数据  0:false新数据
	 */
	public Boolean getOldData(){
		return oldData;
	}
	
	/**
	 * 标识老数据 1:true老数据  0:false新数据
	 */
	public void setOldData(Boolean oldData){
		this.oldData = oldData;
	}
	
	/**
	 * spvID  初始值为0
	 */
	public Integer getSpvId(){
		return spvId;
	}
	
	/**
	 * spvID  初始值为0
	 */
	public void setSpvId(Integer spvId){
		this.spvId = spvId;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public void setDeleteFlag(Boolean deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	
	/**
	 * 标的小状态
	 */
	public String getSubStatus(){
		return subStatus;
	}
	
	/**
	 * 标的小状态
	 */
	public void setSubStatus(String subStatus){
		this.subStatus = subStatus;
	}
	
	/**
	 * 租赁类型
	 */
	public String getLeaseType(){
		return leaseType;
	}
	
	/**
	 * 租赁类型
	 */
	public void setLeaseType(String leaseType){
		this.leaseType = leaseType;
	}
	
	/**
	 * 区域类型
	 */
	public String getAreaType(){
		return areaType;
	}
	
	/**
	 * 区域类型
	 */
	public void setAreaType(String areaType){
		this.areaType = areaType;
	}
	
	/**
	 * 租赁面积
	 */
	public Integer getLeaseArea(){
		return leaseArea;
	}
	
	/**
	 * 租赁面积
	 */
	public void setLeaseArea(Integer leaseArea){
		this.leaseArea = leaseArea;
	}
	
	/**
	 * 租赁间数
	 */
	public Integer getLeaseRoomNumber(){
		return leaseRoomNumber;
	}
	
	/**
	 * 租赁间数
	 */
	public void setLeaseRoomNumber(Integer leaseRoomNumber){
		this.leaseRoomNumber = leaseRoomNumber;
	}
	
	/**
	 * 出租物品地址
	 */
	public String getLeaseAddress(){
		return leaseAddress;
	}
	
	/**
	 * 出租物品地址
	 */
	public void setLeaseAddress(String leaseAddress){
		this.leaseAddress = leaseAddress;
	}
	
	/**
	 * 权力情况
	 */
	public String getPowerSituation(){
		return powerSituation;
	}
	
	/**
	 * 权力情况
	 */
	public void setPowerSituation(String powerSituation){
		this.powerSituation = powerSituation;
	}
	
	/**
	 * 法律用途
	 */
	public String getLegalPurposes(){
		return legalPurposes;
	}
	
	/**
	 * 法律用途
	 */
	public void setLegalPurposes(String legalPurposes){
		this.legalPurposes = legalPurposes;
	}
	
	/**
	 * 现状描述
	 */
	public String getCurrentDesc(){
		return currentDesc;
	}
	
	/**
	 * 现状描述
	 */
	public void setCurrentDesc(String currentDesc){
		this.currentDesc = currentDesc;
	}
	
	/**
	 * 出租房名称
	 */
	public String getLessorName(){
		return lessorName;
	}
	
	/**
	 * 出租房名称
	 */
	public void setLessorName(String lessorName){
		this.lessorName = lessorName;
	}
	
	/**
	 * 出租房地址
	 */
	public String getLessorAddress(){
		return lessorAddress;
	}
	
	/**
	 * 出租房地址
	 */
	public void setLessorAddress(String lessorAddress){
		this.lessorAddress = lessorAddress;
	}
	
	/**
	 * 出租方联系人
	 */
	public String getLessorContact(){
		return lessorContact;
	}
	
	/**
	 * 出租方联系人
	 */
	public void setLessorContact(String lessorContact){
		this.lessorContact = lessorContact;
	}
	
	/**
	 * 出租房联系方式
	 */
	public String getLessorContactNumber(){
		return lessorContactNumber;
	}
	
	/**
	 * 出租房联系方式
	 */
	public void setLessorContactNumber(String lessorContactNumber){
		this.lessorContactNumber = lessorContactNumber;
	}
	
	/**
	 * 竞租方资格
	 */
	public String getBidderQualification(){
		return bidderQualification;
	}
	
	/**
	 * 竞租方资格
	 */
	public void setBidderQualification(String bidderQualification){
		this.bidderQualification = bidderQualification;
	}
	
	/**
	 * 是否有房产证
	 */
	public Boolean getDeedFlag(){
		return deedFlag;
	}
	
	/**
	 * 是否有房产证
	 */
	public void setDeedFlag(Boolean deedFlag){
		this.deedFlag = deedFlag;
	}
	
	/**
	 * 资料预审方式
	 */
	public String getPreAuditMethod(){
		return preAuditMethod;
	}
	
	/**
	 * 资料预审方式
	 */
	public void setPreAuditMethod(String preAuditMethod){
		this.preAuditMethod = preAuditMethod;
	}
	
	/**
	 * 原租户名称
	 */
	public String getOriginalTenantName(){
		return originalTenantName;
	}
	
	/**
	 * 原租户名称
	 */
	public void setOriginalTenantName(String originalTenantName){
		this.originalTenantName = originalTenantName;
	}
	
	/**
	 * 原租户联系方式
	 */
	public String getOriginalTenantNumber(){
		return originalTenantNumber;
	}
	
	/**
	 * 原租户联系方式
	 */
	public void setOriginalTenantNumber(String originalTenantNumber){
		this.originalTenantNumber = originalTenantNumber;
	}
	
	/**
	 * 优先资格
	 */
	public String getPriorityQualification(){
		return priorityQualification;
	}
	
	/**
	 * 优先资格
	 */
	public void setPriorityQualification(String priorityQualification){
		this.priorityQualification = priorityQualification;
	}
	
	/**
	 * 经营能力
	 */
	public String getManagementCapacity(){
		return managementCapacity;
	}
	
	/**
	 * 经营能力
	 */
	public void setManagementCapacity(String managementCapacity){
		this.managementCapacity = managementCapacity;
	}
	
	/**
	 * 注册地址
	 */
	public String getRegisteredAddress(){
		return registeredAddress;
	}
	
	/**
	 * 注册地址
	 */
	public void setRegisteredAddress(String registeredAddress){
		this.registeredAddress = registeredAddress;
	}
	
	/**
	 * 现在经营业务
	 */
	public String getBusinessNow(){
		return businessNow;
	}
	
	/**
	 * 现在经营业务
	 */
	public void setBusinessNow(String businessNow){
		this.businessNow = businessNow;
	}
	
	/**
	 * 租赁开始时间
	 */
	public java.util.Date getLeaseStartTime(){
		return leaseStartTime;
	}
	
	/**
	 * 租赁开始时间
	 */
	public void setLeaseStartTime(java.util.Date leaseStartTime){
		this.leaseStartTime = leaseStartTime;
	}
	
	/**
	 * 租赁结束时间
	 */
	public java.util.Date getLeaseEndTime(){
		return leaseEndTime;
	}
	
	/**
	 * 租赁结束时间
	 */
	public void setLeaseEndTime(java.util.Date leaseEndTime){
		this.leaseEndTime = leaseEndTime;
	}
	
	/**
	 * 出租用途类型约定
	 */
	public String getOperatingType(){
		return operatingType;
	}
	
	/**
	 * 出租用途类型约定
	 */
	public void setOperatingType(String operatingType){
		this.operatingType = operatingType;
	}
	
	/**
	 * 业态约定
	 */
	public String getBusinessAgreement(){
		return businessAgreement;
	}
	
	/**
	 * 业态约定
	 */
	public void setBusinessAgreement(String businessAgreement){
		this.businessAgreement = businessAgreement;
	}
	
	/**
	 * 租金约定
	 */
	public String getRentAgreement(){
		return rentAgreement;
	}
	
	/**
	 * 租金约定
	 */
	public void setRentAgreement(String rentAgreement){
		this.rentAgreement = rentAgreement;
	}
	
	/**
	 * 房屋结构约定
	 */
	public String getHouseStructureAgreement(){
		return houseStructureAgreement;
	}
	
	/**
	 * 房屋结构约定
	 */
	public void setHouseStructureAgreement(String houseStructureAgreement){
		this.houseStructureAgreement = houseStructureAgreement;
	}
	
	/**
	 * 维护约定
	 */
	public String getMaintainAgreement(){
		return maintainAgreement;
	}
	
	/**
	 * 维护约定
	 */
	public void setMaintainAgreement(String maintainAgreement){
		this.maintainAgreement = maintainAgreement;
	}
	
	/**
	 * 安全约定
	 */
	public String getSafetyAgreement(){
		return safetyAgreement;
	}
	
	/**
	 * 安全约定
	 */
	public void setSafetyAgreement(String safetyAgreement){
		this.safetyAgreement = safetyAgreement;
	}
	
	/**
	 * 其他约定
	 */
	public String getLesseeOtherAgreement(){
		return lesseeOtherAgreement;
	}
	
	/**
	 * 其他约定
	 */
	public void setLesseeOtherAgreement(String lesseeOtherAgreement){
		this.lesseeOtherAgreement = lesseeOtherAgreement;
	}
	
	/**
	 * 征收约定
	 */
	public String getLevyAgreement(){
		return levyAgreement;
	}
	
	/**
	 * 征收约定
	 */
	public void setLevyAgreement(String levyAgreement){
		this.levyAgreement = levyAgreement;
	}
	
	/**
	 * 续租约定
	 */
	public String getRenewalAgreement(){
		return renewalAgreement;
	}
	
	/**
	 * 续租约定
	 */
	public void setRenewalAgreement(String renewalAgreement){
		this.renewalAgreement = renewalAgreement;
	}
	
	/**
	 * 终止合同约定
	 */
	public String getTerminationContractAgreement(){
		return terminationContractAgreement;
	}
	
	/**
	 * 终止合同约定
	 */
	public void setTerminationContractAgreement(String terminationContractAgreement){
		this.terminationContractAgreement = terminationContractAgreement;
	}
	
	/**
	 * 争议处理约定
	 */
	public String getDisputeResolutionAgreement(){
		return disputeResolutionAgreement;
	}
	
	/**
	 * 争议处理约定
	 */
	public void setDisputeResolutionAgreement(String disputeResolutionAgreement){
		this.disputeResolutionAgreement = disputeResolutionAgreement;
	}
	
	/**
	 * 违约责任约定
	 */
	public String getDefaultLiabilityAgreement(){
		return defaultLiabilityAgreement;
	}
	
	/**
	 * 违约责任约定
	 */
	public void setDefaultLiabilityAgreement(String defaultLiabilityAgreement){
		this.defaultLiabilityAgreement = defaultLiabilityAgreement;
	}
	
	/**
	 * 合同其他约定
	 */
	public String getContractOtherAgreement(){
		return contractOtherAgreement;
	}
	
	/**
	 * 合同其他约定
	 */
	public void setContractOtherAgreement(String contractOtherAgreement){
		this.contractOtherAgreement = contractOtherAgreement;
	}
	
	/**
	 * 出租房佣金比例
	 */
	public String getLessorCommissionRate(){
		return lessorCommissionRate;
	}
	
	/**
	 * 出租房佣金比例
	 */
	public void setLessorCommissionRate(String lessorCommissionRate){
		this.lessorCommissionRate = lessorCommissionRate;
	}
	
	/**
	 * 承租方佣金比例
	 */
	public String getLesseeCommissionRate(){
		return lesseeCommissionRate;
	}
	
	/**
	 * 承租方佣金比例
	 */
	public void setLesseeCommissionRate(String lesseeCommissionRate){
		this.lesseeCommissionRate = lesseeCommissionRate;
	}
	
	/**
	 * 报名结束时间
	 */
	public java.util.Date getApplyEndTime(){
		return applyEndTime;
	}
	
	/**
	 * 报名结束时间
	 */
	public void setApplyEndTime(java.util.Date applyEndTime){
		this.applyEndTime = applyEndTime;
	}
	
	/**
	 * 审核结束时间
	 */
	public java.util.Date getApprovalEndTime(){
		return approvalEndTime;
	}
	
	/**
	 * 审核结束时间
	 */
	public void setApprovalEndTime(java.util.Date approvalEndTime){
		this.approvalEndTime = approvalEndTime;
	}
	
	/**
	 * 租金年递增比例
	 */
	public String getAnnualIncrementRate(){
		return annualIncrementRate;
	}
	
	/**
	 * 租金年递增比例
	 */
	public void setAnnualIncrementRate(String annualIncrementRate){
		this.annualIncrementRate = annualIncrementRate;
	}
	
	/**
	 * 标id
	 */
	public Integer getAssetId(){
		return assetId;
	}
	
	/**
	 * 标id
	 */
	public void setAssetId(Integer assetId){
		this.assetId = assetId;
	}
	
	/**
	 * 保证金
	 */
	public java.math.BigDecimal getDeposit(){
		return deposit;
	}
	
	/**
	 * 保证金
	 */
	public void setDeposit(java.math.BigDecimal deposit){
		this.deposit = deposit;
	}
	
	/**
	 * 加价幅度
	 */
	public java.math.BigDecimal getIncrement(){
		return increment;
	}
	
	/**
	 * 加价幅度
	 */
	public void setIncrement(java.math.BigDecimal increment){
		this.increment = increment;
	}
	
	/**
	 * 降价幅度
	 */
	public java.math.BigDecimal getReduction(){
		return reduction;
	}
	
	/**
	 * 降价幅度
	 */
	public void setReduction(java.math.BigDecimal reduction){
		this.reduction = reduction;
	}
	
	/**
	 * 降价周期
	 */
	public Integer getReductionPeriod(){
		return reductionPeriod;
	}
	
	/**
	 * 降价周期
	 */
	public void setReductionPeriod(Integer reductionPeriod){
		this.reductionPeriod = reductionPeriod;
	}
	
	/**
	 * 开始报名时间
	 */
	public java.util.Date getPreviewAt(){
		return previewAt;
	}
	
	/**
	 * 开始报名时间
	 */
	public void setPreviewAt(java.util.Date previewAt){
		this.previewAt = previewAt;
	}
	
	/**
	 * 资产亮点
	 */
	public String getBrightSpot(){
		return brightSpot;
	}
	
	/**
	 * 资产亮点
	 */
	public void setBrightSpot(String brightSpot){
		this.brightSpot = brightSpot;
	}
	
	/**
	 * 服务机构id
	 */
	public Integer getServerAgencyId(){
		return serverAgencyId;
	}
	
	/**
	 * 服务机构id
	 */
	public void setServerAgencyId(Integer serverAgencyId){
		this.serverAgencyId = serverAgencyId;
	}

}