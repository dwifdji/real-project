
package com.tzCloud.core.condition.legalEngine;

import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年06月25日 09时42分26秒
 */
public class DsrxxParseStatusCondition implements DaoCondition{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 文书id
	 */
	private String docId;
	/**
	 * 0：未解析 1:已解析
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