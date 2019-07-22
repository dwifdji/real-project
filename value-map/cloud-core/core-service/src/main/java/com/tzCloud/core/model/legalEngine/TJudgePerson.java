
package com.tzCloud.core.model.legalEngine;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月23日 14时53分11秒
 */
public class TJudgePerson implements java.io.Serializable{

	/**
	 * 主键自增
	 */
	private Integer id;
	/**
	 * 文书id
	 */
	private String docId;
	/**
	 * 审判长
	 */
	private String presidingJudge;
	/**
	 * 审判员
	 */
	private String judicialOfficer;
	/**
	 * 书记员
	 */
	private String courtClerk;
	/**
	 * 法官助理
	 */
	private String judgeAssistant;
	/**
	 * 代理审判员
	 */
	private String actingJudge;
	/**
	 * 执行人
	 */
	private String executor;
	
	/**
	 * 主键自增
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键自增
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 文书id
	 */
	public String getDocId(){
		return docId;
	}
	
	/**
	 * 文书id
	 */
	public void setDocId(String docId){
		this.docId = docId;
	}
	
	/**
	 * 审判长
	 */
	public String getPresidingJudge(){
		return presidingJudge;
	}
	
	/**
	 * 审判长
	 */
	public void setPresidingJudge(String presidingJudge){
		this.presidingJudge = presidingJudge;
	}
	
	/**
	 * 审判员
	 */
	public String getJudicialOfficer(){
		return judicialOfficer;
	}
	
	/**
	 * 审判员
	 */
	public void setJudicialOfficer(String judicialOfficer){
		this.judicialOfficer = judicialOfficer;
	}
	
	/**
	 * 书记员
	 */
	public String getCourtClerk(){
		return courtClerk;
	}
	
	/**
	 * 书记员
	 */
	public void setCourtClerk(String courtClerk){
		this.courtClerk = courtClerk;
	}
	
	/**
	 * 法官助理
	 */
	public String getJudgeAssistant(){
		return judgeAssistant;
	}
	
	/**
	 * 法官助理
	 */
	public void setJudgeAssistant(String judgeAssistant){
		this.judgeAssistant = judgeAssistant;
	}
	
	/**
	 * 代理审判员
	 */
	public String getActingJudge(){
		return actingJudge;
	}
	
	/**
	 * 代理审判员
	 */
	public void setActingJudge(String actingJudge){
		this.actingJudge = actingJudge;
	}
	
	/**
	 * 执行人
	 */
	public String getExecutor(){
		return executor;
	}
	
	/**
	 * 执行人
	 */
	public void setExecutor(String executor){
		this.executor = executor;
	}

}
