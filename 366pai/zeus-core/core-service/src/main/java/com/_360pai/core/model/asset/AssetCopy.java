
package com._360pai.core.model.asset;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年10月16日 13时08分26秒
 */
public class AssetCopy implements java.io.Serializable{

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
	 * 标的所在地
	 */
	private Integer cityId;
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
	 * 标的所在地
	 */
	public Integer getCityId(){
		return cityId;
	}
	
	/**
	 * 标的所在地
	 */
	public void setCityId(Integer cityId){
		this.cityId = cityId;
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

}
