
package com.tzCloud.core.condition.view;

import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年03月15日 11时13分23秒
 */
public class ViewBriefLawfirmLawyerCondition implements DaoCondition{

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