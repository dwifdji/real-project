
package com._360pai.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分24秒
 */
public class PartyChannelAgent implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer partyId;
	/**
	 * 
	 */
	private Integer channelAgentPartyId;
	/**
	 * 
	 */
	private java.math.BigDecimal commissionPercentChannelAgent;
	/**
	 * 
	 */
	private java.util.Date createdAt;
	
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
	public Integer getChannelAgentPartyId(){
		return channelAgentPartyId;
	}
	
	/**
	 * 
	 */
	public void setChannelAgentPartyId(Integer channelAgentPartyId){
		this.channelAgentPartyId = channelAgentPartyId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getCommissionPercentChannelAgent(){
		return commissionPercentChannelAgent;
	}
	
	/**
	 * 
	 */
	public void setCommissionPercentChannelAgent(java.math.BigDecimal commissionPercentChannelAgent){
		this.commissionPercentChannelAgent = commissionPercentChannelAgent;
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

}
