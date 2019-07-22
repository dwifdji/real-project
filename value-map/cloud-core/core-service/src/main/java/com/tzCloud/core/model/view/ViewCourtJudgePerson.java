
package com.tzCloud.core.model.view;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月24日 10时43分19秒
 */
public class ViewCourtJudgePerson implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 文书ID
	 */
	private String docId;
	/**
	 * 法院名称
	 */
	private String courtName;
	/**
	 * 审判长
	 */
	private String presidingJudge;
	
	/**
	 * 主键
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 文书ID
	 */
	public String getDocId(){
		return docId;
	}
	
	/**
	 * 文书ID
	 */
	public void setDocId(String docId){
		this.docId = docId;
	}
	
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

}
