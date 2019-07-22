
package com._360pai.core.condition.email;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年10月28日 12时39分36秒
 */
public class GatewayEmailRecordCondition implements DaoCondition{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 发送的邮件
	 */
	private java.lang.String email;
	/**
	 * 抄送的邮件
	 */
	private java.lang.String copyEmail;
	/**
	 * 发送方式
	 */
	private java.lang.String sendType;
	/**
	 * 邮件模板
	 */
	private java.lang.String templateCode;
	/**
	 * 邮件模板参数
	 */
	private java.lang.String templateParam;
	/**
	 * 邮件标题
	 */
	private java.lang.String title;
	/**
	 * 邮件内容
	 */
	private java.lang.String content;
	/**
	 * 短信发送的状态
	 */
	private java.lang.String status;
	/**
	 * 备注信息
	 */
	private java.lang.String msg;
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
	 * 发送的邮件
	 */
	public java.lang.String getEmail(){
		return email;
	}
	
	/**
	 * 发送的邮件
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
	}
	
	/**
	 * 抄送的邮件
	 */
	public java.lang.String getCopyEmail(){
		return copyEmail;
	}
	
	/**
	 * 抄送的邮件
	 */
	public void setCopyEmail(java.lang.String copyEmail){
		this.copyEmail = copyEmail;
	}
	
	/**
	 * 发送方式
	 */
	public java.lang.String getSendType(){
		return sendType;
	}
	
	/**
	 * 发送方式
	 */
	public void setSendType(java.lang.String sendType){
		this.sendType = sendType;
	}
	
	/**
	 * 邮件模板
	 */
	public java.lang.String getTemplateCode(){
		return templateCode;
	}
	
	/**
	 * 邮件模板
	 */
	public void setTemplateCode(java.lang.String templateCode){
		this.templateCode = templateCode;
	}
	
	/**
	 * 邮件模板参数
	 */
	public java.lang.String getTemplateParam(){
		return templateParam;
	}
	
	/**
	 * 邮件模板参数
	 */
	public void setTemplateParam(java.lang.String templateParam){
		this.templateParam = templateParam;
	}
	
	/**
	 * 邮件标题
	 */
	public java.lang.String getTitle(){
		return title;
	}
	
	/**
	 * 邮件标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	
	/**
	 * 邮件内容
	 */
	public java.lang.String getContent(){
		return content;
	}
	
	/**
	 * 邮件内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	
	/**
	 * 短信发送的状态
	 */
	public java.lang.String getStatus(){
		return status;
	}
	
	/**
	 * 短信发送的状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	
	/**
	 * 备注信息
	 */
	public java.lang.String getMsg(){
		return msg;
	}
	
	/**
	 * 备注信息
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