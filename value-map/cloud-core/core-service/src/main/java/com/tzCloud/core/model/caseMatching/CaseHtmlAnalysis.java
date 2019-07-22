
package com.tzCloud.core.model.caseMatching;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年03月05日 09时26分19秒
 */
public class CaseHtmlAnalysis implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 文书id
	 */
	private String docId;
	/**
	 * 文本内容
	 */
	private String html;
	/**
	 * 原告(多个以 ; 隔开)
	 */
	private String prosecutor;
	/**
	 * 被告(多个以 ; 隔开)
	 */
	private String defendant;
	/**
	 * 上诉人代理律师 (多个以 ; 隔开)
	 */
	private String prosecutorLawyer;
	/**
	 * 被上诉人代理律师(多个以 ; 隔开)
	 */
	private String defendantLawyer;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;
	
	/**
	 * 主键id
	 */
	public Long getId(){
		return id;
	}
	
	/**
	 * 主键id
	 */
	public void setId(Long id){
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
	 * 文本内容
	 */
	public String getHtml(){
		return html;
	}
	
	/**
	 * 文本内容
	 */
	public void setHtml(String html){
		this.html = html;
	}
	
	/**
	 * 原告(多个以 ; 隔开)
	 */
	public String getProsecutor(){
		return prosecutor;
	}
	
	/**
	 * 原告(多个以 ; 隔开)
	 */
	public void setProsecutor(String prosecutor){
		this.prosecutor = prosecutor;
	}
	
	/**
	 * 被告(多个以 ; 隔开)
	 */
	public String getDefendant(){
		return defendant;
	}
	
	/**
	 * 被告(多个以 ; 隔开)
	 */
	public void setDefendant(String defendant){
		this.defendant = defendant;
	}
	
	/**
	 * 上诉人代理律师 (多个以 ; 隔开)
	 */
	public String getProsecutorLawyer(){
		return prosecutorLawyer;
	}
	
	/**
	 * 上诉人代理律师 (多个以 ; 隔开)
	 */
	public void setProsecutorLawyer(String prosecutorLawyer){
		this.prosecutorLawyer = prosecutorLawyer;
	}
	
	/**
	 * 被上诉人代理律师(多个以 ; 隔开)
	 */
	public String getDefendantLawyer(){
		return defendantLawyer;
	}
	
	/**
	 * 被上诉人代理律师(多个以 ; 隔开)
	 */
	public void setDefendantLawyer(String defendantLawyer){
		this.defendantLawyer = defendantLawyer;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	
	/**
	 * 修改时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 修改时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}
