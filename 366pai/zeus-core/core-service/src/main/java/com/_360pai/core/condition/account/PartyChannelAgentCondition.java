
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
public class PartyChannelAgentCondition implements DaoCondition{

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