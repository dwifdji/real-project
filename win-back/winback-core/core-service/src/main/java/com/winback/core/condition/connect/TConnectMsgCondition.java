
package com.winback.core.condition.connect;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
public class TConnectMsgCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 房间id
	 */
	private java.lang.Integer roomId;
	/**
	 * 房间人物id
	 */
	private java.lang.Integer personId;
	/**
	 * 消息信息
	 */
	private java.lang.String msg;


	private java.lang.String msgType;

	/**
	 * 删除标志0 不删除 1删除
	 */
	private java.lang.Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;


	private java.lang.Integer caseId;


	private java.lang.String personType;


	private java.lang.String source;

	private java.lang.String timeInfo;//语音信息的时长

	public String getTimeInfo() {
		return timeInfo;
	}

	public void setTimeInfo(String timeInfo) {
		this.timeInfo = timeInfo;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	/**
	 * 
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 房间id
	 */
	public java.lang.Integer getRoomId(){
		return roomId;
	}
	
	/**
	 * 房间id
	 */
	public void setRoomId(java.lang.Integer roomId){
		this.roomId = roomId;
	}
	
	/**
	 * 房间人物id
	 */
	public java.lang.Integer getPersonId(){
		return personId;
	}
	
	/**
	 * 房间人物id
	 */
	public void setPersonId(java.lang.Integer personId){
		this.personId = personId;
	}
	
	/**
	 * 消息信息
	 */
	public java.lang.String getMsg(){
		return msg;
	}
	
	/**
	 * 消息信息
	 */
	public void setMsg(java.lang.String msg){
		this.msg = msg;
	}
	
	/**
	 * 删除标志0 不删除 1删除
	 */
	public java.lang.Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志0 不删除 1删除
	 */
	public void setDeleteFlag(java.lang.Boolean deleteFlag){
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