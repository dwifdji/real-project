
package com._360pai.core.model.fastway;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年11月29日 17时00分20秒
 */
public class TFastwayAgencyApply implements java.io.Serializable{

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
	 * 许可证有效开始日期
	 */
	private java.util.Date qualifiedBegin;
	/**
	 * 许可证有效结束日期
	 */
	private java.util.Date qualifiedEnd;
	/**
	 * 许可证签署时间
	 */
	private java.util.Date qualifiedSignDate;
	/**
	 * 营业开始时间
	 */
	private java.util.Date businessBegin;
	/**
	 * 营业结束时间
	 */
	private java.util.Date businessEnd;
	/**
	 * 机构简称
	 */
	private java.lang.String shortName;
	/**
	 * 机构代码
	 */
	private java.lang.String code;
	/**
	 * 成拍分成
	 */
	private Integer serveBuyerPercent;
	/**
	 * 送拍分成
	 */
	private Integer serveSellerPercent;
	/**
	 * 资质批准证书号
	 */
	private String auctionApproveNo;
	/**
	 * 申请状态  10：待审核  20：通过  30：拒绝
	 */
	private String applyStatus;
	/**
	 * 申请类型   auction：拍卖行
	 */
	private String applyType;
	/**
	 * 入口 10: h5   20:主站
	 */
	private String source;
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

	private String mobile;
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
	 * 许可证有效开始日期
	 */
	public java.util.Date getQualifiedBegin(){
		return qualifiedBegin;
	}
	
	/**
	 * 许可证有效开始日期
	 */
	public void setQualifiedBegin(java.util.Date qualifiedBegin){
		this.qualifiedBegin = qualifiedBegin;
	}
	
	/**
	 * 许可证有效结束日期
	 */
	public java.util.Date getQualifiedEnd(){
		return qualifiedEnd;
	}
	
	/**
	 * 许可证有效结束日期
	 */
	public void setQualifiedEnd(java.util.Date qualifiedEnd){
		this.qualifiedEnd = qualifiedEnd;
	}
	
	/**
	 * 许可证签署时间
	 */
	public java.util.Date getQualifiedSignDate(){
		return qualifiedSignDate;
	}
	
	/**
	 * 许可证签署时间
	 */
	public void setQualifiedSignDate(java.util.Date qualifiedSignDate){
		this.qualifiedSignDate = qualifiedSignDate;
	}
	
	/**
	 * 营业开始时间
	 */
	public java.util.Date getBusinessBegin(){
		return businessBegin;
	}
	
	/**
	 * 营业开始时间
	 */
	public void setBusinessBegin(java.util.Date businessBegin){
		this.businessBegin = businessBegin;
	}
	
	/**
	 * 营业结束时间
	 */
	public java.util.Date getBusinessEnd(){
		return businessEnd;
	}
	
	/**
	 * 营业结束时间
	 */
	public void setBusinessEnd(java.util.Date businessEnd){
		this.businessEnd = businessEnd;
	}
	
	/**
	 * 成拍分成
	 */
	public Integer getServeBuyerPercent(){
		return serveBuyerPercent;
	}
	
	/**
	 * 成拍分成
	 */
	public void setServeBuyerPercent(Integer serveBuyerPercent){
		this.serveBuyerPercent = serveBuyerPercent;
	}
	
	/**
	 * 送拍分成
	 */
	public Integer getServeSellerPercent(){
		return serveSellerPercent;
	}
	
	/**
	 * 送拍分成
	 */
	public void setServeSellerPercent(Integer serveSellerPercent){
		this.serveSellerPercent = serveSellerPercent;
	}
	
	/**
	 * 资质批准证书号
	 */
	public String getAuctionApproveNo(){
		return auctionApproveNo;
	}
	
	/**
	 * 资质批准证书号
	 */
	public void setAuctionApproveNo(String auctionApproveNo){
		this.auctionApproveNo = auctionApproveNo;
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
	 * 申请类型   auction：拍卖行
	 */
	public String getApplyType(){
		return applyType;
	}
	
	/**
	 * 申请类型   auction：拍卖行
	 */
	public void setApplyType(String applyType){
		this.applyType = applyType;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
