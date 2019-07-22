
package com.winback.core.model.payment;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月17日 13时31分40秒
 */
public class TInvoice implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 账户id
	 */
	private Integer accountId;
	/**
	 * 纸质/电子
	 */
	private String type;
	/**
	 * 抬头
	 */
	private String title;
	/**
	 * 抬头类型（user：ger，company：企业）
	 */
	private String titleType;
	/**
	 * 纳税人识别号
	 */
	private String taxpayerIdentificationNumber;
	/**
	 * 发票内容
	 */
	private String content;
	/**
	 * 物流公司
	 */
	private String amount;
	/**
	 * 备注信息
	 */
	private String memo;
	/**
	 * 收件人
	 */
	private String recipient;
	/**
	 * 联系电话
	 */
	private String contactPhone;
	/**
	 * 省id
	 */
	private Integer provinceId;
	/**
	 * 市id
	 */
	private Integer cityId;
	/**
	 * 区县id
	 */
	private Integer areaId;
	/**
	 * 地址详情
	 */
	private String addressDetail;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	
	/**
	 * 
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 账户id
	 */
	public Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 账户id
	 */
	public void setAccountId(Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 纸质/电子
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 纸质/电子
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 抬头
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * 抬头
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * 抬头类型（user：ger，company：企业）
	 */
	public String getTitleType(){
		return titleType;
	}
	
	/**
	 * 抬头类型（user：ger，company：企业）
	 */
	public void setTitleType(String titleType){
		this.titleType = titleType;
	}
	
	/**
	 * 纳税人识别号
	 */
	public String getTaxpayerIdentificationNumber(){
		return taxpayerIdentificationNumber;
	}
	
	/**
	 * 纳税人识别号
	 */
	public void setTaxpayerIdentificationNumber(String taxpayerIdentificationNumber){
		this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
	}
	
	/**
	 * 发票内容
	 */
	public String getContent(){
		return content;
	}
	
	/**
	 * 发票内容
	 */
	public void setContent(String content){
		this.content = content;
	}
	
	/**
	 * 物流公司
	 */
	public String getAmount(){
		return amount;
	}
	
	/**
	 * 物流公司
	 */
	public void setAmount(String amount){
		this.amount = amount;
	}
	
	/**
	 * 备注信息
	 */
	public String getMemo(){
		return memo;
	}
	
	/**
	 * 备注信息
	 */
	public void setMemo(String memo){
		this.memo = memo;
	}
	
	/**
	 * 收件人
	 */
	public String getRecipient(){
		return recipient;
	}
	
	/**
	 * 收件人
	 */
	public void setRecipient(String recipient){
		this.recipient = recipient;
	}
	
	/**
	 * 联系电话
	 */
	public String getContactPhone(){
		return contactPhone;
	}
	
	/**
	 * 联系电话
	 */
	public void setContactPhone(String contactPhone){
		this.contactPhone = contactPhone;
	}
	
	/**
	 * 省id
	 */
	public Integer getProvinceId(){
		return provinceId;
	}
	
	/**
	 * 省id
	 */
	public void setProvinceId(Integer provinceId){
		this.provinceId = provinceId;
	}
	
	/**
	 * 市id
	 */
	public Integer getCityId(){
		return cityId;
	}
	
	/**
	 * 市id
	 */
	public void setCityId(Integer cityId){
		this.cityId = cityId;
	}
	
	/**
	 * 区县id
	 */
	public Integer getAreaId(){
		return areaId;
	}
	
	/**
	 * 区县id
	 */
	public void setAreaId(Integer areaId){
		this.areaId = areaId;
	}
	
	/**
	 * 地址详情
	 */
	public String getAddressDetail(){
		return addressDetail;
	}
	
	/**
	 * 地址详情
	 */
	public void setAddressDetail(String addressDetail){
		this.addressDetail = addressDetail;
	}
	
	/**
	 * 邮箱
	 */
	public String getEmail(){
		return email;
	}
	
	/**
	 * 邮箱
	 */
	public void setEmail(String email){
		this.email = email;
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
	 * 创建时间
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	
	/**
	 * 更新时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 更新时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}
