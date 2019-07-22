
package com._360pai.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分24秒
 */
public class PartyBlackListAction implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String action;
	/**
	 * 
	 */
	private Integer partyId;
	/**
	 * 
	 */
	private String reason;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	/**
	 * 
	 */
	private Integer staffId;
	
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
	public String getAction(){
		return action;
	}
	
	/**
	 * 
	 */
	public void setAction(String action){
		this.action = action;
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
	public String getReason(){
		return reason;
	}
	
	/**
	 * 
	 */
	public void setReason(String reason){
		this.reason = reason;
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
	public Integer getStaffId(){
		return staffId;
	}
	
	/**
	 * 
	 */
	public void setStaffId(Integer staffId){
		this.staffId = staffId;
	}

}
