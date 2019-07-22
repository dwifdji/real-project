
package com._360pai.core.condition.assistant;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月27日 12时52分09秒
 */
public class THallEnrollingActivityCondition implements DaoCondition{

	/**
	 * 自增
	 */
	private Integer id;
	/**
	 * 大厅ID
	 */
	private Integer hallId;
	/**
	 * 预招商活动ID
	 */
	private Integer enrollingActivityId;
	/**
	 * 
	 */
	private Integer orderNum;
	/**
	 * 0不删除 1删除
	 */
	private Integer delFlag;
	
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
	 * 大厅ID
	 */
	public Integer getHallId(){
		return hallId;
	}
	
	/**
	 * 大厅ID
	 */
	public void setHallId(Integer hallId){
		this.hallId = hallId;
	}
	
	/**
	 * 预招商活动ID
	 */
	public Integer getEnrollingActivityId(){
		return enrollingActivityId;
	}
	
	/**
	 * 预招商活动ID
	 */
	public void setEnrollingActivityId(Integer enrollingActivityId){
		this.enrollingActivityId = enrollingActivityId;
	}
	
	/**
	 * 
	 */
	public Integer getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 
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