
package com.tzCloud.core.model.caseMatching;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年03月08日 15时17分56秒
 */
public class CaseHtmlDsrxx implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 文书id
	 */
	private String docId;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 身份
	 */
	private String identity;
	/**
	 * 信息
	 */
	private String detail;
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
	 * 姓名
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 姓名
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 身份
	 */
	public String getIdentity(){
		return identity;
	}
	
	/**
	 * 身份
	 */
	public void setIdentity(String identity){
		this.identity = identity;
	}
	
	/**
	 * 信息
	 */
	public String getDetail(){
		return detail;
	}
	
	/**
	 * 信息
	 */
	public void setDetail(String detail){
		this.detail = detail;
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
