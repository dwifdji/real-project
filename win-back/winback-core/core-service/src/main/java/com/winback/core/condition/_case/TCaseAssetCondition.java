
package com.winback.core.condition._case;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年02月28日 16时03分22秒
 */
public class TCaseAssetCondition implements DaoCondition{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer accountId;
	/**
	 * 
	 */
	private java.math.BigDecimal assetAmount;
	/**
	 * 
	 */
	private String assetDesc;
	/**
	 * 
	 */
	private Boolean deleteFlag;
	/**
	 * 
	 */
	private java.util.Date createTime;
	/**
	 * 
	 */
	private java.util.Date updateTime;

	private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

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
	 * 
	 */
	public Integer getAccountId(){
		return accountId;
	}
	
	/**
	 * 
	 */
	public void setAccountId(Integer accountId){
		this.accountId = accountId;
	}
	
	/**
	 * 
	 */
	public java.math.BigDecimal getAssetAmount(){
		return assetAmount;
	}
	
	/**
	 * 
	 */
	public void setAssetAmount(java.math.BigDecimal assetAmount){
		this.assetAmount = assetAmount;
	}
	
	/**
	 * 
	 */
	public String getAssetDesc(){
		return assetDesc;
	}
	
	/**
	 * 
	 */
	public void setAssetDesc(String assetDesc){
		this.assetDesc = assetDesc;
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