
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年12月21日 15时29分00秒
 */
public class TPreemptivePersonCondition implements DaoCondition {

	private Integer askResult;

	/**
	 * 自增
	 */
	private Integer id;
	/**
	 * 优先购买人名称
	 */
	private String preemptivePersonName;
	/**
	 * 优先购买人身份证
	 */
	private String preemptivePersonCard;
	/**
	 * 标的ID
	 */
	private Integer assetId;
	/**
	 * 购买等级
	 */
	private String level;
	/**
	 * 创建时间
	 */
	private java.util.Date createdTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updatedTime;
	/**
	 * 是否删除0未删除 1删除
	 */
	private Integer delFlag;

	public Integer getAskResult() {
		return askResult;
	}

	public void setAskResult(Integer askResult) {
		this.askResult = askResult;
	}

	/**
	 * 自增
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 自增
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 优先购买人名称
	 */
	public String getPreemptivePersonName(){
		return preemptivePersonName;
	}
	
	/**
	 * 优先购买人名称
	 */
	public void setPreemptivePersonName(String preemptivePersonName){
		this.preemptivePersonName = preemptivePersonName;
	}
	
	/**
	 * 优先购买人身份证
	 */
	public String getPreemptivePersonCard(){
		return preemptivePersonCard;
	}
	
	/**
	 * 优先购买人身份证
	 */
	public void setPreemptivePersonCard(String preemptivePersonCard){
		this.preemptivePersonCard = preemptivePersonCard;
	}
	
	/**
	 * 标的ID
	 */
	public Integer getAssetId(){
		return assetId;
	}
	
	/**
	 * 标的ID
	 */
	public void setAssetId(Integer assetId){
		this.assetId = assetId;
	}
	
	/**
	 * 购买等级
	 */
	public String getLevel(){
		return level;
	}
	
	/**
	 * 购买等级
	 */
	public void setLevel(String level){
		this.level = level;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreatedTime(){
		return createdTime;
	}
	
	/**
	 * 创建时间
	 */
	public void setCreatedTime(java.util.Date createdTime){
		this.createdTime = createdTime;
	}
	
	/**
	 * 更新时间
	 */
	public java.util.Date getUpdatedTime(){
		return updatedTime;
	}
	
	/**
	 * 更新时间
	 */
	public void setUpdatedTime(java.util.Date updatedTime){
		this.updatedTime = updatedTime;
	}
	
	/**
	 * 是否删除0未删除 1删除
	 */
	public Integer getDelFlag(){
		return delFlag;
	}
	
	/**
	 * 是否删除0未删除 1删除
	 */
	public void setDelFlag(Integer delFlag){
		this.delFlag = delFlag;
	}

}