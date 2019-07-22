
package com.tzCloud.core.model.applicant;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年07月05日 13时25分49秒
 */
public class TMapApplicant implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 姓名
	 */
	private String applicantName;
	/**
	 * 联系方式
	 */
	private String applicantPhone;
	/**
	 * 留言备注
	 */
	private String remark;
	/**
	 * 删除标识
	 */
	private Integer deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;
	
	/**
	 * 主键
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 姓名
	 */
	public String getApplicantName(){
		return applicantName;
	}
	
	/**
	 * 姓名
	 */
	public void setApplicantName(String applicantName){
		this.applicantName = applicantName;
	}
	
	/**
	 * 联系方式
	 */
	public String getApplicantPhone(){
		return applicantPhone;
	}
	
	/**
	 * 联系方式
	 */
	public void setApplicantPhone(String applicantPhone){
		this.applicantPhone = applicantPhone;
	}
	
	/**
	 * 留言备注
	 */
	public String getRemark(){
		return remark;
	}
	
	/**
	 * 留言备注
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	/**
	 * 删除标识
	 */
	public Integer getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标识
	 */
	public void setDeleteFlag(Integer deleteFlag){
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
