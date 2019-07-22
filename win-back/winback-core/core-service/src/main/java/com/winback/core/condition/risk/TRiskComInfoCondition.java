
package com.winback.core.condition.risk;


import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月23日 18时45分29秒
 */
public class TRiskComInfoCondition implements DaoCondition {

	/**
	 * 
	 */
	private java.lang.Integer id;
	/**
	 * 公司名称
	 */
	private java.lang.String name;
	/**
	 * 1 公司 2 个人
	 */
	private java.lang.String type;
	/**
	 * 1 正常 2 异常
	 */
	private java.lang.Integer exceptionFlag;
	/**
	 * 注册资本
	 */
	private java.lang.String registerCapital;
	/**
	 * 公司状态
	 */
	private java.lang.String status;
	/**
	 * 注册地
	 */
	private java.lang.String registerArea;
	/**
	 * 公司类型
	 */
	private java.lang.String comType;
	/**
	 * 原告笔数
	 */
	private java.lang.String accuserNum;
	/**
	 * 被告笔数
	 */
	private java.lang.String defendantNum;
	/**
	 * 被执行笔数
	 */
	private java.lang.String executeNum;
	/**
	 * 失信笔数
	 */
	private java.lang.String loseCreditNum;
	/**
	 * 股东信息
	 */
	private java.lang.String shareholdersInfo;
	/**
	 * 财产线索
	 */
	private java.lang.String propertyClue;
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
	 * 公司名称
	 */
	public java.lang.String getName(){
		return name;
	}
	
	/**
	 * 公司名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	
	/**
	 * 1 公司 2 个人
	 */
	public java.lang.String getType(){
		return type;
	}
	
	/**
	 * 1 公司 2 个人
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	
	/**
	 * 1 正常 2 异常
	 */
	public java.lang.Integer getExceptionFlag(){
		return exceptionFlag;
	}
	
	/**
	 * 1 正常 2 异常
	 */
	public void setExceptionFlag(java.lang.Integer exceptionFlag){
		this.exceptionFlag = exceptionFlag;
	}
	
	/**
	 * 注册资本
	 */
	public java.lang.String getRegisterCapital(){
		return registerCapital;
	}
	
	/**
	 * 注册资本
	 */
	public void setRegisterCapital(java.lang.String registerCapital){
		this.registerCapital = registerCapital;
	}
	
	/**
	 * 公司状态
	 */
	public java.lang.String getStatus(){
		return status;
	}
	
	/**
	 * 公司状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	
	/**
	 * 注册地
	 */
	public java.lang.String getRegisterArea(){
		return registerArea;
	}
	
	/**
	 * 注册地
	 */
	public void setRegisterArea(java.lang.String registerArea){
		this.registerArea = registerArea;
	}
	
	/**
	 * 公司类型
	 */
	public java.lang.String getComType(){
		return comType;
	}
	
	/**
	 * 公司类型
	 */
	public void setComType(java.lang.String comType){
		this.comType = comType;
	}
	
	/**
	 * 原告笔数
	 */
	public java.lang.String getAccuserNum(){
		return accuserNum;
	}
	
	/**
	 * 原告笔数
	 */
	public void setAccuserNum(java.lang.String accuserNum){
		this.accuserNum = accuserNum;
	}
	
	/**
	 * 被告笔数
	 */
	public java.lang.String getDefendantNum(){
		return defendantNum;
	}
	
	/**
	 * 被告笔数
	 */
	public void setDefendantNum(java.lang.String defendantNum){
		this.defendantNum = defendantNum;
	}
	
	/**
	 * 被执行笔数
	 */
	public java.lang.String getExecuteNum(){
		return executeNum;
	}
	
	/**
	 * 被执行笔数
	 */
	public void setExecuteNum(java.lang.String executeNum){
		this.executeNum = executeNum;
	}
	
	/**
	 * 失信笔数
	 */
	public java.lang.String getLoseCreditNum(){
		return loseCreditNum;
	}
	
	/**
	 * 失信笔数
	 */
	public void setLoseCreditNum(java.lang.String loseCreditNum){
		this.loseCreditNum = loseCreditNum;
	}
	
	/**
	 * 股东信息
	 */
	public java.lang.String getShareholdersInfo(){
		return shareholdersInfo;
	}
	
	/**
	 * 股东信息
	 */
	public void setShareholdersInfo(java.lang.String shareholdersInfo){
		this.shareholdersInfo = shareholdersInfo;
	}
	
	/**
	 * 财产线索
	 */
	public java.lang.String getPropertyClue(){
		return propertyClue;
	}
	
	/**
	 * 财产线索
	 */
	public void setPropertyClue(java.lang.String propertyClue){
		this.propertyClue = propertyClue;
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