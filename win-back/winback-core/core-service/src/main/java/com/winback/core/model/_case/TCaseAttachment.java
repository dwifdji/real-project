
package com.winback.core.model._case;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月25日 10时50分54秒
 */
public class TCaseAttachment implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 案件id
	 */
	private String caseId;
	/**
	 * 案件状态
	 */
	private String caseStatus;
	/**
	 * 附件名称
	 */
	private String attachName;
	/**
	 * 附件url
	 */
	private String attachUrl;

	private String attachType;

	/**
	 * 附件类型
	 */
	private String fileType;
	/**
	 * 附件大下
	 */
	private Integer fileSize;
	/**
	 * 删除标志0 不删除 1删除
	 */
	private Integer deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
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

	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}

	/**
	 * 案件id
	 */
	public String getCaseId(){
		return caseId;
	}
	
	/**
	 * 案件id
	 */
	public void setCaseId(String caseId){
		this.caseId = caseId;
	}
	
	/**
	 * 案件状态
	 */
	public String getCaseStatus(){
		return caseStatus;
	}
	
	/**
	 * 案件状态
	 */
	public void setCaseStatus(String caseStatus){
		this.caseStatus = caseStatus;
	}
	
	/**
	 * 附件名称
	 */
	public String getAttachName(){
		return attachName;
	}
	
	/**
	 * 附件名称
	 */
	public void setAttachName(String attachName){
		this.attachName = attachName;
	}
	
	/**
	 * 附件url
	 */
	public String getAttachUrl(){
		return attachUrl;
	}
	
	/**
	 * 附件url
	 */
	public void setAttachUrl(String attachUrl){
		this.attachUrl = attachUrl;
	}
	
	/**
	 * 附件类型
	 */
	public String getFileType(){
		return fileType;
	}
	
	/**
	 * 附件类型
	 */
	public void setFileType(String fileType){
		this.fileType = fileType;
	}
	
	/**
	 * 附件大下
	 */
	public Integer getFileSize(){
		return fileSize;
	}
	
	/**
	 * 附件大下
	 */
	public void setFileSize(Integer fileSize){
		this.fileSize = fileSize;
	}
	
	/**
	 * 删除标志0 不删除 1删除
	 */
	public Integer getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志0 不删除 1删除
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
