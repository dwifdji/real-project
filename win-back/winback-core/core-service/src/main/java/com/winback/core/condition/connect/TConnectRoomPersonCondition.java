
package com.winback.core.condition.connect;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
public class TConnectRoomPersonCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 房间id
	 */
	private java.lang.Integer roomId;

	private java.lang.Integer caseId;

	/**
	 * 类型 1 当事人 2 律师 3 业务员 4 平台客服
	 */
	private java.lang.String type;
	/**
	 * 关联id 为空时取角色下所有的id
	 */
	private java.lang.Integer relevanceId;
	/**
	 * 删除标志0 不删除 1删除
	 */
	private java.lang.Boolean deleteFlag;
	/**
	 * 禁言标志0 不禁言 1 禁言
	 */
	private java.lang.Boolean shutupFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;


	private java.lang.Integer unreadNum;

	public Integer getUnreadNum() {
		return unreadNum;
	}

	public void setUnreadNum(Integer unreadNum) {
		this.unreadNum = unreadNum;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
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
	 * 类型 1 当事人 2 律师 3 业务员 4 平台客服
	 */
	public java.lang.String getType(){
		return type;
	}
	
	/**
	 * 类型 1 当事人 2 律师 3 业务员 4 平台客服
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	
	/**
	 * 关联id 为空时取角色下所有的id
	 */
	public java.lang.Integer getRelevanceId(){
		return relevanceId;
	}
	
	/**
	 * 关联id 为空时取角色下所有的id
	 */
	public void setRelevanceId(java.lang.Integer relevanceId){
		this.relevanceId = relevanceId;
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
	 * 禁言标志0 不禁言 1 禁言
	 */
	public java.lang.Boolean getShutupFlag(){
		return shutupFlag;
	}
	
	/**
	 * 禁言标志0 不禁言 1 禁言
	 */
	public void setShutupFlag(java.lang.Boolean shutupFlag){
		this.shutupFlag = shutupFlag;
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