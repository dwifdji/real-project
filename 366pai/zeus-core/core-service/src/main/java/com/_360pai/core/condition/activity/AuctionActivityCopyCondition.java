
package com._360pai.core.condition.activity;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年10月16日 13时43分01秒
 */
public class AuctionActivityCopyCondition implements DaoCondition{

	/**
	 * 自增ID
	 */
	private Integer id;
	/**
	 * 活动状态  PLATFORM_REVIEWING = 30  # 平台审核
	 */
	private String status;
	/**
	 * 活动CODE
	 */
	private String code;
	/**
	 * 出价次数
	 */
	private Integer bidCount;
	/**
	 * 报名人数
	 */
	private Integer participantNumber;
	/**
	 * 活动创建时间
	 */
	private java.util.Date createdAt;
	/**
	 * 活动更新时间
	 */
	private java.util.Date updatedAt;
	/**
	 * 标的ID
	 */
	private Integer assetId;
	/**
	 * 拍卖方式
	 */
	private String mode;
	/**
	 * 
	 */
	private String assetType;
	/**
	 * 标的名称
	 */
	private String assetName;
	/**
	 * 
	 */
	private Integer subCategoryId;
	/**
	 * 模板类型(asset_category_id)
	 */
	private Integer categoryId;
	/**
	 * 机构ID
	 */
	private Integer agencyId;
	/**
	 * 限制类型
	 */
	private String restrict;
	/**
	 * 预展时间
	 */
	private java.util.Date previewAt;
	/**
	 * 拍卖开始时间
	 */
	private java.util.Date beginAt;
	/**
	 * 是否强制终止
	 */
	private Boolean isForceFinished;
	/**
	 * 预计结束时间
	 */
	private java.util.Date endAt;
	/**
	 * 实际结束时间
	 */
	private java.util.Date finishAt;
	/**
	 * 保留价
	 */
	private java.math.BigDecimal reservePrice;
	/**
	 * 参考价
	 */
	private java.math.BigDecimal refPrice;
	/**
	 * 起拍价
	 */
	private java.math.BigDecimal startingPrice;
	/**
	 * 当前价
	 */
	private java.math.BigDecimal currentPrice;
	/**
	 * 加价幅度
	 */
	private java.math.BigDecimal increment;
	/**
	 * 延时分钟数
	 */
	private Integer biddingExtension;
	/**
	 * 延长期可以把这个字段设置成True，那么后续不再延期
	 */
	private Boolean lockEndAt;
	/**
	 * 是否进入延长期
	 */
	private Boolean extended;
	/**
	 * 每一个减价周期减少的价格
	 */
	private java.math.BigDecimal reduction;
	/**
	 * 减价周期, 用分钟计算
	 */
	private Integer reductionPeriod;
	/**
	 * 减价时间
	 */
	private java.util.Date reducedAt;
	/**
	 * 秒杀标的的个数, 只有秒杀才需要设置
	 */
	private Integer quantity;
	/**
	 * 是否卖方付款，默认都是买方付款，目前只有自由报价可能卖方付款
	 */
	private Boolean paidBySeller;
	/**
	 * 报名价
	 */
	private java.math.BigDecimal deposit;
	/**
	 * 佣金 卖方
	 */
	private java.math.BigDecimal commissionSeller;
	/**
	 * 佣金 买方
	 */
	private java.math.BigDecimal commissionBuyer;
	/**
	 * 卖方付的成交价百分比
	 */
	private java.math.BigDecimal commissionPercentSeller;
	/**
	 * 买方付的成交价百分比
	 */
	private java.math.BigDecimal commissionPercentBuyer;
	/**
	 * 缓存拍品类型
	 */
	private Integer assetCategoryGroupId;
	/**
	 * 缓存的拍品模板
	 */
	private Integer assetCategoryId;
	/**
	 * 拍品属性ID
	 */
	private Integer assetPropertyId;
	/**
	 * 从转让类转过来的服务类
	 */
	private Integer referenceActivityId;
	/**
	 * 拍卖师 姓名
	 */
	private String auctioneerName;
	/**
	 * 拍卖师 手机号
	 */
	private String auctioneerPhone;
	/**
	 * 拍卖师 QQ
	 */
	private String auctioneerQq;
	/**
	 * 展示名称
	 */
	private String displayName;
	/**
	 * 开始前一小时是否通知
	 */
	private Boolean beginNotified;
	/**
	 * 结束前一小时是否通知
	 */
	private Boolean endNotified;
	/**
	 * 佣金 第三方渠道
	 */
	private java.math.BigDecimal commissionChannelAgent;
	/**
	 * 操作时间
	 */
	private java.util.Date operatorAt;
	/**
	 * 审核人
	 */
	private Integer operatorId;
	/**
	 * 绑定staff
	 */
	private Integer staffId;
	/**
	 * 可见类型
	 */
	private String visibilityLevel;
	
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
	 * 活动状态  PLATFORM_REVIEWING = 30  # 平台审核
	 */
	public String getStatus(){
		return status;
	}
	
	/**
	 * 活动状态  PLATFORM_REVIEWING = 30  # 平台审核
	 */
	public void setStatus(String status){
		this.status = status;
	}
	
	/**
	 * 活动CODE
	 */
	public String getCode(){
		return code;
	}
	
	/**
	 * 活动CODE
	 */
	public void setCode(String code){
		this.code = code;
	}
	
	/**
	 * 出价次数
	 */
	public Integer getBidCount(){
		return bidCount;
	}
	
	/**
	 * 出价次数
	 */
	public void setBidCount(Integer bidCount){
		this.bidCount = bidCount;
	}
	
	/**
	 * 报名人数
	 */
	public Integer getParticipantNumber(){
		return participantNumber;
	}
	
	/**
	 * 报名人数
	 */
	public void setParticipantNumber(Integer participantNumber){
		this.participantNumber = participantNumber;
	}
	
	/**
	 * 活动创建时间
	 */
	public java.util.Date getCreatedAt(){
		return createdAt;
	}
	
	/**
	 * 活动创建时间
	 */
	public void setCreatedAt(java.util.Date createdAt){
		this.createdAt = createdAt;
	}
	
	/**
	 * 活动更新时间
	 */
	public java.util.Date getUpdatedAt(){
		return updatedAt;
	}
	
	/**
	 * 活动更新时间
	 */
	public void setUpdatedAt(java.util.Date updatedAt){
		this.updatedAt = updatedAt;
	}
	
	/**
	 * 标的ID
	 */
	public Integer getAssetId(){
		return assetId;
	}
	
	/**
	 * 标的ID
	 */
	public void setAssetId(Integer assetId){
		this.assetId = assetId;
	}
	
	/**
	 * 拍卖方式
	 */
	public String getMode(){
		return mode;
	}
	
	/**
	 * 拍卖方式
	 */
	public void setMode(String mode){
		this.mode = mode;
	}
	
	/**
	 * 
	 */
	public String getAssetType(){
		return assetType;
	}
	
	/**
	 * 
	 */
	public void setAssetType(String assetType){
		this.assetType = assetType;
	}
	
	/**
	 * 标的名称
	 */
	public String getAssetName(){
		return assetName;
	}
	
	/**
	 * 标的名称
	 */
	public void setAssetName(String assetName){
		this.assetName = assetName;
	}
	
	/**
	 * 
	 */
	public Integer getSubCategoryId(){
		return subCategoryId;
	}
	
	/**
	 * 
	 */
	public void setSubCategoryId(Integer subCategoryId){
		this.subCategoryId = subCategoryId;
	}
	
	/**
	 * 模板类型(asset_category_id)
	 */
	public Integer getCategoryId(){
		return categoryId;
	}
	
	/**
	 * 模板类型(asset_category_id)
	 */
	public void setCategoryId(Integer categoryId){
		this.categoryId = categoryId;
	}
	
	/**
	 * 机构ID
	 */
	public Integer getAgencyId(){
		return agencyId;
	}
	
	/**
	 * 机构ID
	 */
	public void setAgencyId(Integer agencyId){
		this.agencyId = agencyId;
	}
	
	/**
	 * 限制类型
	 */
	public String getRestrict(){
		return restrict;
	}
	
	/**
	 * 限制类型
	 */
	public void setRestrict(String restrict){
		this.restrict = restrict;
	}
	
	/**
	 * 预展时间
	 */
	public java.util.Date getPreviewAt(){
		return previewAt;
	}
	
	/**
	 * 预展时间
	 */
	public void setPreviewAt(java.util.Date previewAt){
		this.previewAt = previewAt;
	}
	
	/**
	 * 拍卖开始时间
	 */
	public java.util.Date getBeginAt(){
		return beginAt;
	}
	
	/**
	 * 拍卖开始时间
	 */
	public void setBeginAt(java.util.Date beginAt){
		this.beginAt = beginAt;
	}
	
	/**
	 * 是否强制终止
	 */
	public Boolean getIsForceFinished(){
		return isForceFinished;
	}
	
	/**
	 * 是否强制终止
	 */
	public void setIsForceFinished(Boolean isForceFinished){
		this.isForceFinished = isForceFinished;
	}
	
	/**
	 * 预计结束时间
	 */
	public java.util.Date getEndAt(){
		return endAt;
	}
	
	/**
	 * 预计结束时间
	 */
	public void setEndAt(java.util.Date endAt){
		this.endAt = endAt;
	}
	
	/**
	 * 实际结束时间
	 */
	public java.util.Date getFinishAt(){
		return finishAt;
	}
	
	/**
	 * 实际结束时间
	 */
	public void setFinishAt(java.util.Date finishAt){
		this.finishAt = finishAt;
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
	 * 当前价
	 */
	public java.math.BigDecimal getCurrentPrice(){
		return currentPrice;
	}
	
	/**
	 * 当前价
	 */
	public void setCurrentPrice(java.math.BigDecimal currentPrice){
		this.currentPrice = currentPrice;
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
	 * 延时分钟数
	 */
	public Integer getBiddingExtension(){
		return biddingExtension;
	}
	
	/**
	 * 延时分钟数
	 */
	public void setBiddingExtension(Integer biddingExtension){
		this.biddingExtension = biddingExtension;
	}
	
	/**
	 * 延长期可以把这个字段设置成True，那么后续不再延期
	 */
	public Boolean getLockEndAt(){
		return lockEndAt;
	}
	
	/**
	 * 延长期可以把这个字段设置成True，那么后续不再延期
	 */
	public void setLockEndAt(Boolean lockEndAt){
		this.lockEndAt = lockEndAt;
	}
	
	/**
	 * 是否进入延长期
	 */
	public Boolean getExtended(){
		return extended;
	}
	
	/**
	 * 是否进入延长期
	 */
	public void setExtended(Boolean extended){
		this.extended = extended;
	}
	
	/**
	 * 每一个减价周期减少的价格
	 */
	public java.math.BigDecimal getReduction(){
		return reduction;
	}
	
	/**
	 * 每一个减价周期减少的价格
	 */
	public void setReduction(java.math.BigDecimal reduction){
		this.reduction = reduction;
	}
	
	/**
	 * 减价周期, 用分钟计算
	 */
	public Integer getReductionPeriod(){
		return reductionPeriod;
	}
	
	/**
	 * 减价周期, 用分钟计算
	 */
	public void setReductionPeriod(Integer reductionPeriod){
		this.reductionPeriod = reductionPeriod;
	}
	
	/**
	 * 减价时间
	 */
	public java.util.Date getReducedAt(){
		return reducedAt;
	}
	
	/**
	 * 减价时间
	 */
	public void setReducedAt(java.util.Date reducedAt){
		this.reducedAt = reducedAt;
	}
	
	/**
	 * 秒杀标的的个数, 只有秒杀才需要设置
	 */
	public Integer getQuantity(){
		return quantity;
	}
	
	/**
	 * 秒杀标的的个数, 只有秒杀才需要设置
	 */
	public void setQuantity(Integer quantity){
		this.quantity = quantity;
	}
	
	/**
	 * 是否卖方付款，默认都是买方付款，目前只有自由报价可能卖方付款
	 */
	public Boolean getPaidBySeller(){
		return paidBySeller;
	}
	
	/**
	 * 是否卖方付款，默认都是买方付款，目前只有自由报价可能卖方付款
	 */
	public void setPaidBySeller(Boolean paidBySeller){
		this.paidBySeller = paidBySeller;
	}
	
	/**
	 * 报名价
	 */
	public java.math.BigDecimal getDeposit(){
		return deposit;
	}
	
	/**
	 * 报名价
	 */
	public void setDeposit(java.math.BigDecimal deposit){
		this.deposit = deposit;
	}
	
	/**
	 * 佣金 卖方
	 */
	public java.math.BigDecimal getCommissionSeller(){
		return commissionSeller;
	}
	
	/**
	 * 佣金 卖方
	 */
	public void setCommissionSeller(java.math.BigDecimal commissionSeller){
		this.commissionSeller = commissionSeller;
	}
	
	/**
	 * 佣金 买方
	 */
	public java.math.BigDecimal getCommissionBuyer(){
		return commissionBuyer;
	}
	
	/**
	 * 佣金 买方
	 */
	public void setCommissionBuyer(java.math.BigDecimal commissionBuyer){
		this.commissionBuyer = commissionBuyer;
	}
	
	/**
	 * 卖方付的成交价百分比
	 */
	public java.math.BigDecimal getCommissionPercentSeller(){
		return commissionPercentSeller;
	}
	
	/**
	 * 卖方付的成交价百分比
	 */
	public void setCommissionPercentSeller(java.math.BigDecimal commissionPercentSeller){
		this.commissionPercentSeller = commissionPercentSeller;
	}
	
	/**
	 * 买方付的成交价百分比
	 */
	public java.math.BigDecimal getCommissionPercentBuyer(){
		return commissionPercentBuyer;
	}
	
	/**
	 * 买方付的成交价百分比
	 */
	public void setCommissionPercentBuyer(java.math.BigDecimal commissionPercentBuyer){
		this.commissionPercentBuyer = commissionPercentBuyer;
	}
	
	/**
	 * 缓存拍品类型
	 */
	public Integer getAssetCategoryGroupId(){
		return assetCategoryGroupId;
	}
	
	/**
	 * 缓存拍品类型
	 */
	public void setAssetCategoryGroupId(Integer assetCategoryGroupId){
		this.assetCategoryGroupId = assetCategoryGroupId;
	}
	
	/**
	 * 缓存的拍品模板
	 */
	public Integer getAssetCategoryId(){
		return assetCategoryId;
	}
	
	/**
	 * 缓存的拍品模板
	 */
	public void setAssetCategoryId(Integer assetCategoryId){
		this.assetCategoryId = assetCategoryId;
	}
	
	/**
	 * 拍品属性ID
	 */
	public Integer getAssetPropertyId(){
		return assetPropertyId;
	}
	
	/**
	 * 拍品属性ID
	 */
	public void setAssetPropertyId(Integer assetPropertyId){
		this.assetPropertyId = assetPropertyId;
	}
	
	/**
	 * 从转让类转过来的服务类
	 */
	public Integer getReferenceActivityId(){
		return referenceActivityId;
	}
	
	/**
	 * 从转让类转过来的服务类
	 */
	public void setReferenceActivityId(Integer referenceActivityId){
		this.referenceActivityId = referenceActivityId;
	}
	
	/**
	 * 拍卖师 姓名
	 */
	public String getAuctioneerName(){
		return auctioneerName;
	}
	
	/**
	 * 拍卖师 姓名
	 */
	public void setAuctioneerName(String auctioneerName){
		this.auctioneerName = auctioneerName;
	}
	
	/**
	 * 拍卖师 手机号
	 */
	public String getAuctioneerPhone(){
		return auctioneerPhone;
	}
	
	/**
	 * 拍卖师 手机号
	 */
	public void setAuctioneerPhone(String auctioneerPhone){
		this.auctioneerPhone = auctioneerPhone;
	}
	
	/**
	 * 拍卖师 QQ
	 */
	public String getAuctioneerQq(){
		return auctioneerQq;
	}
	
	/**
	 * 拍卖师 QQ
	 */
	public void setAuctioneerQq(String auctioneerQq){
		this.auctioneerQq = auctioneerQq;
	}
	
	/**
	 * 展示名称
	 */
	public String getDisplayName(){
		return displayName;
	}
	
	/**
	 * 展示名称
	 */
	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}
	
	/**
	 * 开始前一小时是否通知
	 */
	public Boolean getBeginNotified(){
		return beginNotified;
	}
	
	/**
	 * 开始前一小时是否通知
	 */
	public void setBeginNotified(Boolean beginNotified){
		this.beginNotified = beginNotified;
	}
	
	/**
	 * 结束前一小时是否通知
	 */
	public Boolean getEndNotified(){
		return endNotified;
	}
	
	/**
	 * 结束前一小时是否通知
	 */
	public void setEndNotified(Boolean endNotified){
		this.endNotified = endNotified;
	}
	
	/**
	 * 佣金 第三方渠道
	 */
	public java.math.BigDecimal getCommissionChannelAgent(){
		return commissionChannelAgent;
	}
	
	/**
	 * 佣金 第三方渠道
	 */
	public void setCommissionChannelAgent(java.math.BigDecimal commissionChannelAgent){
		this.commissionChannelAgent = commissionChannelAgent;
	}
	
	/**
	 * 操作时间
	 */
	public java.util.Date getOperatorAt(){
		return operatorAt;
	}
	
	/**
	 * 操作时间
	 */
	public void setOperatorAt(java.util.Date operatorAt){
		this.operatorAt = operatorAt;
	}
	
	/**
	 * 审核人
	 */
	public Integer getOperatorId(){
		return operatorId;
	}
	
	/**
	 * 审核人
	 */
	public void setOperatorId(Integer operatorId){
		this.operatorId = operatorId;
	}
	
	/**
	 * 绑定staff
	 */
	public Integer getStaffId(){
		return staffId;
	}
	
	/**
	 * 绑定staff
	 */
	public void setStaffId(Integer staffId){
		this.staffId = staffId;
	}
	
	/**
	 * 可见类型
	 */
	public String getVisibilityLevel(){
		return visibilityLevel;
	}
	
	/**
	 * 可见类型
	 */
	public void setVisibilityLevel(String visibilityLevel){
		this.visibilityLevel = visibilityLevel;
	}

}