
package com._360pai.core.model.assistant;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月27日 12时52分09秒
 */
public class THallEnrollingActivity implements java.io.Serializable{

	/**
	 * 自增
	 */
	private Integer id;
	/**
	 * 大厅ID
	 */
	private Integer hallId;
	/**
	 * 大厅ID
	 */
	private String hallName;
	/**
	 * 预招商活动ID
	 */
	private Integer enrollingActivityId;
	/**
	 * 预招商活动名称
	 */
	private String enrollingActivityName;
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

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public String getEnrollingActivityName() {
		return enrollingActivityName;
	}

	public void setEnrollingActivityName(String enrollingActivityName) {
		this.enrollingActivityName = enrollingActivityName;
	}
}
