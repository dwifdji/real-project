
package com.winback.gateway.model.sms;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月16日 19时33分16秒
 */
public class TGatewaySmsRecord implements java.io.Serializable{

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 发送的短信号码
	 */
	private java.lang.String phone;
	/**
	 * 发送短信的模板
	 */
	private java.lang.String templateCode;
	/**
	 * 发送短信的模板参数
	 */
	private java.lang.String templateParam;
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
	 * 短信模板
	 */
	private java.lang.String templateContent;
	
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
	 * 发送的短信号码
	 */
	public java.lang.String getPhone(){
		return phone;
	}
	
	/**
	 * 发送的短信号码
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	
	/**
	 * 发送短信的模板
	 */
	public java.lang.String getTemplateCode(){
		return templateCode;
	}
	
	/**
	 * 发送短信的模板
	 */
	public void setTemplateCode(java.lang.String templateCode){
		this.templateCode = templateCode;
	}
	
	/**
	 * 发送短信的模板参数
	 */
	public java.lang.String getTemplateParam(){
		return templateParam;
	}
	
	/**
	 * 发送短信的模板参数
	 */
	public void setTemplateParam(java.lang.String templateParam){
		this.templateParam = templateParam;
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
	
	/**
	 * 短信模板
	 */
	public java.lang.String getTemplateContent(){
		return templateContent;
	}
	
	/**
	 * 短信模板
	 */
	public void setTemplateContent(java.lang.String templateContent){
		this.templateContent = templateContent;
	}

}
