
package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年08月10日 18时47分26秒
 */
public class ArticleScenarioReq extends RequestModel {

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String desc;
	/**
	 * 
	 */
	private Integer articleId;
	
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
	 * 
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 
	 */
	public String getDesc(){
		return desc;
	}
	
	/**
	 * 
	 */
	public void setDesc(String desc){
		this.desc = desc;
	}
	
	/**
	 * 
	 */
	public Integer getArticleId(){
		return articleId;
	}
	
	/**
	 * 
	 */
	public void setArticleId(Integer articleId){
		this.articleId = articleId;
	}

}
