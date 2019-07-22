
package com.tzCloud.core.model.assistant;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年10月12日 13时59分42秒
 */
public class TSmsEmailConfig implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 业务类型
	 */
	private String busType;
	/**
	 * 客服的电话 多个以, 隔开
	 */
	private String servicePhone;
	/**
	 * 客服的邮件多个以，隔开
	 */
	private String serviceEmail;
	/**
	 * 审核人员电话
	 */
	private String auditorPhone;
	/**
	 * 审核人员邮件
	 */
	private String auditorEmail;
	/**
	 * 转态信息1启用 2 不启用
	 */
	private String status;
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
	 * 业务类型
	 */
	public String getBusType(){
		return busType;
	}

	/**
	 * 业务类型
	 */
	public void setBusType(String busType){
		this.busType = busType;
	}

	/**
	 * 客服的电话 多个以, 隔开
	 */
	public String getServicePhone(){
		return servicePhone;
	}

	/**
	 * 客服的电话 多个以, 隔开
	 */
	public void setServicePhone(String servicePhone){
		this.servicePhone = servicePhone;
	}

	/**
	 * 客服的邮件多个以，隔开
	 */
	public String getServiceEmail(){
		return serviceEmail;
	}

	/**
	 * 客服的邮件多个以，隔开
	 */
	public void setServiceEmail(String serviceEmail){
		this.serviceEmail = serviceEmail;
	}

	/**
	 * 审核人员电话
	 */
	public String getAuditorPhone(){
		return auditorPhone;
	}

	/**
	 * 审核人员电话
	 */
	public void setAuditorPhone(String auditorPhone){
		this.auditorPhone = auditorPhone;
	}

	/**
	 * 审核人员邮件
	 */
	public String getAuditorEmail(){
		return auditorEmail;
	}

	/**
	 * 审核人员邮件
	 */
	public void setAuditorEmail(String auditorEmail){
		this.auditorEmail = auditorEmail;
	}

	/**
	 * 转态信息1启用 2 不启用
	 */
	public String getStatus(){
		return status;
	}

	/**
	 * 转态信息1启用 2 不启用
	 */
	public void setStatus(String status){
		this.status = status;
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
