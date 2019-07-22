
package com.winback.core.model.assistant;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月18日 10时01分28秒
 */
public class TAppReadMessageMap implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 账户id
	 */
	private Integer accountId;
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
	 * 账户id
	 */
	public Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 账户id
	 */
	public void setAccountId(Integer accountId){
		this.accountId = accountId;
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
