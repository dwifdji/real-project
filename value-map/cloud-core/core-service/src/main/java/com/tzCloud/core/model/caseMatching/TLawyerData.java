
package com.tzCloud.core.model.caseMatching;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年03月05日 09时26分19秒
 */
public class TLawyerData implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 文章ID
	 */
	private String docid;
	/**
	 * 律师
	 */
	private String lawyer;
	/**
	 * 律师事务所
	 */
	private String lawFirm;
	/**
	 * 其他描述信息
	 */
	private String detail;
	/**
	 * 更新日期
	 */
	private java.util.Date updatedAt;
	/**
	 * 创建日期
	 */
	private java.util.Date createdAt;
	/**
	 * 律师身份（上诉人律师；被上诉人律师）
	 */
	private String identity;
	/**
	 *
	 */
	private String winFlag;
	private String html;
	private Integer lawyerId;
	private String brief;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

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
	 * 文章ID
	 */
	public String getDocid(){
		return docid;
	}
	
	/**
	 * 文章ID
	 */
	public void setDocid(String docid){
		this.docid = docid;
	}
	
	/**
	 * 律师
	 */
	public String getLawyer(){
		return lawyer;
	}
	
	/**
	 * 律师
	 */
	public void setLawyer(String lawyer){
		this.lawyer = lawyer;
	}
	
	/**
	 * 律师事务所
	 */
	public String getLawFirm(){
		return lawFirm;
	}
	
	/**
	 * 律师事务所
	 */
	public void setLawFirm(String lawFirm){
		this.lawFirm = lawFirm;
	}
	
	/**
	 * 其他描述信息
	 */
	public String getDetail(){
		return detail;
	}
	
	/**
	 * 其他描述信息
	 */
	public void setDetail(String detail){
		this.detail = detail;
	}
	
	/**
	 * 更新日期
	 */
	public java.util.Date getUpdatedAt(){
		return updatedAt;
	}
	
	/**
	 * 更新日期
	 */
	public void setUpdatedAt(java.util.Date updatedAt){
		this.updatedAt = updatedAt;
	}
	
	/**
	 * 创建日期
	 */
	public java.util.Date getCreatedAt(){
		return createdAt;
	}
	
	/**
	 * 创建日期
	 */
	public void setCreatedAt(java.util.Date createdAt){
		this.createdAt = createdAt;
	}
	
	/**
	 * 律师身份（上诉人律师；被上诉人律师）
	 */
	public String getIdentity(){
		return identity;
	}
	
	/**
	 * 律师身份（上诉人律师；被上诉人律师）
	 */
	public void setIdentity(String identity){
		this.identity = identity;
	}

	public String getWinFlag() {
		return winFlag;
	}

	public void setWinFlag(String winFlag) {
		this.winFlag = winFlag;
	}

	public Integer getLawyerId() {
		return lawyerId;
	}

	public void setLawyerId(Integer lawyerId) {
		this.lawyerId = lawyerId;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}
}
