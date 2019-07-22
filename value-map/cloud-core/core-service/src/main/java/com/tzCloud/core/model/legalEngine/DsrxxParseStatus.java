
package com.tzCloud.core.model.legalEngine;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年06月25日 09时42分26秒
 */
public class DsrxxParseStatus implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 文书id
	 */
	private String docId;
	/**
	 * 0：未解析 1:已解析 2:未解析出结果 3：持久化异常
	 */
	private Integer parseStatus;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;

	public DsrxxParseStatus() {
	}

	public DsrxxParseStatus(String docId) {
		this.docId = docId;
	}

	/**
	 * 主键id
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键id
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
	 * 0：未解析 1:已解析
	 */
	public Integer getParseStatus(){
		return parseStatus;
	}
	
	/**
	 * 0：未解析 1:已解析
	 */
	public void setParseStatus(Integer parseStatus){
		this.parseStatus = parseStatus;
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
