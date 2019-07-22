
package com.winback.core.model.systemsite;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月23日 10时13分56秒
 */
public class TCaseStatusMsgTemplate implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 案件进度
	 */
	private String caseStatus;
	/**
	 * 消息体
	 */
	private String msgBody;
	/**
	 * 1app
	 */
	private Integer type;
	/**
	 * 0开通1关闭
	 */
	private Boolean status;
	/**
	 * 删除标识
	 */
	private Boolean deleteFlag;
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
	 * 编码
	 */
	public String getCode(){
		return code;
	}
	
	/**
	 * 编码
	 */
	public void setCode(String code){
		this.code = code;
	}
	
	/**
	 * 案件进度
	 */
	public String getCaseStatus(){
		return caseStatus;
	}
	
	/**
	 * 案件进度
	 */
	public void setCaseStatus(String caseStatus){
		this.caseStatus = caseStatus;
	}
	
	/**
	 * 消息体
	 */
	public String getMsgBody(){
		return msgBody;
	}
	
	/**
	 * 消息体
	 */
	public void setMsgBody(String msgBody){
		this.msgBody = msgBody;
	}
	
	/**
	 * 1app
	 */
	public Integer getType(){
		return type;
	}
	
	/**
	 * 1app
	 */
	public void setType(Integer type){
		this.type = type;
	}
	
	/**
	 * 0开通1关闭
	 */
	public Boolean getStatus(){
		return status;
	}
	
	/**
	 * 0开通1关闭
	 */
	public void setStatus(Boolean status){
		this.status = status;
	}
	
	/**
	 * 删除标识
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标识
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
