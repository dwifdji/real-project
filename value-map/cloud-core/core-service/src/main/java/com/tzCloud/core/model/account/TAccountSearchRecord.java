
package com.tzCloud.core.model.account;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月19日 13时33分42秒
 */
public class TAccountSearchRecord implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 账户id
	 */
	private Integer accountId;
	/**
	 * 搜索类型（1 案件 2 律师 3 律所 4 法院）
	 */
	private String type;
	/**
	 * 搜索内容
	 */
	private String content;
	/**
	 * 删除标志
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
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
	 * 搜索类型（1 案件 2 律师 3 律所 4 法院）
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 搜索类型（1 案件 2 律师 3 律所 4 法院）
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 搜索内容
	 */
	public String getContent(){
		return content;
	}
	
	/**
	 * 搜索内容
	 */
	public void setContent(String content){
		this.content = content;
	}
	
	/**
	 * 删除标志
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志
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
