
package com._360pai.core.condition.activity;

import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月28日 14时05分16秒
 */
public class TOfflineEnterAccountCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * t_auction_offline_finance id
	 */
	private Integer financeId;
	/**
	 * 入账金额
	 */
	private String amount;
	/**
	 * 银行流水单号
	 */
	private String bankOrderNo;
	/**
	 * 公司银行账户id
	 */
	private Integer bankAccountId;
	/**
	 * 入账日期
	 */
	private String enterDate;
	/**
	 * 
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	
	/**
	 * 主键
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * 主键
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * t_auction_offline_finance id
	 */
	public Integer getFinanceId(){
		return financeId;
	}
	
	/**
	 * t_auction_offline_finance id
	 */
	public void setFinanceId(Integer financeId){
		this.financeId = financeId;
	}
	
	/**
	 * 入账金额
	 */
	public String getAmount(){
		return amount;
	}
	
	/**
	 * 入账金额
	 */
	public void setAmount(String amount){
		this.amount = amount;
	}
	
	/**
	 * 银行流水单号
	 */
	public String getBankOrderNo(){
		return bankOrderNo;
	}
	
	/**
	 * 银行流水单号
	 */
	public void setBankOrderNo(String bankOrderNo){
		this.bankOrderNo = bankOrderNo;
	}
	
	/**
	 * 公司银行账户id
	 */
	public Integer getBankAccountId(){
		return bankAccountId;
	}
	
	/**
	 * 公司银行账户id
	 */
	public void setBankAccountId(Integer bankAccountId){
		this.bankAccountId = bankAccountId;
	}
	
	/**
	 * 入账日期
	 */
	public String getEnterDate(){
		return enterDate;
	}
	
	/**
	 * 入账日期
	 */
	public void setEnterDate(String enterDate){
		this.enterDate = enterDate;
	}
	
	/**
	 * 
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 
	 */
	public void setDeleteFlag(Boolean deleteFlag){
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