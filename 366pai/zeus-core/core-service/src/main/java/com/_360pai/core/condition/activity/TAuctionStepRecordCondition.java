
package com._360pai.core.condition.activity;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年10月11日 11时17分23秒
 */
public class TAuctionStepRecordCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer activityId;
	/**
	 * 
	 */
	private Long orderId;

	private Integer partyId;

	/**
	 * 
	 */
	private String step;
	/**
	 * 
	 */
	private String req;
	/**
	 * 
	 */
	private String resp;
	/**
	 * 
	 */
	private String coreException;
	/**
	 * 
	 */
	private String status;
	/**
	 * 
	 */
	private java.util.Date createTime;
	/**
	 * 
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
	public Long getOrderId(){
		return orderId;
	}
	
	/**
	 * 
	 */
	public void setOrderId(Long orderId){
		this.orderId = orderId;
	}
	
	/**
	 * 
	 */
	public String getStep(){
		return step;
	}
	
	/**
	 * 
	 */
	public void setStep(String step){
		this.step = step;
	}
	
	/**
	 * 
	 */
	public String getReq(){
		return req;
	}
	
	/**
	 * 
	 */
	public void setReq(String req){
		this.req = req;
	}
	
	/**
	 * 
	 */
	public String getResp(){
		return resp;
	}
	
	/**
	 * 
	 */
	public void setResp(String resp){
		this.resp = resp;
	}
	
	/**
	 * 
	 */
	public String getCoreException(){
		return coreException;
	}
	
	/**
	 * 
	 */
	public void setCoreException(String coreException){
		this.coreException = coreException;
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

	public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}
}