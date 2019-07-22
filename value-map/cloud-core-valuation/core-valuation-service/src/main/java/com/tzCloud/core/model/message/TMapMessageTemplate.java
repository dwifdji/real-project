
package com.tzCloud.core.model.message;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年07月05日 14时34分53秒
 */
public class TMapMessageTemplate implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 大组的分类
	 */
	private String groupType;
	/**
	 * 发送类型 1 推送 2 邮件
	 */
	private String sendType;
	/**
	 * 消息类型
	 */
	private String type;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 推送的类型
	 */
	private String pushType;
	/**
	 * 发送的目标
	 */
	private String pushIds;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
	/**
	 * 描述信息
	 */
	private String msg;
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
	 * 大组的分类
	 */
	public String getGroupType(){
		return groupType;
	}
	
	/**
	 * 大组的分类
	 */
	public void setGroupType(String groupType){
		this.groupType = groupType;
	}
	
	/**
	 * 发送类型 1 推送 2 邮件
	 */
	public String getSendType(){
		return sendType;
	}
	
	/**
	 * 发送类型 1 推送 2 邮件
	 */
	public void setSendType(String sendType){
		this.sendType = sendType;
	}
	
	/**
	 * 消息类型
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 消息类型
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 标题
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * 标题
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * 内容
	 */
	public String getContent(){
		return content;
	}
	
	/**
	 * 内容
	 */
	public void setContent(String content){
		this.content = content;
	}
	
	/**
	 * 推送的类型
	 */
	public String getPushType(){
		return pushType;
	}
	
	/**
	 * 推送的类型
	 */
	public void setPushType(String pushType){
		this.pushType = pushType;
	}
	
	/**
	 * 发送的目标
	 */
	public String getPushIds(){
		return pushIds;
	}
	
	/**
	 * 发送的目标
	 */
	public void setPushIds(String pushIds){
		this.pushIds = pushIds;
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
	 * 描述信息
	 */
	public String getMsg(){
		return msg;
	}
	
	/**
	 * 描述信息
	 */
	public void setMsg(String msg){
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
