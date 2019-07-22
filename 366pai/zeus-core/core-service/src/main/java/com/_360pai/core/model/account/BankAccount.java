
package com._360pai.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分23秒
 */
public class BankAccount implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String number;
	/**
	 * 
	 */
	private Integer bankId;
	/**
	 * 
	 */
	private Integer partyId;
	/**
	 * 
	 */
	private String bankName;


	private String branchBankName;


	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

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
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 
	 */
	public String getNumber(){
		return number;
	}
	
	/**
	 * 
	 */
	public void setNumber(String number){
		this.number = number;
	}
	
	/**
	 * 
	 */
	public Integer getBankId(){
		return bankId;
	}
	
	/**
	 * 
	 */
	public void setBankId(Integer bankId){
		this.bankId = bankId;
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
	public String getBankName(){
		return bankName;
	}
	
	/**
	 * 
	 */
	public void setBankName(String bankName){
		this.bankName = bankName;
	}

}
