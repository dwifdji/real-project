
package com.winback.core.condition.risk;


import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月23日 16时35分57秒
 */
public class TRiskSearchRecordCondition implements DaoCondition {

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 搜索的关键词
	 */
	private java.lang.String keyWord;
	/**
	 * 查询的类型
	 */
	private java.lang.Integer type;
	/**
	 * 搜索的状态
	 */
	private java.lang.Integer status;
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

	private java.lang.String operatorName;

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
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
	 * 搜索的关键词
	 */
	public java.lang.String getKeyWord(){
		return keyWord;
	}
	
	/**
	 * 搜索的关键词
	 */
	public void setKeyWord(java.lang.String keyWord){
		this.keyWord = keyWord;
	}
	
	/**
	 * 查询的类型
	 */
	public java.lang.Integer getType(){
		return type;
	}
	
	/**
	 * 查询的类型
	 */
	public void setType(java.lang.Integer type){
		this.type = type;
	}
	
	/**
	 * 搜索的状态
	 */
	public java.lang.Integer getStatus(){
		return status;
	}
	
	/**
	 * 搜索的状态
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
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