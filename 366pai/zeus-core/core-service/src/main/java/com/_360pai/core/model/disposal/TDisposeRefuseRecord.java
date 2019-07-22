
package com._360pai.core.model.disposal;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年10月29日 12时44分58秒
 */
public class TDisposeRefuseRecord implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 处置服务商id
	 */
	private Integer providerId;
	private Integer levelId;
	/**
	 * 接单id
	 */
	private Integer surveyId;
	/**
	 * 委托时间
	 */
	private java.util.Date assignTime;
	/**
	 * 违约时间
	 */
	private java.util.Date refuseTime;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
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
	 * 处置服务商id
	 */
	public Integer getProviderId(){
		return providerId;
	}
	
	/**
	 * 处置服务商id
	 */
	public void setProviderId(Integer providerId){
		this.providerId = providerId;
	}
	
	/**
	 * 接单id
	 */
	public Integer getSurveyId(){
		return surveyId;
	}
	
	/**
	 * 接单id
	 */
	public void setSurveyId(Integer surveyId){
		this.surveyId = surveyId;
	}
	
	/**
	 * 委托时间
	 */
	public java.util.Date getAssignTime(){
		return assignTime;
	}
	
	/**
	 * 委托时间
	 */
	public void setAssignTime(java.util.Date assignTime){
		this.assignTime = assignTime;
	}
	
	/**
	 * 违约时间
	 */
	public java.util.Date getRefuseTime(){
		return refuseTime;
	}
	
	/**
	 * 违约时间
	 */
	public void setRefuseTime(java.util.Date refuseTime){
		this.refuseTime = refuseTime;
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
