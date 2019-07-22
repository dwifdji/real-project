
package com._360pai.core.model.assistant;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月27日 12时52分09秒
 */
public class THallActivity implements java.io.Serializable{

	/**
	 * 自增主键
	 */
	private Integer id;
	/**
	 * 大厅类型ID
	 */
	private Integer hallId;
	/**
	 * 大厅类型名称
	 */
	private String hallName;
	/**
	 * 活动ID
	 */
	private Integer activityId;
	/**
	 * 活动名称
	 */
	private String activityName;
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

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
}
