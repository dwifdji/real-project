
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
public class PartyCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String type;
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
	public String getType(){
		return type;
	}
	
	/**
	 * 
	 */
	public void setType(String type){
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