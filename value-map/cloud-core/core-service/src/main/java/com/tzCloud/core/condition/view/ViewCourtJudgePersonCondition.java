
package com.tzCloud.core.condition.view;

import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月24日 10时43分19秒
 */
public class ViewCourtJudgePersonCondition implements DaoCondition{

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