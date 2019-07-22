
package com.winback.gateway.controller.req.email;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年10月28日 12时39分36秒
 */
public class GatewayEmailRecordReq implements java.io.Serializable{

	/**
	 *
	 */
	private Integer id;
	/**
	 * 发送的邮件
	 */
	private String email;
	/**
	 * 抄送的邮件
	 */
	private String copyEmail;
	/**
	 * 发送方式
	 */
	private String sendType;
	/**
	 * 邮件模板
	 */
	private String templateCode;
	/**
	 * 邮件模板参数
	 */
	private String templateParam;
	/**
	 * 邮件标题
	 */
	private String title;
	/**
	 * 邮件内容
	 */
	private String content;
	/**
	 * 短信发送的状态
	 */
	private String status;
	/**
	 * 备注信息
	 */
	private String msg;
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
	 * 发送的邮件
	 */
	public String getEmail(){
		return email;
	}

	/**
	 * 发送的邮件
	 */
	public void setEmail(String email){
		this.email = email;
	}

	/**
	 * 抄送的邮件
	 */
	public String getCopyEmail(){
		return copyEmail;
	}

	/**
	 * 抄送的邮件
	 */
	public void setCopyEmail(String copyEmail){
		this.copyEmail = copyEmail;
	}

	/**
	 * 发送方式
	 */
	public String getSendType(){
		return sendType;
	}

	/**
	 * 发送方式
	 */
	public void setSendType(String sendType){
		this.sendType = sendType;
	}

	/**
	 * 邮件模板
	 */
	public String getTemplateCode(){
		return templateCode;
	}

	/**
	 * 邮件模板
	 */
	public void setTemplateCode(String templateCode){
		this.templateCode = templateCode;
	}

	/**
	 * 邮件模板参数
	 */
	public String getTemplateParam(){
		return templateParam;
	}

	/**
	 * 邮件模板参数
	 */
	public void setTemplateParam(String templateParam){
		this.templateParam = templateParam;
	}

	/**
	 * 邮件标题
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * 邮件标题
	 */
	public void setTitle(String title){
		this.title = title;
	}

	/**
	 * 邮件内容
	 */
	public String getContent(){
		return content;
	}

	/**
	 * 邮件内容
	 */
	public void setContent(String content){
		this.content = content;
	}

	/**
	 * 短信发送的状态
	 */
	public String getStatus(){
		return status;
	}

	/**
	 * 短信发送的状态
	 */
	public void setStatus(String status){
		this.status = status;
	}

	/**
	 * 备注信息
	 */
	public String getMsg(){
		return msg;
	}

	/**
	 * 备注信息
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
