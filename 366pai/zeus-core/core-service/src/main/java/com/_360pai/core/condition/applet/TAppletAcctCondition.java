
package com._360pai.core.condition.applet;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
public class TAppletAcctCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * user、compny、agency
	 */
	private String type;
	/**
	 * 
	 */
	private Integer partyId;
	/**
	 * 总金额
	 */
	private java.math.BigDecimal totalAmt;
	/**
	 * 锁定金额
	 */
	private java.math.BigDecimal lockAmt;
	/**
	 * 可用余额
	 */
	private java.math.BigDecimal availAmt;
	/**
	 * 
	 */
	private java.util.Date createTime;
	/**
	 * 
	 */
	private java.util.Date updateTime;
	
	/**
	 * 
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * user、compny、agency
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * user、compny、agency
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 
	 */
	public Integer getPartyId(){
		return partyId;
	}
	
	/**
	 * 
	 */
	public void setPartyId(Integer partyId){
		this.partyId = partyId;
	}
	
	/**
	 * 总金额
	 */
	public java.math.BigDecimal getTotalAmt(){
		return totalAmt;
	}
	
	/**
	 * 总金额
	 */
	public void setTotalAmt(java.math.BigDecimal totalAmt){
		this.totalAmt = totalAmt;
	}
	
	/**
	 * 锁定金额
	 */
	public java.math.BigDecimal getLockAmt(){
		return lockAmt;
	}
	
	/**
	 * 锁定金额
	 */
	public void setLockAmt(java.math.BigDecimal lockAmt){
		this.lockAmt = lockAmt;
	}
	
	/**
	 * 可用余额
	 */
	public java.math.BigDecimal getAvailAmt(){
		return availAmt;
	}
	
	/**
	 * 可用余额
	 */
	public void setAvailAmt(java.math.BigDecimal availAmt){
		this.availAmt = availAmt;
	}
	
	/**
	 * 
	 */
	public java.util.Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * 
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	
	/**
	 * 
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}