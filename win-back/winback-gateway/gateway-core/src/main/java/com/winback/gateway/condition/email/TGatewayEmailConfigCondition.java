
package com.winback.gateway.condition.email;


import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年03月07日 20时57分16秒
 */
public class TGatewayEmailConfigCondition implements DaoCondition {

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 业务分类
	 */
	private java.lang.String busType;
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
	 * 发送的目标邮件
	 */
	private java.lang.String emails;
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
	 * 业务分类
	 */
	public java.lang.String getBusType(){
		return busType;
	}
	
	/**
	 * 业务分类
	 */
	public void setBusType(java.lang.String busType){
		this.busType = busType;
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
	 * 发送的目标邮件
	 */
	public java.lang.String getEmails(){
		return emails;
	}
	
	/**
	 * 发送的目标邮件
	 */
	public void setEmails(java.lang.String emails){
		this.emails = emails;
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