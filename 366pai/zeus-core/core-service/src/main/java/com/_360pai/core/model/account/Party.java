
package com._360pai.core.model.account;

import com._360pai.core.common.constants.PartyEnum;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分24秒
 */
public class Party implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private PartyEnum.Type type;
	/**
	 * 
	 */
	private String category;
	/**
	 * 
	 */
	private Boolean isChannelAgent;
	/**
	 * 
	 */
	private Boolean isInBlackList;
	/**
	 * 
	 */
	private Boolean isInternal;
	
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
	public PartyEnum.Type getType(){
		return type;
	}
	
	/**
	 * 
	 */
	public void setType(PartyEnum.Type type){
		this.type = type;
	}
	
	/**
	 * 
	 */
	public String getCategory(){
		return category;
	}
	
	/**
	 * 
	 */
	public void setCategory(String category){
		this.category = category;
	}
	
	/**
	 * 
	 */
	public Boolean getIsChannelAgent(){
		return isChannelAgent;
	}
	
	/**
	 * 
	 */
	public void setIsChannelAgent(Boolean isChannelAgent){
		this.isChannelAgent = isChannelAgent;
	}
	
	/**
	 * 
	 */
	public Boolean getIsInBlackList(){
		return isInBlackList;
	}
	
	/**
	 * 
	 */
	public void setIsInBlackList(Boolean isInBlackList){
		this.isInBlackList = isInBlackList;
	}
	
	/**
	 * 
	 */
	public Boolean getIsInternal(){
		return isInternal;
	}
	
	/**
	 * 
	 */
	public void setIsInternal(Boolean isInternal){
		this.isInternal = isInternal;
	}

}
