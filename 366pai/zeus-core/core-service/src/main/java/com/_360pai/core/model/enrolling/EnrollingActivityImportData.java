
package com._360pai.core.model.enrolling;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年02月14日 13时27分22秒
 */
public class EnrollingActivityImportData implements java.io.Serializable{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 
	 */
	private java.lang.Integer activityId;
	/**
	 * 招商会名称
	 */
	private java.lang.String name;
	/**
	 * 分公司名称
	 */
	private java.lang.String branchComName;
	/**
	 * 债务人
	 */
	private java.lang.String debtor;
	/**
	 * 债务人行业
	 */
	private java.lang.String debtorBus;
	/**
	 * 经营状况
	 */
	private java.lang.String busStates;
	/**
	 * 省份
	 */
	private java.lang.String debtorPro;
	/**
	 * 城市
	 */
	private java.lang.String debtorCity;
	/**
	 * 地区
	 */
	private java.lang.String debtorArea;
	/**
	 * 所在地
	 */
	private java.lang.String debtLocation;
	/**
	 * 抵押物所在地
	 */
	private java.lang.String pawnLocation;
	/**
	 * 省份
	 */
	private java.lang.String pawnPro;
	/**
	 * 城市
	 */
	private java.lang.String pawnCity;
	/**
	 * 地区
	 */
	private java.lang.String pawnArea;
	/**
	 * 基准日
	 */
	private java.lang.String baseDate;
	/**
	 * 未偿本金
	 */
	private java.lang.String outstandingPrincipal;
	/**
	 * 未偿利息
	 */
	private java.lang.String outstandingInterest;
	/**
	 * 违约金
	 */
	private java.lang.String dedit;
	/**
	 * 其他
	 */
	private java.lang.String otherInfo;
	/**
	 * 债权总额
	 */
	private java.lang.String totalDebt;
	/**
	 * 资产包户数
	 */
	private java.lang.String assetNum;
	/**
	 * 资产包来源
	 */
	private java.lang.String assetSource;
	/**
	 * 评估值
	 */
	private java.lang.String assetValue;
	/**
	 * 评估基准日
	 */
	private java.lang.String assetBaseDate;
	/**
	 * 评估报告出具日
	 */
	private java.lang.String reportDate;
	/**
	 * 担保方式
	 */
	private java.lang.String assureMeans;
	/**
	 * 保证人名称
	 */
	private java.lang.String assureName;
	/**
	 * 具体担保措施
	 */
	private java.lang.String specificAssureMeans;
	/**
	 * 房地产性质
	 */
	private java.lang.String realtyCharacter;
	/**
	 * 土地面积
	 */
	private java.lang.String landArea;
	/**
	 * 房屋建筑面积
	 */
	private java.lang.String buildingArea;
	/**
	 * 抵质押顺位
	 */
	private java.lang.String pledgeSequence;
	/**
	 * 其他担保方式情况说明
	 */
	private java.lang.String guaranteeThat;
	/**
	 * 所处诉讼环节
	 */
	private java.lang.String litigationLink;
	/**
	 * 是否采取司法保全
	 */
	private java.lang.String hasGuarantee;
	/**
	 * 如已采取司法保全
	 */
	private java.lang.String ifGuarantee;
	/**
	 * 项目亮点
	 */
	private java.lang.String projectWindow;
	/**
	 * 备注
	 */
	private java.lang.String remarks;
	/**
	 * 资产描述
	 */
	private java.lang.String assetDesc;
	/**
	 * 项目联系人
	 */
	private java.lang.String contactPerson;
	/**
	 * 联系电话
	 */
	private java.lang.String contactPhone;
	/**
	 * 处置服务商
	 */
	private java.lang.String disposalService;
	/**
	 * 资金服务商
	 */
	private java.lang.String fundProvider;


	private java.lang.Integer operatorId;


	private java.lang.String disposalPhone;

	private java.lang.String fundPhone;


	private java.lang.String debtorProId;

	private java.lang.String debtorCityId;

	/**
	 * 区县id
	 */
	private java.lang.String debtorAreaId;

	/**
	 * 区县id
	 */
	private java.lang.String pawnAreaId;

	private java.lang.String createTime;



	/**
	 * 删除标志（0 否 1 是）
	 */
	private java.lang.Boolean deleteFlag;

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	private java.lang.String pawnProId;

	private java.lang.String pawnCityId;





	public String getDebtorProId() {
		return debtorProId;
	}

	public void setDebtorProId(String debtorProId) {
		this.debtorProId = debtorProId;
	}

	public String getDebtorCityId() {
		return debtorCityId;
	}

	public void setDebtorCityId(String debtorCityId) {
		this.debtorCityId = debtorCityId;
	}

	public String getPawnProId() {
		return pawnProId;
	}

	public void setPawnProId(String pawnProId) {
		this.pawnProId = pawnProId;
	}

	public String getPawnCityId() {
		return pawnCityId;
	}

	public void setPawnCityId(String pawnCityId) {
		this.pawnCityId = pawnCityId;
	}

	public String getDisposalPhone() {
		return disposalPhone;
	}

	public void setDisposalPhone(String disposalPhone) {
		this.disposalPhone = disposalPhone;
	}

	public String getFundPhone() {
		return fundPhone;
	}

	public void setFundPhone(String fundPhone) {
		this.fundPhone = fundPhone;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * 
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 
	 */
	public java.lang.Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 
	 */
	public void setActivityId(java.lang.Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 招商会名称
	 */
	public java.lang.String getName(){
		return name;
	}
	
	/**
	 * 招商会名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	
	/**
	 * 分公司名称
	 */
	public java.lang.String getBranchComName(){
		return branchComName;
	}
	
	/**
	 * 分公司名称
	 */
	public void setBranchComName(java.lang.String branchComName){
		this.branchComName = branchComName;
	}
	
	/**
	 * 债务人
	 */
	public java.lang.String getDebtor(){
		return debtor;
	}
	
	/**
	 * 债务人
	 */
	public void setDebtor(java.lang.String debtor){
		this.debtor = debtor;
	}
	
	/**
	 * 债务人行业
	 */
	public java.lang.String getDebtorBus(){
		return debtorBus;
	}
	
	/**
	 * 债务人行业
	 */
	public void setDebtorBus(java.lang.String debtorBus){
		this.debtorBus = debtorBus;
	}
	
	/**
	 * 经营状况
	 */
	public java.lang.String getBusStates(){
		return busStates;
	}
	
	/**
	 * 经营状况
	 */
	public void setBusStates(java.lang.String busStates){
		this.busStates = busStates;
	}
	
	/**
	 * 省份
	 */
	public java.lang.String getDebtorPro(){
		return debtorPro;
	}
	
	/**
	 * 省份
	 */
	public void setDebtorPro(java.lang.String debtorPro){
		this.debtorPro = debtorPro;
	}
	
	/**
	 * 城市
	 */
	public java.lang.String getDebtorCity(){
		return debtorCity;
	}
	
	/**
	 * 城市
	 */
	public void setDebtorCity(java.lang.String debtorCity){
		this.debtorCity = debtorCity;
	}
	
	/**
	 * 地区
	 */
	public java.lang.String getDebtorArea(){
		return debtorArea;
	}
	
	/**
	 * 地区
	 */
	public void setDebtorArea(java.lang.String debtorArea){
		this.debtorArea = debtorArea;
	}
	
	/**
	 * 所在地
	 */
	public java.lang.String getDebtLocation(){
		return debtLocation;
	}
	
	/**
	 * 所在地
	 */
	public void setDebtLocation(java.lang.String debtLocation){
		this.debtLocation = debtLocation;
	}
	
	/**
	 * 抵押物所在地
	 */
	public java.lang.String getPawnLocation(){
		return pawnLocation;
	}
	
	/**
	 * 抵押物所在地
	 */
	public void setPawnLocation(java.lang.String pawnLocation){
		this.pawnLocation = pawnLocation;
	}
	
	/**
	 * 省份
	 */
	public java.lang.String getPawnPro(){
		return pawnPro;
	}
	
	/**
	 * 省份
	 */
	public void setPawnPro(java.lang.String pawnPro){
		this.pawnPro = pawnPro;
	}
	
	/**
	 * 城市
	 */
	public java.lang.String getPawnCity(){
		return pawnCity;
	}
	
	/**
	 * 城市
	 */
	public void setPawnCity(java.lang.String pawnCity){
		this.pawnCity = pawnCity;
	}
	
	/**
	 * 地区
	 */
	public java.lang.String getPawnArea(){
		return pawnArea;
	}
	
	/**
	 * 地区
	 */
	public void setPawnArea(java.lang.String pawnArea){
		this.pawnArea = pawnArea;
	}
	
	/**
	 * 基准日
	 */
	public java.lang.String getBaseDate(){
		return baseDate;
	}
	
	/**
	 * 基准日
	 */
	public void setBaseDate(java.lang.String baseDate){
		this.baseDate = baseDate;
	}
	
	/**
	 * 未偿本金
	 */
	public java.lang.String getOutstandingPrincipal(){
		return outstandingPrincipal;
	}
	
	/**
	 * 未偿本金
	 */
	public void setOutstandingPrincipal(java.lang.String outstandingPrincipal){
		this.outstandingPrincipal = outstandingPrincipal;
	}
	
	/**
	 * 未偿利息
	 */
	public java.lang.String getOutstandingInterest(){
		return outstandingInterest;
	}
	
	/**
	 * 未偿利息
	 */
	public void setOutstandingInterest(java.lang.String outstandingInterest){
		this.outstandingInterest = outstandingInterest;
	}
	
	/**
	 * 违约金
	 */
	public java.lang.String getDedit(){
		return dedit;
	}
	
	/**
	 * 违约金
	 */
	public void setDedit(java.lang.String dedit){
		this.dedit = dedit;
	}
	
	/**
	 * 其他
	 */
	public java.lang.String getOtherInfo(){
		return otherInfo;
	}
	
	/**
	 * 其他
	 */
	public void setOtherInfo(java.lang.String otherInfo){
		this.otherInfo = otherInfo;
	}
	
	/**
	 * 债权总额
	 */
	public java.lang.String getTotalDebt(){
		return totalDebt;
	}
	
	/**
	 * 债权总额
	 */
	public void setTotalDebt(java.lang.String totalDebt){
		this.totalDebt = totalDebt;
	}
	
	/**
	 * 资产包户数
	 */
	public java.lang.String getAssetNum(){
		return assetNum;
	}
	
	/**
	 * 资产包户数
	 */
	public void setAssetNum(java.lang.String assetNum){
		this.assetNum = assetNum;
	}
	
	/**
	 * 资产包来源
	 */
	public java.lang.String getAssetSource(){
		return assetSource;
	}
	
	/**
	 * 资产包来源
	 */
	public void setAssetSource(java.lang.String assetSource){
		this.assetSource = assetSource;
	}
	
	/**
	 * 评估值
	 */
	public java.lang.String getAssetValue(){
		return assetValue;
	}
	
	/**
	 * 评估值
	 */
	public void setAssetValue(java.lang.String assetValue){
		this.assetValue = assetValue;
	}
	
	/**
	 * 评估基准日
	 */
	public java.lang.String getAssetBaseDate(){
		return assetBaseDate;
	}
	
	/**
	 * 评估基准日
	 */
	public void setAssetBaseDate(java.lang.String assetBaseDate){
		this.assetBaseDate = assetBaseDate;
	}
	
	/**
	 * 评估报告出具日
	 */
	public java.lang.String getReportDate(){
		return reportDate;
	}
	
	/**
	 * 评估报告出具日
	 */
	public void setReportDate(java.lang.String reportDate){
		this.reportDate = reportDate;
	}
	
	/**
	 * 担保方式
	 */
	public java.lang.String getAssureMeans(){
		return assureMeans;
	}
	
	/**
	 * 担保方式
	 */
	public void setAssureMeans(java.lang.String assureMeans){
		this.assureMeans = assureMeans;
	}
	
	/**
	 * 保证人名称
	 */
	public java.lang.String getAssureName(){
		return assureName;
	}
	
	/**
	 * 保证人名称
	 */
	public void setAssureName(java.lang.String assureName){
		this.assureName = assureName;
	}
	
	/**
	 * 具体担保措施
	 */
	public java.lang.String getSpecificAssureMeans(){
		return specificAssureMeans;
	}
	
	/**
	 * 具体担保措施
	 */
	public void setSpecificAssureMeans(java.lang.String specificAssureMeans){
		this.specificAssureMeans = specificAssureMeans;
	}
	
	/**
	 * 房地产性质
	 */
	public java.lang.String getRealtyCharacter(){
		return realtyCharacter;
	}
	
	/**
	 * 房地产性质
	 */
	public void setRealtyCharacter(java.lang.String realtyCharacter){
		this.realtyCharacter = realtyCharacter;
	}
	
	/**
	 * 土地面积
	 */
	public java.lang.String getLandArea(){
		return landArea;
	}
	
	/**
	 * 土地面积
	 */
	public void setLandArea(java.lang.String landArea){
		this.landArea = landArea;
	}
	
	/**
	 * 房屋建筑面积
	 */
	public java.lang.String getBuildingArea(){
		return buildingArea;
	}
	
	/**
	 * 房屋建筑面积
	 */
	public void setBuildingArea(java.lang.String buildingArea){
		this.buildingArea = buildingArea;
	}
	
	/**
	 * 抵质押顺位
	 */
	public java.lang.String getPledgeSequence(){
		return pledgeSequence;
	}
	
	/**
	 * 抵质押顺位
	 */
	public void setPledgeSequence(java.lang.String pledgeSequence){
		this.pledgeSequence = pledgeSequence;
	}
	
	/**
	 * 其他担保方式情况说明
	 */
	public java.lang.String getGuaranteeThat(){
		return guaranteeThat;
	}
	
	/**
	 * 其他担保方式情况说明
	 */
	public void setGuaranteeThat(java.lang.String guaranteeThat){
		this.guaranteeThat = guaranteeThat;
	}
	
	/**
	 * 所处诉讼环节
	 */
	public java.lang.String getLitigationLink(){
		return litigationLink;
	}
	
	/**
	 * 所处诉讼环节
	 */
	public void setLitigationLink(java.lang.String litigationLink){
		this.litigationLink = litigationLink;
	}
	
	/**
	 * 是否采取司法保全
	 */
	public java.lang.String getHasGuarantee(){
		return hasGuarantee;
	}
	
	/**
	 * 是否采取司法保全
	 */
	public void setHasGuarantee(java.lang.String hasGuarantee){
		this.hasGuarantee = hasGuarantee;
	}
	
	/**
	 * 如已采取司法保全
	 */
	public java.lang.String getIfGuarantee(){
		return ifGuarantee;
	}
	
	/**
	 * 如已采取司法保全
	 */
	public void setIfGuarantee(java.lang.String ifGuarantee){
		this.ifGuarantee = ifGuarantee;
	}
	
	/**
	 * 项目亮点
	 */
	public java.lang.String getProjectWindow(){
		return projectWindow;
	}
	
	/**
	 * 项目亮点
	 */
	public void setProjectWindow(java.lang.String projectWindow){
		this.projectWindow = projectWindow;
	}
	
	/**
	 * 备注
	 */
	public java.lang.String getRemarks(){
		return remarks;
	}
	
	/**
	 * 备注
	 */
	public void setRemarks(java.lang.String remarks){
		this.remarks = remarks;
	}
	
	/**
	 * 资产描述
	 */
	public java.lang.String getAssetDesc(){
		return assetDesc;
	}
	
	/**
	 * 资产描述
	 */
	public void setAssetDesc(java.lang.String assetDesc){
		this.assetDesc = assetDesc;
	}
	
	/**
	 * 项目联系人
	 */
	public java.lang.String getContactPerson(){
		return contactPerson;
	}
	
	/**
	 * 项目联系人
	 */
	public void setContactPerson(java.lang.String contactPerson){
		this.contactPerson = contactPerson;
	}
	
	/**
	 * 联系电话
	 */
	public java.lang.String getContactPhone(){
		return contactPhone;
	}
	
	/**
	 * 联系电话
	 */
	public void setContactPhone(java.lang.String contactPhone){
		this.contactPhone = contactPhone;
	}
	
	/**
	 * 处置服务商
	 */
	public java.lang.String getDisposalService(){
		return disposalService;
	}
	
	/**
	 * 处置服务商
	 */
	public void setDisposalService(java.lang.String disposalService){
		this.disposalService = disposalService;
	}
	
	/**
	 * 资金服务商
	 */
	public java.lang.String getFundProvider(){
		return fundProvider;
	}
	
	/**
	 * 资金服务商
	 */
	public void setFundProvider(java.lang.String fundProvider){
		this.fundProvider = fundProvider;
	}

	public String getDebtorAreaId() {
		return debtorAreaId;
	}

	public void setDebtorAreaId(String debtorAreaId) {
		this.debtorAreaId = debtorAreaId;
	}

	public String getPawnAreaId() {
		return pawnAreaId;
	}

	public void setPawnAreaId(String pawnAreaId) {
		this.pawnAreaId = pawnAreaId;
	}
}
