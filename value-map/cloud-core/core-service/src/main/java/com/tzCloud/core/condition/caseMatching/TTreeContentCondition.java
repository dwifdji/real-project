
package com.tzCloud.core.condition.caseMatching;

import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年03月07日 15时53分28秒
 */
public class TTreeContentCondition implements DaoCondition{
	/**
	 * 主键自增
	 */
	private Integer id;
	/**
	 * 关键字
	 */
	private String keyWord;
	/**
	 * 父集ID
	 */
	private Integer parentId;
	/**
	 * 字段
	 */
	private String field;
	/**
	 * 主键自增
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键自增
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 关键字
	 */
	public String getKeyWord(){
		return keyWord;
	}
	
	/**
	 * 关键字
	 */
	public void setKeyWord(String keyWord){
		this.keyWord = keyWord;
	}
	
	/**
	 * 父集ID
	 */
	public Integer getParentId(){
		return parentId;
	}
	
	/**
	 * 父集ID
	 */
	public void setParentId(Integer parentId){
		this.parentId = parentId;
	}
	
	/**
	 * 字段
	 */
	public String getField(){
		return field;
	}
	
	/**
	 * 字段
	 */
	public void setField(String field){
		this.field = field;
	}

}