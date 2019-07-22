
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月26日 11时17分01秒
 */
public class TSpvApplyCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer companyId;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String license;
	/**
	 * 
	 */
	private String mobile;
	/**
	 * PENDING、APPROVED、REJECT
	 */
	private String status;
	/**
	 * 
	 */
	private Integer operatorId;
	/**
	 * 
	 */
	private java.util.Date createTime;
	/**
	 * 
	 */
	private java.util.Date updateTime;
	
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
	public Integer getCompanyId(){
		return companyId;
	}
	
	/**
	 * 
	 */
	public void setCompanyId(Integer companyId){
		this.companyId = companyId;
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
	public String getLicense(){
		return license;
	}
	
	/**
	 * 
	 */
	public void setLicense(String license){
		this.license = license;
	}
	
	/**
	 * 
	 */
	public String getMobile(){
		return mobile;
	}
	
	/**
	 * 
	 */
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	
	/**
	 * PENDING、APPROVED、REJECT
	 */
	public String getStatus(){
		return status;
	}
	
	/**
	 * PENDING、APPROVED、REJECT
	 */
	public void setStatus(String status){
		this.status = status;
	}
	
	/**
	 * 
	 */
	public Integer getOperatorId(){
		return operatorId;
	}
	
	/**
	 * 
	 */
	public void setOperatorId(Integer operatorId){
		this.operatorId = operatorId;
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
	
	/**
	 * 
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}