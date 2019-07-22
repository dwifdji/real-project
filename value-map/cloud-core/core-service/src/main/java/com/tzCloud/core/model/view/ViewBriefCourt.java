
package com.tzCloud.core.model.view;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年03月15日 11时13分23秒
 */
public class ViewBriefCourt implements java.io.Serializable{

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
