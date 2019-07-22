
package com.tzCloud.core.model.view;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年03月15日 11时13分23秒
 */
public class ViewBriefLawyerRanking implements java.io.Serializable{

	/**
	 * 律师
	 */
	private String lawyerName;
	/**
	 * 案由
	 */
	private String brief;
	/**
	 * 
	 */
	private Long thisCaseNum;
	/**
	 * 
	 */
	private java.math.BigDecimal thisCaseWinRates;
	
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
	public Long getThisCaseNum(){
		return thisCaseNum;
	}
	
	/**
	 * 
	 */
	public void setThisCaseNum(Long thisCaseNum){
		this.thisCaseNum = thisCaseNum;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getThisCaseWinRates(){
		return thisCaseWinRates;
	}
	
	/**
	 * 
	 */
	public void setThisCaseWinRates(java.math.BigDecimal thisCaseWinRates){
		this.thisCaseWinRates = thisCaseWinRates;
	}

}
