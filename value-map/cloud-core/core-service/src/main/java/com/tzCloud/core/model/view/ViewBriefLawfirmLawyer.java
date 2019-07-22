
package com.tzCloud.core.model.view;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年03月15日 11时13分23秒
 */
public class ViewBriefLawfirmLawyer implements java.io.Serializable{

	/**
	 * 律师
	 */
	private String lawyerName;
	/**
	 * 律师事务所
	 */
	private String lawyerFirm;
	/**
	 * 案由
	 */
	private String brief;
	
	/**
	 * 律师
	 */
	public String getLawyerName(){
		return lawyerName;
	}
	
	/**
	 * 律师
	 */
	public void setLawyerName(String lawyerName){
		this.lawyerName = lawyerName;
	}
	
	/**
	 * 律师事务所
	 */
	public String getLawyerFirm(){
		return lawyerFirm;
	}
	
	/**
	 * 律师事务所
	 */
	public void setLawyerFirm(String lawyerFirm){
		this.lawyerFirm = lawyerFirm;
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

}
