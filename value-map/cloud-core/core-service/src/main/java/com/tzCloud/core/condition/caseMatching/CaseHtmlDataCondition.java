
package com.tzCloud.core.condition.caseMatching;

import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年03月05日 11时07分13秒
 */
public class CaseHtmlDataCondition implements DaoCondition{

	/**
	 * 自增ID
	 */
	private Integer id;
	/**
	 * 文书ID
	 */
	private String docId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 公示时间
	 */
	private String pubDate;
	/**
	 * 正文
	 */
	private String html;
	/**
	 * 去除样式的正文
	 */
	private String removeHtml;
	/**
	 * 创建时间
	 */
	private java.util.Date createdAt;
	/**
	 * 修改时间
	 */
	private java.util.Date updatedAt;
	
	/**
	 * 自增ID
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 自增ID
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
	 * 标题
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * 标题
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * 公示时间
	 */
	public String getPubDate(){
		return pubDate;
	}
	
	/**
	 * 公示时间
	 */
	public void setPubDate(String pubDate){
		this.pubDate = pubDate;
	}
	
	/**
	 * 正文
	 */
	public String getHtml(){
		return html;
	}
	
	/**
	 * 正文
	 */
	public void setHtml(String html){
		this.html = html;
	}
	
	/**
	 * 去除样式的正文
	 */
	public String getRemoveHtml(){
		return removeHtml;
	}
	
	/**
	 * 去除样式的正文
	 */
	public void setRemoveHtml(String removeHtml){
		this.removeHtml = removeHtml;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreatedAt(){
		return createdAt;
	}
	
	/**
	 * 创建时间
	 */
	public void setCreatedAt(java.util.Date createdAt){
		this.createdAt = createdAt;
	}
	
	/**
	 * 修改时间
	 */
	public java.util.Date getUpdatedAt(){
		return updatedAt;
	}
	
	/**
	 * 修改时间
	 */
	public void setUpdatedAt(java.util.Date updatedAt){
		this.updatedAt = updatedAt;
	}

}