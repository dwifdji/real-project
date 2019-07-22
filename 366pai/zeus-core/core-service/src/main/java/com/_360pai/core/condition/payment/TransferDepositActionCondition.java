
package com._360pai.core.condition.payment;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分20秒
 */
public class TransferDepositActionCondition implements DaoCondition{

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private java.math.BigDecimal amount;
	/**
	 * 
	 */
	private Boolean forSendBack;
	
	/**
	 * 
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(String id){
		this.id = id;
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
	public Boolean getForSendBack(){
		return forSendBack;
	}
	
	/**
	 * 
	 */
	public void setForSendBack(Boolean forSendBack){
		this.forSendBack = forSendBack;
	}

}