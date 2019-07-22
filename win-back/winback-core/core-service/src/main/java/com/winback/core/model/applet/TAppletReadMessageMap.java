
package com.winback.core.model.applet;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月18日 10时01分28秒
 */
public class TAppletReadMessageMap implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 账户外部关系绑定表id
	 */
	private Integer extBindId;
	/**
	 * 已读消息id
	 */
	private Integer messageId;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
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
	 * 账户外部关系绑定表id
	 */
	public Integer getExtBindId(){
		return extBindId;
	}
	
	/**
	 * 账户外部关系绑定表id
	 */
	public void setExtBindId(Integer extBindId){
		this.extBindId = extBindId;
	}
	
	/**
	 * 已读消息id
	 */
	public Integer getMessageId(){
		return messageId;
	}
	
	/**
	 * 已读消息id
	 */
	public void setMessageId(Integer messageId){
		this.messageId = messageId;
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

}
