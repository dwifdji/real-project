
package com.winback.core.condition.risk;


import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月23日 16时29分01秒
 */
public class TRiskInvestmentCondition implements DaoCondition {

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 查询关键字
	 */
	private java.lang.String keyWord;
	/**
	 * 被投资人企业名称
	 */
	private java.lang.String comName;
	/**
	 * 法人代表
	 */
	private java.lang.String legalPerson;
	/**
	 * 注册资本
	 */
	private java.lang.String registCapital;
	/**
	 * 出资比例
	 */
	private java.lang.String investScale;
	/**
	 * 成立日期
	 */
	private java.lang.String setupDate;
	/**
	 * 状态
	 */
	private java.lang.String status;
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
	 * 查询关键字
	 */
	public java.lang.String getKeyWord(){
		return keyWord;
	}
	
	/**
	 * 查询关键字
	 */
	public void setKeyWord(java.lang.String keyWord){
		this.keyWord = keyWord;
	}
	
	/**
	 * 被投资人企业名称
	 */
	public java.lang.String getComName(){
		return comName;
	}
	
	/**
	 * 被投资人企业名称
	 */
	public void setComName(java.lang.String comName){
		this.comName = comName;
	}
	
	/**
	 * 法人代表
	 */
	public java.lang.String getLegalPerson(){
		return legalPerson;
	}
	
	/**
	 * 法人代表
	 */
	public void setLegalPerson(java.lang.String legalPerson){
		this.legalPerson = legalPerson;
	}
	
	/**
	 * 注册资本
	 */
	public java.lang.String getRegistCapital(){
		return registCapital;
	}
	
	/**
	 * 注册资本
	 */
	public void setRegistCapital(java.lang.String registCapital){
		this.registCapital = registCapital;
	}
	
	/**
	 * 出资比例
	 */
	public java.lang.String getInvestScale(){
		return investScale;
	}
	
	/**
	 * 出资比例
	 */
	public void setInvestScale(java.lang.String investScale){
		this.investScale = investScale;
	}
	
	/**
	 * 成立日期
	 */
	public java.lang.String getSetupDate(){
		return setupDate;
	}
	
	/**
	 * 成立日期
	 */
	public void setSetupDate(java.lang.String setupDate){
		this.setupDate = setupDate;
	}
	
	/**
	 * 状态
	 */
	public java.lang.String getStatus(){
		return status;
	}
	
	/**
	 * 状态
	 */
	public void setStatus(java.lang.String status){
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