
package com.winback.core.condition.payment;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月16日 15时40分03秒
 */
public class TFinanceInvoiceCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 案件id
	 */
	private java.lang.Integer caseId;
	/**
	 * 发票类型 1 增值税专用 2 增值税普通发票
	 */
	private java.lang.Integer type;
	/**
	 * 发票金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 公司名称
	 */
	private java.lang.String comName;
	/**
	 * 公司税号
	 */
	private java.lang.String comDuty;
	/**
	 * 联系方式
	 */
	private java.lang.String phone;
	/**
	 * 开票日期
	 */
	private java.util.Date invoiceTime;
	/**
	 * 提交人id
	 */
	private java.lang.Integer operId;
	/**
	 * 提交人名称
	 */
	private java.lang.String operName;
	/**
	 * 财务人员id
	 */
	private java.lang.Integer finId;
	/**
	 * 财务人员名称
	 */
	private java.lang.String finName;
	/**
	 * 发票凭证
	 */
	private java.lang.String certificate;
	/**
	 * 状态
	 */
	private java.lang.Integer status;
	/**
	 * 删除标志0 不删除 1删除
	 */
	private java.lang.Boolean deleteFlag;
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
	 * 案件id
	 */
	public java.lang.Integer getCaseId(){
		return caseId;
	}
	
	/**
	 * 案件id
	 */
	public void setCaseId(java.lang.Integer caseId){
		this.caseId = caseId;
	}
	
	/**
	 * 发票类型 1 增值税专用 2 增值税普通发票
	 */
	public java.lang.Integer getType(){
		return type;
	}
	
	/**
	 * 发票类型 1 增值税专用 2 增值税普通发票
	 */
	public void setType(java.lang.Integer type){
		this.type = type;
	}
	
	/**
	 * 发票金额
	 */
	public java.math.BigDecimal getAmount(){
		return amount;
	}
	
	/**
	 * 发票金额
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}
	
	/**
	 * 公司名称
	 */
	public java.lang.String getComName(){
		return comName;
	}
	
	/**
	 * 公司名称
	 */
	public void setComName(java.lang.String comName){
		this.comName = comName;
	}
	
	/**
	 * 公司税号
	 */
	public java.lang.String getComDuty(){
		return comDuty;
	}
	
	/**
	 * 公司税号
	 */
	public void setComDuty(java.lang.String comDuty){
		this.comDuty = comDuty;
	}
	
	/**
	 * 联系方式
	 */
	public java.lang.String getPhone(){
		return phone;
	}
	
	/**
	 * 联系方式
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	
	/**
	 * 开票日期
	 */
	public java.util.Date getInvoiceTime(){
		return invoiceTime;
	}
	
	/**
	 * 开票日期
	 */
	public void setInvoiceTime(java.util.Date invoiceTime){
		this.invoiceTime = invoiceTime;
	}
	
	/**
	 * 提交人id
	 */
	public java.lang.Integer getOperId(){
		return operId;
	}
	
	/**
	 * 提交人id
	 */
	public void setOperId(java.lang.Integer operId){
		this.operId = operId;
	}
	
	/**
	 * 提交人名称
	 */
	public java.lang.String getOperName(){
		return operName;
	}
	
	/**
	 * 提交人名称
	 */
	public void setOperName(java.lang.String operName){
		this.operName = operName;
	}
	
	/**
	 * 财务人员id
	 */
	public java.lang.Integer getFinId(){
		return finId;
	}
	
	/**
	 * 财务人员id
	 */
	public void setFinId(java.lang.Integer finId){
		this.finId = finId;
	}
	
	/**
	 * 财务人员名称
	 */
	public java.lang.String getFinName(){
		return finName;
	}
	
	/**
	 * 财务人员名称
	 */
	public void setFinName(java.lang.String finName){
		this.finName = finName;
	}
	
	/**
	 * 发票凭证
	 */
	public java.lang.String getCertificate(){
		return certificate;
	}
	
	/**
	 * 发票凭证
	 */
	public void setCertificate(java.lang.String certificate){
		this.certificate = certificate;
	}
	
	/**
	 * 状态
	 */
	public java.lang.Integer getStatus(){
		return status;
	}
	
	/**
	 * 状态
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
	
	/**
	 * 删除标志0 不删除 1删除
	 */
	public java.lang.Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志0 不删除 1删除
	 */
	public void setDeleteFlag(java.lang.Boolean deleteFlag){
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