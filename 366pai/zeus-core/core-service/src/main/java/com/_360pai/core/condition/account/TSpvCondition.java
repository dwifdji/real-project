
package com._360pai.core.condition.account;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月21日 11时12分56秒
 */
public class TSpvCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 企业id
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
	 * 
	 */
	private Integer payBind;
	/**
	 * 
	 */
	private String dfftId;
	/**
	 * 
	 */
	private String fddId;
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
	 * 企业id
	 */
	public Integer getCompanyId(){
		return companyId;
	}
	
	/**
	 * 企业id
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
	 * 
	 */
	public Integer getPayBind(){
		return payBind;
	}
	
	/**
	 * 
	 */
	public void setPayBind(Integer payBind){
		this.payBind = payBind;
	}
	
	/**
	 * 
	 */
	public String getDfftId(){
		return dfftId;
	}
	
	/**
	 * 
	 */
	public void setDfftId(String dfftId){
		this.dfftId = dfftId;
	}
	
	/**
	 * 
	 */
	public String getFddId(){
		return fddId;
	}
	
	/**
	 * 
	 */
	public void setFddId(String fddId){
		this.fddId = fddId;
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