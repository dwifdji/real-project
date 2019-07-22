
package com._360pai.core.condition.fastway;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年11月26日 19时35分09秒
 */
public class TFastwayDisposeApplyCondition implements DaoCondition{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 账户id
	 */
	private Integer accountId;
	/**
	 * partyId
	 */
	private Integer partyId;
	/**
	 * 申请信息
	 */
	private com.alibaba.fastjson.JSONObject applyFiled;
	/**
	 * 申请状态  10：待审核  20：通过  30：拒绝
	 */
	private String applyStatus;
	/**
	 * 申请类型   lawyer：律师  lawOffice:事务所
	 */
	private String applyType;
	/**
	 * 审核人id
	 */
	private Integer operatorId;
	/**
	 * 审核备注
	 */
	private String remark;
	/**
	 * 拒绝原因
	 */
	private String refuseReason;
	/**
	 * 审核时间
	 */
	private java.util.Date operatorTime;
	/**
	 * 入口 10: h5   20:主站
	 */
	private String source;
	/**
	 * 开始时间
	 */
	private java.util.Date beginDate;
	/**
	 * 结束时间
	 */
	private java.util.Date endDate;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;
	/**
	 * 删除标识 0:未删除  1:已删除
	 */
	private Boolean isDel;
	/**
	 * 业务对接人
	 */
	private java.lang.Integer businessOperatorId;
	/**
	 * 开户人员
	 */
	private java.lang.Integer openAccountOperatorId;
	
	/**
	 * 主键id
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键id
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
	 * partyId
	 */
	public Integer getPartyId(){
		return partyId;
	}
	
	/**
	 * partyId
	 */
	public void setPartyId(Integer partyId){
		this.partyId = partyId;
	}
	
	/**
	 * 申请信息
	 */
	public com.alibaba.fastjson.JSONObject getApplyFiled(){
		return applyFiled;
	}
	
	/**
	 * 申请信息
	 */
	public void setApplyFiled(com.alibaba.fastjson.JSONObject applyFiled){
		this.applyFiled = applyFiled;
	}
	
	/**
	 * 申请状态  10：待审核  20：通过  30：拒绝
	 */
	public String getApplyStatus(){
		return applyStatus;
	}
	
	/**
	 * 申请状态  10：待审核  20：通过  30：拒绝
	 */
	public void setApplyStatus(String applyStatus){
		this.applyStatus = applyStatus;
	}
	
	/**
	 * 申请类型   lawyer：律师  lawOffice:事务所
	 */
	public String getApplyType(){
		return applyType;
	}
	
	/**
	 * 申请类型   lawyer：律师  lawOffice:事务所
	 */
	public void setApplyType(String applyType){
		this.applyType = applyType;
	}
	
	/**
	 * 审核人id
	 */
	public Integer getOperatorId(){
		return operatorId;
	}
	
	/**
	 * 审核人id
	 */
	public void setOperatorId(Integer operatorId){
		this.operatorId = operatorId;
	}
	
	/**
	 * 审核备注
	 */
	public String getRemark(){
		return remark;
	}
	
	/**
	 * 审核备注
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	/**
	 * 拒绝原因
	 */
	public String getRefuseReason(){
		return refuseReason;
	}
	
	/**
	 * 拒绝原因
	 */
	public void setRefuseReason(String refuseReason){
		this.refuseReason = refuseReason;
	}
	
	/**
	 * 审核时间
	 */
	public java.util.Date getOperatorTime(){
		return operatorTime;
	}
	
	/**
	 * 审核时间
	 */
	public void setOperatorTime(java.util.Date operatorTime){
		this.operatorTime = operatorTime;
	}
	
	/**
	 * 入口 10: h5   20:主站
	 */
	public String getSource(){
		return source;
	}
	
	/**
	 * 入口 10: h5   20:主站
	 */
	public void setSource(String source){
		this.source = source;
	}
	
	/**
	 * 开始时间
	 */
	public java.util.Date getBeginDate(){
		return beginDate;
	}
	
	/**
	 * 开始时间
	 */
	public void setBeginDate(java.util.Date beginDate){
		this.beginDate = beginDate;
	}
	
	/**
	 * 结束时间
	 */
	public java.util.Date getEndDate(){
		return endDate;
	}
	
	/**
	 * 结束时间
	 */
	public void setEndDate(java.util.Date endDate){
		this.endDate = endDate;
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
	 * 修改时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 修改时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}
	
	/**
	 * 删除标识 0:未删除  1:已删除
	 */
	public Boolean getIsDel(){
		return isDel;
	}
	
	/**
	 * 删除标识 0:未删除  1:已删除
	 */
	public void setIsDel(Boolean isDel){
		this.isDel = isDel;
	}

	public Integer getBusinessOperatorId() {
		return businessOperatorId;
	}

	public void setBusinessOperatorId(Integer businessOperatorId) {
		this.businessOperatorId = businessOperatorId;
	}

	public Integer getOpenAccountOperatorId() {
		return openAccountOperatorId;
	}

	public void setOpenAccountOperatorId(Integer openAccountOperatorId) {
		this.openAccountOperatorId = openAccountOperatorId;
	}
}