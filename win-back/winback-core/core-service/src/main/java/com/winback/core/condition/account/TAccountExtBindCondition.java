
package com.winback.core.condition.account;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月16日 15时40分01秒
 */
public class TAccountExtBindCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 账户id
	 */
	private java.lang.Integer accountId;
	/**
	 * 外部类型（APPLET：小程序）
	 */
	private java.lang.String extType;
	/**
	 * 外部用户id
	 */
	private java.lang.String extUserId;
	/**
	 * 昵称
	 */
	private java.lang.String nickName;
	/**
	 * 头像
	 */
	private java.lang.String headImgUrl;
	/**
	 * 删除标志（0 否 1 是）
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
	 * 账户id
	 */
	public java.lang.Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 账户id
	 */
	public void setAccountId(java.lang.Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 外部类型（APPLET：小程序）
	 */
	public java.lang.String getExtType(){
		return extType;
	}
	
	/**
	 * 外部类型（APPLET：小程序）
	 */
	public void setExtType(java.lang.String extType){
		this.extType = extType;
	}
	
	/**
	 * 外部用户id
	 */
	public java.lang.String getExtUserId(){
		return extUserId;
	}
	
	/**
	 * 外部用户id
	 */
	public void setExtUserId(java.lang.String extUserId){
		this.extUserId = extUserId;
	}
	
	/**
	 * 昵称
	 */
	public java.lang.String getNickName(){
		return nickName;
	}
	
	/**
	 * 昵称
	 */
	public void setNickName(java.lang.String nickName){
		this.nickName = nickName;
	}
	
	/**
	 * 头像
	 */
	public java.lang.String getHeadImgUrl(){
		return headImgUrl;
	}
	
	/**
	 * 头像
	 */
	public void setHeadImgUrl(java.lang.String headImgUrl){
		this.headImgUrl = headImgUrl;
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