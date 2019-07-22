
package com.winback.core.model._case;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月29日 13时20分27秒
 */
public class TLawyerCaseBriefMap implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 律师id
	 */
	private Integer lawyerId;
	/**
	 * 案由id
	 */
	private Integer caseBriefId;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
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
	 * 律师id
	 */
	public Integer getLawyerId(){
		return lawyerId;
	}
	
	/**
	 * 律师id
	 */
	public void setLawyerId(Integer lawyerId){
		this.lawyerId = lawyerId;
	}
	
	/**
	 * 案由id
	 */
	public Integer getCaseBriefId(){
		return caseBriefId;
	}
	
	/**
	 * 案由id
	 */
	public void setCaseBriefId(Integer caseBriefId){
		this.caseBriefId = caseBriefId;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public void setDeleteFlag(Boolean deleteFlag){
		this.deleteFlag = deleteFlag;
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
	 * 更新时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 更新时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}
