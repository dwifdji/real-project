
package com._360pai.core.condition.enrolling;


import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年05月20日 15时39分44秒
 */
public class EnrollingActivityImportRealDataCondition implements DaoCondition {

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
	 * 所在地
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
	 * 省份id
	 */
	private java.lang.String pawnProId;
	/**
	 * 城市id
	 */
	private java.lang.String pawnCityId;
	/**
	 * 地区id
	 */
	private java.lang.String pawnAreaId;
	/**
	 * 市场参考价
	 */
	private java.lang.String refPrice;
	/**
	 * 商品类型
	 */
	private java.lang.String busTypeName;
	/**
	 * 权力人类型
	 */
	private java.lang.String powerType;
	/**
	 * 共有情况
	 */
	private java.lang.String ownedCondition;
	/**
	 * 共有数量
	 */
	private java.lang.String ownedNum;
	/**
	 * 所有权来源
	 */
	private java.lang.String ownedSource;
	/**
	 * 房地产权证号
	 */
	private java.lang.String realLicense;
	/**
	 * 土地使用权证号
	 */
	private java.lang.String landLicense;
	/**
	 * 建筑面积
	 */
	private java.lang.String buildArea;
	/**
	 * 登记日期
	 */
	private java.lang.String recordDate;
	/**
	 * 房屋类型
	 */
	private java.lang.String housingType;
	/**
	 * 套内面积
	 */
	private java.lang.String insideSpace;
	/**
	 * 装修程度
	 */
	private java.lang.String decorate;
	/**
	 * 竣工日期
	 */
	private java.lang.String completionDate;
	/**
	 * 总层数
	 */
	private java.lang.String totalNum;
	/**
	 * 所在层数
	 */
	private java.lang.String storyNum;
	/**
	 * 承担方式
	 */
	private java.lang.String bearWay;
	/**
	 * 公用事业欠费
	 */
	private java.lang.String publicOwe;
	/**
	 * 物业管理欠费
	 */
	private java.lang.String tenementOwe;
	/**
	 * 增值税及附加
	 */
	private java.lang.String vatAddition;
	/**
	 * 土地增值税
	 */
	private java.lang.String landVat;
	/**
	 * 个人所得税
	 */
	private java.lang.String personalVat;
	/**
	 * 备注
	 */
	private java.lang.String remark;
	/**
	 * 土地面积
	 */
	private java.lang.String landArea;
	/**
	 * 分摊面积
	 */
	private java.lang.String sharingArea;
	/**
	 * 土地用途
	 */
	private java.lang.String landUse;
	/**
	 * 使用权来源
	 */
	private java.lang.String useSource;
	/**
	 * 使用期限
	 */
	private java.lang.String usePeriod;
	/**
	 * 是否有抵押
	 */
	private java.lang.String ifMortgage;
	/**
	 * 权利人
	 */
	private java.lang.String holder;
	/**
	 * 权利种类
	 */
	private java.lang.String rightType;
	/**
	 * 他项产权号
	 */
	private java.lang.String otherEquity;
	/**
	 * 权利价值
	 */
	private java.lang.String rightValue;
	/**
	 * 使用情况
	 */
	private java.lang.String usageType;
	/**
	 * 户籍/工商注册
	 */
	private java.lang.String registration;
	/**
	 * 户籍数/工商注册数
	 */
	private java.lang.String registrationNum;
	/**
	 * 租赁情况
	 */
	private java.lang.String leaseCondition;
	/**
	 * 租赁期限
	 */
	private java.lang.String leaseDeadline;
	/**
	 * 使用人
	 */
	private java.lang.String leaseUser;
	/**
	 * 押金
	 */
	private java.lang.String leaseDeposit;
	/**
	 * 租金
	 */
	private java.lang.String leaseRent;
	/**
	 * 租金支付至
	 */
	private java.lang.String leasePayTo;
	/**
	 * 与当事人关系
	 */
	private java.lang.String relationship;
	/**
	 * 室内物品
	 */
	private java.lang.String indoorGoods;
	/**
	 * 联系电话
	 */
	private java.lang.String leasePhone;
	/**
	 * 备注
	 */
	private java.lang.String leaseRemark;
	/**
	 * 有无限制
	 */
	private java.lang.String unlimited;
	/**
	 * 限制人
	 */
	private java.lang.String limitOne;
	/**
	 * 限制方式
	 */
	private java.lang.String limitType;
	/**
	 * 其他限制
	 */
	private java.lang.String limitOther;
	/**
	 * 限制备注
	 */
	private java.lang.String limitRemark;
	/**
	 * 处置服务商
	 */
	private java.lang.String disposalService;
	/**
	 * 资金服务商
	 */
	private java.lang.String fundProvider;


	private java.lang.String contactName;


	private java.lang.String contactPhone;

	/**
	 * 上传业务人员ID
	 */
	private java.lang.Integer operatorId;
	/**
	 * 
	 */
	private java.lang.String extra;
	/**
	 * 
	 */
	private java.util.Date beginAt;
	/**
	 * 
	 */
	private java.util.Date endAt;
	/**
	 * 
	 */
	private java.lang.Boolean deleteFlag;
	/**
	 * 
	 */
	private java.util.Date createTime;
	/**
	 * 
	 */
	private java.util.Date updateTime;


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
	 * 所在地
	 */
	public java.lang.String getPawnLocation(){
		return pawnLocation;
	}
	
	/**
	 * 所在地
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
	 * 省份id
	 */
	public java.lang.String getPawnProId(){
		return pawnProId;
	}
	
	/**
	 * 省份id
	 */
	public void setPawnProId(java.lang.String pawnProId){
		this.pawnProId = pawnProId;
	}
	
	/**
	 * 城市id
	 */
	public java.lang.String getPawnCityId(){
		return pawnCityId;
	}
	
	/**
	 * 城市id
	 */
	public void setPawnCityId(java.lang.String pawnCityId){
		this.pawnCityId = pawnCityId;
	}
	
	/**
	 * 地区id
	 */
	public java.lang.String getPawnAreaId(){
		return pawnAreaId;
	}
	
	/**
	 * 地区id
	 */
	public void setPawnAreaId(java.lang.String pawnAreaId){
		this.pawnAreaId = pawnAreaId;
	}
	
	/**
	 * 市场参考价
	 */
	public java.lang.String getRefPrice(){
		return refPrice;
	}
	
	/**
	 * 市场参考价
	 */
	public void setRefPrice(java.lang.String refPrice){
		this.refPrice = refPrice;
	}
	
	/**
	 * 商品类型
	 */
	public java.lang.String getBusTypeName(){
		return busTypeName;
	}
	
	/**
	 * 商品类型
	 */
	public void setBusTypeName(java.lang.String busTypeName){
		this.busTypeName = busTypeName;
	}
	
	/**
	 * 权力人类型
	 */
	public java.lang.String getPowerType(){
		return powerType;
	}
	
	/**
	 * 权力人类型
	 */
	public void setPowerType(java.lang.String powerType){
		this.powerType = powerType;
	}
	
	/**
	 * 共有情况
	 */
	public java.lang.String getOwnedCondition(){
		return ownedCondition;
	}
	
	/**
	 * 共有情况
	 */
	public void setOwnedCondition(java.lang.String ownedCondition){
		this.ownedCondition = ownedCondition;
	}
	
	/**
	 * 共有数量
	 */
	public java.lang.String getOwnedNum(){
		return ownedNum;
	}
	
	/**
	 * 共有数量
	 */
	public void setOwnedNum(java.lang.String ownedNum){
		this.ownedNum = ownedNum;
	}
	
	/**
	 * 所有权来源
	 */
	public java.lang.String getOwnedSource(){
		return ownedSource;
	}
	
	/**
	 * 所有权来源
	 */
	public void setOwnedSource(java.lang.String ownedSource){
		this.ownedSource = ownedSource;
	}
	
	/**
	 * 房地产权证号
	 */
	public java.lang.String getRealLicense(){
		return realLicense;
	}
	
	/**
	 * 房地产权证号
	 */
	public void setRealLicense(java.lang.String realLicense){
		this.realLicense = realLicense;
	}
	
	/**
	 * 土地使用权证号
	 */
	public java.lang.String getLandLicense(){
		return landLicense;
	}
	
	/**
	 * 土地使用权证号
	 */
	public void setLandLicense(java.lang.String landLicense){
		this.landLicense = landLicense;
	}
	
	/**
	 * 建筑面积
	 */
	public java.lang.String getBuildArea(){
		return buildArea;
	}
	
	/**
	 * 建筑面积
	 */
	public void setBuildArea(java.lang.String buildArea){
		this.buildArea = buildArea;
	}
	
	/**
	 * 登记日期
	 */
	public java.lang.String getRecordDate(){
		return recordDate;
	}
	
	/**
	 * 登记日期
	 */
	public void setRecordDate(java.lang.String recordDate){
		this.recordDate = recordDate;
	}
	
	/**
	 * 房屋类型
	 */
	public java.lang.String getHousingType(){
		return housingType;
	}
	
	/**
	 * 房屋类型
	 */
	public void setHousingType(java.lang.String housingType){
		this.housingType = housingType;
	}
	
	/**
	 * 套内面积
	 */
	public java.lang.String getInsideSpace(){
		return insideSpace;
	}
	
	/**
	 * 套内面积
	 */
	public void setInsideSpace(java.lang.String insideSpace){
		this.insideSpace = insideSpace;
	}
	
	/**
	 * 装修程度
	 */
	public java.lang.String getDecorate(){
		return decorate;
	}
	
	/**
	 * 装修程度
	 */
	public void setDecorate(java.lang.String decorate){
		this.decorate = decorate;
	}
	
	/**
	 * 竣工日期
	 */
	public java.lang.String getCompletionDate(){
		return completionDate;
	}
	
	/**
	 * 竣工日期
	 */
	public void setCompletionDate(java.lang.String completionDate){
		this.completionDate = completionDate;
	}
	
	/**
	 * 总层数
	 */
	public java.lang.String getTotalNum(){
		return totalNum;
	}
	
	/**
	 * 总层数
	 */
	public void setTotalNum(java.lang.String totalNum){
		this.totalNum = totalNum;
	}
	
	/**
	 * 所在层数
	 */
	public java.lang.String getStoryNum(){
		return storyNum;
	}
	
	/**
	 * 所在层数
	 */
	public void setStoryNum(java.lang.String storyNum){
		this.storyNum = storyNum;
	}
	
	/**
	 * 承担方式
	 */
	public java.lang.String getBearWay(){
		return bearWay;
	}
	
	/**
	 * 承担方式
	 */
	public void setBearWay(java.lang.String bearWay){
		this.bearWay = bearWay;
	}
	
	/**
	 * 公用事业欠费
	 */
	public java.lang.String getPublicOwe(){
		return publicOwe;
	}
	
	/**
	 * 公用事业欠费
	 */
	public void setPublicOwe(java.lang.String publicOwe){
		this.publicOwe = publicOwe;
	}
	
	/**
	 * 物业管理欠费
	 */
	public java.lang.String getTenementOwe(){
		return tenementOwe;
	}
	
	/**
	 * 物业管理欠费
	 */
	public void setTenementOwe(java.lang.String tenementOwe){
		this.tenementOwe = tenementOwe;
	}
	
	/**
	 * 增值税及附加
	 */
	public java.lang.String getVatAddition(){
		return vatAddition;
	}
	
	/**
	 * 增值税及附加
	 */
	public void setVatAddition(java.lang.String vatAddition){
		this.vatAddition = vatAddition;
	}
	
	/**
	 * 土地增值税
	 */
	public java.lang.String getLandVat(){
		return landVat;
	}
	
	/**
	 * 土地增值税
	 */
	public void setLandVat(java.lang.String landVat){
		this.landVat = landVat;
	}
	
	/**
	 * 个人所得税
	 */
	public java.lang.String getPersonalVat(){
		return personalVat;
	}
	
	/**
	 * 个人所得税
	 */
	public void setPersonalVat(java.lang.String personalVat){
		this.personalVat = personalVat;
	}
	
	/**
	 * 备注
	 */
	public java.lang.String getRemark(){
		return remark;
	}
	
	/**
	 * 备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
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
	 * 分摊面积
	 */
	public java.lang.String getSharingArea(){
		return sharingArea;
	}
	
	/**
	 * 分摊面积
	 */
	public void setSharingArea(java.lang.String sharingArea){
		this.sharingArea = sharingArea;
	}
	
	/**
	 * 土地用途
	 */
	public java.lang.String getLandUse(){
		return landUse;
	}
	
	/**
	 * 土地用途
	 */
	public void setLandUse(java.lang.String landUse){
		this.landUse = landUse;
	}
	
	/**
	 * 使用权来源
	 */
	public java.lang.String getUseSource(){
		return useSource;
	}
	
	/**
	 * 使用权来源
	 */
	public void setUseSource(java.lang.String useSource){
		this.useSource = useSource;
	}
	
	/**
	 * 使用期限
	 */
	public java.lang.String getUsePeriod(){
		return usePeriod;
	}
	
	/**
	 * 使用期限
	 */
	public void setUsePeriod(java.lang.String usePeriod){
		this.usePeriod = usePeriod;
	}
	
	/**
	 * 是否有抵押
	 */
	public java.lang.String getIfMortgage(){
		return ifMortgage;
	}
	
	/**
	 * 是否有抵押
	 */
	public void setIfMortgage(java.lang.String ifMortgage){
		this.ifMortgage = ifMortgage;
	}
	
	/**
	 * 权利人
	 */
	public java.lang.String getHolder(){
		return holder;
	}
	
	/**
	 * 权利人
	 */
	public void setHolder(java.lang.String holder){
		this.holder = holder;
	}
	
	/**
	 * 权利种类
	 */
	public java.lang.String getRightType(){
		return rightType;
	}
	
	/**
	 * 权利种类
	 */
	public void setRightType(java.lang.String rightType){
		this.rightType = rightType;
	}
	
	/**
	 * 他项产权号
	 */
	public java.lang.String getOtherEquity(){
		return otherEquity;
	}
	
	/**
	 * 他项产权号
	 */
	public void setOtherEquity(java.lang.String otherEquity){
		this.otherEquity = otherEquity;
	}
	
	/**
	 * 权利价值
	 */
	public java.lang.String getRightValue(){
		return rightValue;
	}
	
	/**
	 * 权利价值
	 */
	public void setRightValue(java.lang.String rightValue){
		this.rightValue = rightValue;
	}


	public String getUsageType() {
		return usageType;
	}

	public void setUsageType(String usageType) {
		this.usageType = usageType;
	}

	/**
	 * 户籍/工商注册
	 */
	public java.lang.String getRegistration(){
		return registration;
	}
	
	/**
	 * 户籍/工商注册
	 */
	public void setRegistration(java.lang.String registration){
		this.registration = registration;
	}
	
	/**
	 * 户籍数/工商注册数
	 */
	public java.lang.String getRegistrationNum(){
		return registrationNum;
	}
	
	/**
	 * 户籍数/工商注册数
	 */
	public void setRegistrationNum(java.lang.String registrationNum){
		this.registrationNum = registrationNum;
	}
	
	/**
	 * 租赁情况
	 */
	public java.lang.String getLeaseCondition(){
		return leaseCondition;
	}
	
	/**
	 * 租赁情况
	 */
	public void setLeaseCondition(java.lang.String leaseCondition){
		this.leaseCondition = leaseCondition;
	}
	
	/**
	 * 租赁期限
	 */
	public java.lang.String getLeaseDeadline(){
		return leaseDeadline;
	}
	
	/**
	 * 租赁期限
	 */
	public void setLeaseDeadline(java.lang.String leaseDeadline){
		this.leaseDeadline = leaseDeadline;
	}
	
	/**
	 * 使用人
	 */
	public java.lang.String getLeaseUser(){
		return leaseUser;
	}
	
	/**
	 * 使用人
	 */
	public void setLeaseUser(java.lang.String leaseUser){
		this.leaseUser = leaseUser;
	}
	
	/**
	 * 押金
	 */
	public java.lang.String getLeaseDeposit(){
		return leaseDeposit;
	}
	
	/**
	 * 押金
	 */
	public void setLeaseDeposit(java.lang.String leaseDeposit){
		this.leaseDeposit = leaseDeposit;
	}
	
	/**
	 * 租金
	 */
	public java.lang.String getLeaseRent(){
		return leaseRent;
	}
	
	/**
	 * 租金
	 */
	public void setLeaseRent(java.lang.String leaseRent){
		this.leaseRent = leaseRent;
	}
	
	/**
	 * 租金支付至
	 */
	public java.lang.String getLeasePayTo(){
		return leasePayTo;
	}
	
	/**
	 * 租金支付至
	 */
	public void setLeasePayTo(java.lang.String leasePayTo){
		this.leasePayTo = leasePayTo;
	}
	
	/**
	 * 与当事人关系
	 */
	public java.lang.String getRelationship(){
		return relationship;
	}
	
	/**
	 * 与当事人关系
	 */
	public void setRelationship(java.lang.String relationship){
		this.relationship = relationship;
	}
	
	/**
	 * 室内物品
	 */
	public java.lang.String getIndoorGoods(){
		return indoorGoods;
	}
	
	/**
	 * 室内物品
	 */
	public void setIndoorGoods(java.lang.String indoorGoods){
		this.indoorGoods = indoorGoods;
	}
	
	/**
	 * 联系电话
	 */
	public java.lang.String getLeasePhone(){
		return leasePhone;
	}
	
	/**
	 * 联系电话
	 */
	public void setLeasePhone(java.lang.String leasePhone){
		this.leasePhone = leasePhone;
	}
	
	/**
	 * 备注
	 */
	public java.lang.String getLeaseRemark(){
		return leaseRemark;
	}
	
	/**
	 * 备注
	 */
	public void setLeaseRemark(java.lang.String leaseRemark){
		this.leaseRemark = leaseRemark;
	}
	
	/**
	 * 有无限制
	 */
	public java.lang.String getUnlimited(){
		return unlimited;
	}
	
	/**
	 * 有无限制
	 */
	public void setUnlimited(java.lang.String unlimited){
		this.unlimited = unlimited;
	}
	
	/**
	 * 限制人
	 */
	public java.lang.String getLimitOne(){
		return limitOne;
	}
	
	/**
	 * 限制人
	 */
	public void setLimitOne(java.lang.String limitOne){
		this.limitOne = limitOne;
	}
	
	/**
	 * 限制方式
	 */
	public java.lang.String getLimitType(){
		return limitType;
	}
	
	/**
	 * 限制方式
	 */
	public void setLimitType(java.lang.String limitType){
		this.limitType = limitType;
	}
	
	/**
	 * 其他限制
	 */
	public java.lang.String getLimitOther(){
		return limitOther;
	}
	
	/**
	 * 其他限制
	 */
	public void setLimitOther(java.lang.String limitOther){
		this.limitOther = limitOther;
	}
	
	/**
	 * 限制备注
	 */
	public java.lang.String getLimitRemark(){
		return limitRemark;
	}
	
	/**
	 * 限制备注
	 */
	public void setLimitRemark(java.lang.String limitRemark){
		this.limitRemark = limitRemark;
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
	
	/**
	 * 上传业务人员ID
	 */
	public java.lang.Integer getOperatorId(){
		return operatorId;
	}
	
	/**
	 * 上传业务人员ID
	 */
	public void setOperatorId(java.lang.Integer operatorId){
		this.operatorId = operatorId;
	}


	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	/**
	 * 
	 */
	public java.util.Date getBeginAt(){
		return beginAt;
	}
	
	/**
	 * 
	 */
	public void setBeginAt(java.util.Date beginAt){
		this.beginAt = beginAt;
	}
	
	/**
	 * 
	 */
	public java.util.Date getEndAt(){
		return endAt;
	}
	
	/**
	 * 
	 */
	public void setEndAt(java.util.Date endAt){
		this.endAt = endAt;
	}
	
	/**
	 * 
	 */
	public java.lang.Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 
	 */
	public void setDeleteFlag(java.lang.Boolean deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	
	/**
	 * 
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}