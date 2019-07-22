
package com.tzCloud.core.condition.view;

import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年03月15日 11时13分23秒
 */
public class ViewBriefCourtCondition implements DaoCondition{

	/**
	 * 法院名称
	 */
	private String courtName;
	/**
	 * 案由
	 */
	private String brief;
	/**
	 * 
	 */
	private Long caseNum;
	
	/**
	 * 法院名称
	 */
	public String getCourtName(){
		return courtName;
	}
	
	/**
	 * 法院名称
	 */
	public void setCourtName(String courtName){
		this.courtName = courtName;
	}
	
	/**
	 * 案由
	 */
	public String getBrief(){
		return brief;
	}
	
	/**
	 * 案由
	 */
	public void setBrief(String brief){
		this.brief = brief;
	}
	
	/**
	 * 
	 */
	public Long getCaseNum(){
		return caseNum;
	}
	
	/**
	 * 
	 */
	public void setCaseNum(Long caseNum){
		this.caseNum = caseNum;
	}

}