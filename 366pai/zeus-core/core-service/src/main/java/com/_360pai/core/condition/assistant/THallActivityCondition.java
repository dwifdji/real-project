
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月27日 12时52分09秒
 */
public class THallActivityCondition implements DaoCondition{

	/**
	 * 自增主键
	 */
	private Integer id;
	/**
	 * 大厅类型ID
	 */
	private Integer hallId;
	/**
	 * 活动ID
	 */
	private Integer activityId;
	/**
	 * 排序编号
	 */
	private Integer orderNum;
	/**
	 * 0不删除 1删除
	 */
	private Integer delFlag;
	
	/**
	 * 自增主键
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 自增主键
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * 大厅类型ID
	 */
	public Integer getHallId(){
		return hallId;
	}
	
	/**
	 * 大厅类型ID
	 */
	public void setHallId(Integer hallId){
		this.hallId = hallId;
	}
	
	/**
	 * 活动ID
	 */
	public Integer getActivityId(){
		return activityId;
	}
	
	/**
	 * 活动ID
	 */
	public void setActivityId(Integer activityId){
		this.activityId = activityId;
	}
	
	/**
	 * 排序编号
	 */
	public Integer getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 排序编号
	 */
	public void setOrderNum(Integer orderNum){
		this.orderNum = orderNum;
	}
	
	/**
	 * 0不删除 1删除
	 */
	public Integer getDelFlag(){
		return delFlag;
	}
	
	/**
	 * 0不删除 1删除
	 */
	public void setDelFlag(Integer delFlag){
		this.delFlag = delFlag;
	}

}