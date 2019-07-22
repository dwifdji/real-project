
package com.winback.core.model.contract;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public class TContractShoppingCartItem implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 购物车id
	 */
	private Integer shoppingCartId;
	/**
	 * 合同id
	 */
	private Integer contractId;
	/**
	 * 数量
	 */
	private Integer num;
	/**
	 * 删除标志（0 否 1 是）
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
	 * 购物车id
	 */
	public Integer getShoppingCartId(){
		return shoppingCartId;
	}
	
	/**
	 * 购物车id
	 */
	public void setShoppingCartId(Integer shoppingCartId){
		this.shoppingCartId = shoppingCartId;
	}
	
	/**
	 * 合同id
	 */
	public Integer getContractId(){
		return contractId;
	}
	
	/**
	 * 合同id
	 */
	public void setContractId(Integer contractId){
		this.contractId = contractId;
	}
	
	/**
	 * 数量
	 */
	public Integer getNum(){
		return num;
	}
	
	/**
	 * 数量
	 */
	public void setNum(Integer num){
		this.num = num;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志（0 否 1 是）
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
