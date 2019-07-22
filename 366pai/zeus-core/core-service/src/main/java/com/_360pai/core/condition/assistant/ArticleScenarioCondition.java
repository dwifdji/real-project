
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
public class ArticleScenarioCondition implements DaoCondition{

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