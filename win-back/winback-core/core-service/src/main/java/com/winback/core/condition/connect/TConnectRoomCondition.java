
package com.winback.core.condition.connect;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
public class TConnectRoomCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 房间名称
	 */
	private java.lang.String name;
	/**
	 * 案件名称
	 */
	private java.lang.Integer caseId;
	/**
	 * 房间开发标志0 开放 1已关闭
	 */
	private java.lang.Boolean openFlag;
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
	 * 房间名称
	 */
	public java.lang.String getName(){
		return name;
	}
	
	/**
	 * 房间名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	
	/**
	 * 案件名称
	 */
	public java.lang.Integer getCaseId(){
		return caseId;
	}
	
	/**
	 * 案件名称
	 */
	public void setCaseId(java.lang.Integer caseId){
		this.caseId = caseId;
	}
	
	/**
	 * 房间开发标志0 开放 1已关闭
	 */
	public java.lang.Boolean getOpenFlag(){
		return openFlag;
	}
	
	/**
	 * 房间开发标志0 开放 1已关闭
	 */
	public void setOpenFlag(java.lang.Boolean openFlag){
		this.openFlag = openFlag;
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