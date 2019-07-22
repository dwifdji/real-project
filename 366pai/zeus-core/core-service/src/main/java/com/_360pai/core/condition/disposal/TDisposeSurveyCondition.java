
package com._360pai.core.condition.disposal;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年10月29日 12时44分58秒
 */
public class TDisposeSurveyCondition implements DaoCondition{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 需求编号
	 */
	private String surveyNo;
	/**
	 * 拍品id
	 */
	private Integer assetId;
	/**
	 * 处置服务商id
	 */
	private Integer providerId;
	/**
	 * 拍品名称
	 */
	private String assetName;
	/**
	 * 城市Id
	 */
	private String cityId;
	/**
	 * 委托时间
	 */
	private java.util.Date assignTime;
	/**
	 * 00:待接受、10:待上传、20:已上传、30:已失效
	 */
	private String surveyStatus;
	/**
	 * 基础尽调
	 */
	private String basisSurvey;
	/**
	 * 基础尽调价格
	 */
	private java.math.BigDecimal basisSurveyPrice;
	/**
	 * 完整尽调
	 */
	private String completeSurvey;
	/**
	 * 完整尽调价格
	 */
	private java.math.BigDecimal completeSurveyPrice;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date updateTime;
	/**
	 * 0: 未删除 1:删除
	 */
	private Boolean delFlag;
	
	/**
	 * 主键id
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键id
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 需求编号
	 */
	public String getSurveyNo(){
		return surveyNo;
	}
	
	/**
	 * 需求编号
	 */
	public void setSurveyNo(String surveyNo){
		this.surveyNo = surveyNo;
	}
	
	/**
	 * 拍品id
	 */
	public Integer getAssetId(){
		return assetId;
	}
	
	/**
	 * 拍品id
	 */
	public void setAssetId(Integer assetId){
		this.assetId = assetId;
	}
	
	/**
	 * 处置服务商id
	 */
	public Integer getProviderId(){
		return providerId;
	}
	
	/**
	 * 处置服务商id
	 */
	public void setProviderId(Integer providerId){
		this.providerId = providerId;
	}
	
	/**
	 * 拍品名称
	 */
	public String getAssetName(){
		return assetName;
	}
	
	/**
	 * 拍品名称
	 */
	public void setAssetName(String assetName){
		this.assetName = assetName;
	}
	
	/**
	 * 城市Id
	 */
	public String getCityId(){
		return cityId;
	}
	
	/**
	 * 城市Id
	 */
	public void setCityId(String cityId){
		this.cityId = cityId;
	}
	
	/**
	 * 委托时间
	 */
	public java.util.Date getAssignTime(){
		return assignTime;
	}
	
	/**
	 * 委托时间
	 */
	public void setAssignTime(java.util.Date assignTime){
		this.assignTime = assignTime;
	}
	
	/**
	 * 00:待接受、10:待上传、20:已上传、30:已失效
	 */
	public String getSurveyStatus(){
		return surveyStatus;
	}
	
	/**
	 * 00:待接受、10:待上传、20:已上传、30:已失效
	 */
	public void setSurveyStatus(String surveyStatus){
		this.surveyStatus = surveyStatus;
	}
	
	/**
	 * 基础尽调
	 */
	public String getBasisSurvey(){
		return basisSurvey;
	}
	
	/**
	 * 基础尽调
	 */
	public void setBasisSurvey(String basisSurvey){
		this.basisSurvey = basisSurvey;
	}
	
	/**
	 * 基础尽调价格
	 */
	public java.math.BigDecimal getBasisSurveyPrice(){
		return basisSurveyPrice;
	}
	
	/**
	 * 基础尽调价格
	 */
	public void setBasisSurveyPrice(java.math.BigDecimal basisSurveyPrice){
		this.basisSurveyPrice = basisSurveyPrice;
	}
	
	/**
	 * 完整尽调
	 */
	public String getCompleteSurvey(){
		return completeSurvey;
	}
	
	/**
	 * 完整尽调
	 */
	public void setCompleteSurvey(String completeSurvey){
		this.completeSurvey = completeSurvey;
	}
	
	/**
	 * 完整尽调价格
	 */
	public java.math.BigDecimal getCompleteSurveyPrice(){
		return completeSurveyPrice;
	}
	
	/**
	 * 完整尽调价格
	 */
	public void setCompleteSurveyPrice(java.math.BigDecimal completeSurveyPrice){
		this.completeSurveyPrice = completeSurveyPrice;
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
	 * 修改时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 修改时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}
	
	/**
	 * 0: 未删除 1:删除
	 */
	public Boolean getDelFlag(){
		return delFlag;
	}
	
	/**
	 * 0: 未删除 1:删除
	 */
	public void setDelFlag(Boolean delFlag){
		this.delFlag = delFlag;
	}

}