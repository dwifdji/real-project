
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月11日 19时10分03秒
 */
public class TAssetFieldGroupCondition implements DaoCondition{

	/**
	 * 自增ID
	 */
	private Integer id;
	/**
	 * 字段分组的名称
	 */
	private String name;
	/**
	 * 排序编号
	 */
	private Integer orderNum;
	/**
	 * 是否可增加该分组字段 0:不可以  1:可以
	 */
	private Integer extensible;
	/**
	 * 分组对应的title
	 */
	private String title;
	
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
	 * 字段分组的名称
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 字段分组的名称
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 排序编号
	 */
	public Integer getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 排序编号
	 */
	public void setOrderNum(Integer orderNum){
		this.orderNum = orderNum;
	}
	
	/**
	 * 是否可增加该分组字段 0:不可以  1:可以
	 */
	public Integer getExtensible(){
		return extensible;
	}
	
	/**
	 * 是否可增加该分组字段 0:不可以  1:可以
	 */
	public void setExtensible(Integer extensible){
		this.extensible = extensible;
	}
	
	/**
	 * 分组对应的title
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * 分组对应的title
	 */
	public void setTitle(String title){
		this.title = title;
	}

}