
package com.winback.core.model.assistant;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年02月26日 19时39分16秒
 */
public class TAppMessageTemplate implements java.io.Serializable{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 大组的分类
	 */
	private java.lang.String groupType;
	/**
	 * 发送类型 1 推送 2 邮件
	 */
	private java.lang.String sendType;
	/**
	 * 消息类型
	 */
	private java.lang.String type;
	/**
	 * 标题
	 */
	private java.lang.String title;
	/**
	 * 内容
	 */
	private java.lang.String content;
	/**
	 * 推送的类型
	 */
	private java.lang.String pushType;
	/**
	 * 发送的目标
	 */
	private java.lang.String pushIds;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private java.lang.Boolean deleteFlag;
	/**
	 * 描述信息
	 */
	private java.lang.String msg;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	
	/**
	 * 主键
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 主键
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 大组的分类
	 */
	public java.lang.String getGroupType(){
		return groupType;
	}
	
	/**
	 * 大组的分类
	 */
	public void setGroupType(java.lang.String groupType){
		this.groupType = groupType;
	}
	
	/**
	 * 发送类型 1 推送 2 邮件
	 */
	public java.lang.String getSendType(){
		return sendType;
	}
	
	/**
	 * 发送类型 1 推送 2 邮件
	 */
	public void setSendType(java.lang.String sendType){
		this.sendType = sendType;
	}
	
	/**
	 * 消息类型
	 */
	public java.lang.String getType(){
		return type;
	}
	
	/**
	 * 消息类型
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	
	/**
	 * 标题
	 */
	public java.lang.String getTitle(){
		return title;
	}
	
	/**
	 * 标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	
	/**
	 * 内容
	 */
	public java.lang.String getContent(){
		return content;
	}
	
	/**
	 * 内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	
	/**
	 * 推送的类型
	 */
	public java.lang.String getPushType(){
		return pushType;
	}
	
	/**
	 * 推送的类型
	 */
	public void setPushType(java.lang.String pushType){
		this.pushType = pushType;
	}
	
	/**
	 * 发送的目标
	 */
	public java.lang.String getPushIds(){
		return pushIds;
	}
	
	/**
	 * 发送的目标
	 */
	public void setPushIds(java.lang.String pushIds){
		this.pushIds = pushIds;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public java.lang.Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public void setDeleteFlag(java.lang.Boolean deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	
	/**
	 * 描述信息
	 */
	public java.lang.String getMsg(){
		return msg;
	}
	
	/**
	 * 描述信息
	 */
	public void setMsg(java.lang.String msg){
		this.msg = msg;
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
