
package com.winback.gateway.condition.check;


import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月24日 09时20分51秒
 */
public class TGatewayCheckRecordCondition implements DaoCondition {

	/**
	 * 主键，案件编号
	 */
	private java.lang.Integer id;
	/**
	 * 支付类型1 支付宝 2 微信
	 */
	private java.lang.String type;
	/**
	 * 业务类型
	 */
	private java.lang.String businessType;
	/**
	 * 请求参数
	 */
	private java.lang.String reqParam;
	/**
	 * 返回参数
	 */
	private java.lang.String respParam;
	/**
	 * 请求状态0 初始状态 1 请求成功 2 系统异常
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
	
	/**
	 * 主键，案件编号
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 主键，案件编号
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 支付类型1 支付宝 2 微信
	 */
	public java.lang.String getType(){
		return type;
	}
	
	/**
	 * 支付类型1 支付宝 2 微信
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	
	/**
	 * 业务类型
	 */
	public java.lang.String getBusinessType(){
		return businessType;
	}
	
	/**
	 * 业务类型
	 */
	public void setBusinessType(java.lang.String businessType){
		this.businessType = businessType;
	}
	
	/**
	 * 请求参数
	 */
	public java.lang.String getReqParam(){
		return reqParam;
	}
	
	/**
	 * 请求参数
	 */
	public void setReqParam(java.lang.String reqParam){
		this.reqParam = reqParam;
	}
	
	/**
	 * 返回参数
	 */
	public java.lang.String getRespParam(){
		return respParam;
	}
	
	/**
	 * 返回参数
	 */
	public void setRespParam(java.lang.String respParam){
		this.respParam = respParam;
	}
	
	/**
	 * 请求状态0 初始状态 1 请求成功 2 系统异常
	 */
	public java.lang.Integer getStatus(){
		return status;
	}
	
	/**
	 * 请求状态0 初始状态 1 请求成功 2 系统异常
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