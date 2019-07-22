
package com.winback.core.condition.payment;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
public class TFinanceReceivableCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 案件id
	 */
	private java.lang.Integer caseId;
	/**
	 * 回款类型 1 收入 2 退费
	 */
	private java.lang.Integer type;
	/**
	 * 回款金额
	 */
	private java.math.BigDecimal amount;
	/**
	 * 案件回款
	 */
	private java.math.BigDecimal caseAmount;
	/**
	 * 应收费用
	 */
	private java.math.BigDecimal cost;
	/**
	 * 回款描述
	 */
	private java.lang.String msg;
	/**
	 * 回款凭证
	 */
	private java.lang.String certificate;
	/**
	 * 操作员id
	 */
	private java.lang.Integer operId;
	/**
	 * 操作员名称
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
	 * 回款类型 1 收入 2 退费
	 */
	public java.lang.Integer getType(){
		return type;
	}
	
	/**
	 * 回款类型 1 收入 2 退费
	 */
	public void setType(java.lang.Integer type){
		this.type = type;
	}
	
	/**
	 * 回款金额
	 */
	public java.math.BigDecimal getAmount(){
		return amount;
	}
	
	/**
	 * 回款金额
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}
	
	/**
	 * 案件回款
	 */
	public java.math.BigDecimal getCaseAmount(){
		return caseAmount;
	}
	
	/**
	 * 案件回款
	 */
	public void setCaseAmount(java.math.BigDecimal caseAmount){
		this.caseAmount = caseAmount;
	}
	
	/**
	 * 应收费用
	 */
	public java.math.BigDecimal getCost(){
		return cost;
	}
	
	/**
	 * 应收费用
	 */
	public void setCost(java.math.BigDecimal cost){
		this.cost = cost;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 回款凭证
	 */
	public java.lang.String getCertificate(){
		return certificate;
	}
	
	/**
	 * 回款凭证
	 */
	public void setCertificate(java.lang.String certificate){
		this.certificate = certificate;
	}
	
	/**
	 * 操作员id
	 */
	public java.lang.Integer getOperId(){
		return operId;
	}
	
	/**
	 * 操作员id
	 */
	public void setOperId(java.lang.Integer operId){
		this.operId = operId;
	}
	
	/**
	 * 操作员名称
	 */
	public java.lang.String getOperName(){
		return operName;
	}
	
	/**
	 * 操作员名称
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