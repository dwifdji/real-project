
package com._360pai.core.model.enrolling;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分29秒
 */
public class EnrollingDeposit implements java.io.Serializable{

	/**
	 * 
	 */
	private Long id;
	/**
	 * 
	 */
	private Integer activityId;
	/**
	 * 
	 */
	private Integer partyId;
	/**
	 * 
	 */
	private java.math.BigDecimal amount;
	/**
	 * 
	 */
	private String status;
	/**
	 * 
	 */
	private Integer agencyId;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private String participateProof;
	/**
	 * 
	 */
	private java.util.Date uploadAt;

	/**
	 *保证金锁定类型
	 */
	private String type;

	/**
	 *保证金保证金锁定订单
	 */
	private String orderNum;


	/**
	 *展示的名称
	 */
	private String showName;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private java.lang.Boolean deleteFlag;

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 */
	public Long getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(Long id){
		this.id = id;
	}
	
	/**
	 * 
	 */
	public Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 
	 */
	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 
	 */
	public Integer getPartyId(){
		return partyId;
	}
	
	/**
	 * 
	 */
	public void setPartyId(Integer partyId){
		this.partyId = partyId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getAmount(){
		return amount;
	}
	
	/**
	 * 
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}
	
	/**
	 * 
	 */
	public String getStatus(){
		return status;
	}
	
	/**
	 * 
	 */
	public void setStatus(String status){
		this.status = status;
	}
	
	/**
	 * 
	 */
	public Integer getAgencyId(){
		return agencyId;
	}
	
	/**
	 * 
	 */
	public void setAgencyId(Integer agencyId){
		this.agencyId = agencyId;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreatedAt(){
		return createdAt;
	}
	
	/**
	 * 
	 */
	public void setCreatedAt(java.util.Date createdAt){
		this.createdAt = createdAt;
	}
	
	/**
	 * 
	 */
	public String getParticipateProof(){
		return participateProof;
	}
	
	/**
	 * 
	 */
	public void setParticipateProof(String participateProof){
		this.participateProof = participateProof;
	}
	
	/**
	 * 
	 */
	public java.util.Date getUploadAt(){
		return uploadAt;
	}
	
	/**
	 * 
	 */
	public void setUploadAt(java.util.Date uploadAt){
		this.uploadAt = uploadAt;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
