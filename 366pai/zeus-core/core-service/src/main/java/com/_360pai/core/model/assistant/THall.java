
package com._360pai.core.model.assistant;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年10月08日 13时24分35秒
 */
public class THall implements java.io.Serializable{

	/**
	 * 自增
	 */
	private Integer id;
	/**
	 * 大厅中的类型名称
	 */
	private String hallName;
	/**
	 * 1：拍卖大厅  2:预招商大厅
	 */
	private Integer hallType;
	/**
	 * 排序编号
	 */
	private Integer orderNum;
	/**
	 * 0不删除  1删除
	 */
	private Integer delFlag;
	/**
	 * 预招商类型 默认:0   1:抵押物 2:债权 3:物权
	 */
	private Integer type;
	
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
	 * 大厅中的类型名称
	 */
	public String getHallName(){
		return hallName;
	}
	
	/**
	 * 大厅中的类型名称
	 */
	public void setHallName(String hallName){
		this.hallName = hallName;
	}
	
	/**
	 * 1：拍卖大厅  2:预招商大厅
	 */
	public Integer getHallType(){
		return hallType;
	}
	
	/**
	 * 1：拍卖大厅  2:预招商大厅
	 */
	public void setHallType(Integer hallType){
		this.hallType = hallType;
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
	 * 0不删除  1删除
	 */
	public Integer getDelFlag(){
		return delFlag;
	}
	
	/**
	 * 0不删除  1删除
	 */
	public void setDelFlag(Integer delFlag){
		this.delFlag = delFlag;
	}
	
	/**
	 * 预招商类型 默认:0   1:抵押物 2:债权 3:物权
	 */
	public Integer getType(){
		return type;
	}
	
	/**
	 * 预招商类型 默认:0   1:抵押物 2:债权 3:物权
	 */
	public void setType(Integer type){
		this.type = type;
	}

}
