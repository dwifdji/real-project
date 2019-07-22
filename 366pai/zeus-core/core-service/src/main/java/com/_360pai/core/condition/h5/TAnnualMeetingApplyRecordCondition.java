
package com._360pai.core.condition.h5;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月08日 13时12分26秒
 */
public class TAnnualMeetingApplyRecordCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 职务
	 */
	private String position;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 是否入驻酒店（0 否 1 是）
	 */
	private Boolean isStayOvernight;
	/**
	 * 是否删除（0 否 1 是）
	 */
	private Boolean isDelete;
	/**
	 * 
	 */
	private java.util.Date createTime;
	
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
	 * 
	 */
	public String getCompanyName(){
		return companyName;
	}
	
	/**
	 * 
	 */
	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}
	
	/**
	 * 职务
	 */
	public String getPosition(){
		return position;
	}
	
	/**
	 * 职务
	 */
	public void setPosition(String position){
		this.position = position;
	}
	
	/**
	 * 手机号
	 */
	public String getMobile(){
		return mobile;
	}
	
	/**
	 * 手机号
	 */
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	
	/**
	 * 是否入驻酒店（0 否 1 是）
	 */
	public Boolean getIsStayOvernight(){
		return isStayOvernight;
	}
	
	/**
	 * 是否入驻酒店（0 否 1 是）
	 */
	public void setIsStayOvernight(Boolean isStayOvernight){
		this.isStayOvernight = isStayOvernight;
	}
	
	/**
	 * 是否删除（0 否 1 是）
	 */
	public Boolean getIsDelete(){
		return isDelete;
	}
	
	/**
	 * 是否删除（0 否 1 是）
	 */
	public void setIsDelete(Boolean isDelete){
		this.isDelete = isDelete;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

}