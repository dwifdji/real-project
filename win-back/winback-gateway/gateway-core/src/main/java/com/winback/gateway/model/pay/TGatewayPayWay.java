
package com.winback.gateway.model.pay;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月16日 19时25分32秒
 */
public class TGatewayPayWay implements java.io.Serializable{

	/**
	 * 主键，案件编号
	 */
	private java.lang.Integer id;
	/**
	 * 支付名称
	 */
	private java.lang.String name;
	/**
	 * 支付类型1 支付宝 2 微信
	 */
	private java.lang.String type;
	/**
	 * 支付图标
	 */
	private java.lang.String logoUrl;
	/**
	 * 支付描述
	 */
	private java.lang.String desc;
	/**
	 * 排序
	 */
	private java.lang.Integer orderDesc;
	/**
	 * 推荐标志 0 不推荐 1 推荐
	 */
	private java.lang.Boolean recommFlag;
	/**
	 * 删除标志0 不删除 1删除
	 */
	private java.lang.Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	
	/**
	 * 主键，案件编号
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 主键，案件编号
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 支付名称
	 */
	public java.lang.String getName(){
		return name;
	}
	
	/**
	 * 支付名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	
	/**
	 * 支付类型1 支付宝 2 微信
	 */
	public java.lang.String getType(){
		return type;
	}
	
	/**
	 * 支付类型1 支付宝 2 微信
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	
	/**
	 * 支付图标
	 */
	public java.lang.String getLogoUrl(){
		return logoUrl;
	}
	
	/**
	 * 支付图标
	 */
	public void setLogoUrl(java.lang.String logoUrl){
		this.logoUrl = logoUrl;
	}
	
	/**
	 * 支付描述
	 */
	public java.lang.String getDesc(){
		return desc;
	}
	
	/**
	 * 支付描述
	 */
	public void setDesc(java.lang.String desc){
		this.desc = desc;
	}
	
	/**
	 * 排序
	 */
	public java.lang.Integer getOrderDesc(){
		return orderDesc;
	}
	
	/**
	 * 排序
	 */
	public void setOrderDesc(java.lang.Integer orderDesc){
		this.orderDesc = orderDesc;
	}
	
	/**
	 * 推荐标志 0 不推荐 1 推荐
	 */
	public java.lang.Boolean getRecommFlag(){
		return recommFlag;
	}
	
	/**
	 * 推荐标志 0 不推荐 1 推荐
	 */
	public void setRecommFlag(java.lang.Boolean recommFlag){
		this.recommFlag = recommFlag;
	}
	
	/**
	 * 删除标志0 不删除 1删除
	 */
	public java.lang.Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志0 不删除 1删除
	 */
	public void setDeleteFlag(java.lang.Boolean deleteFlag){
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
