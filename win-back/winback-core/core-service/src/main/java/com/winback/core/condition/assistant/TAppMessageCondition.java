
package com.winback.core.condition.assistant;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月18日 10时01分28秒
 */
public class TAppMessageCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 账户id
	 */
	private Integer accountId;
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
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;

	/**
	 * 参数信息
	 */
	private String param;


	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

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
	 * 账户id
	 */
	public Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 账户id
	 */
	public void setAccountId(Integer accountId){
		this.accountId = accountId;
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