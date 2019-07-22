
package com.winback.core.condition.operate;


import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月22日 10时27分34秒
 */
public class TOperateBannerCondition implements DaoCondition {

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 类型 1 启动页 2 首页
	 */
	private java.lang.String type;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * 描述信息
	 */
	private java.lang.String nameDesc;
	/**
	 * 头像
	 */
	private java.lang.String imgUrl;
	/**
	 * 跳转url
	 */
	private java.lang.String jumpUrl;

	/**
	 * 固定跳转type
	 */
	private java.lang.String fixedJumpType;

	/**
	 * 有效开始时间
	 */
	private java.util.Date beginTime;
	/**
	 * 有效结束时间
	 */
	private java.util.Date endTime;
	/**
	 * 上线标志 0 没上线 1 上线
	 */
	private java.lang.Boolean onlineFlage;
	/**
	 * 删除标志（0 否 1 是）
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
	 * 广告位排序
	 */
	private Integer sort;

	public String getFixedJumpType() {
		return fixedJumpType;
	}

	public void setFixedJumpType(String fixedJumpType) {
		this.fixedJumpType = fixedJumpType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * 主键
	 */
	public java.lang.Integer getId(){
		return id;
	}
	
	/**
	 * 主键
	 */
	public void setId(java.lang.Integer id){
		this.id = id;
	}
	
	/**
	 * 类型 1 启动页 2 首页
	 */
	public java.lang.String getType(){
		return type;
	}
	
	/**
	 * 类型 1 启动页 2 首页
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	
	/**
	 * 名称
	 */
	public java.lang.String getName(){
		return name;
	}
	
	/**
	 * 名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}

	public String getNameDesc() {
		return nameDesc;
	}

	public void setNameDesc(String nameDesc) {
		this.nameDesc = nameDesc;
	}

	/**
	 * 头像
	 */
	public java.lang.String getImgUrl(){
		return imgUrl;
	}
	
	/**
	 * 头像
	 */
	public void setImgUrl(java.lang.String imgUrl){
		this.imgUrl = imgUrl;
	}
	
	/**
	 * 跳转url
	 */
	public java.lang.String getJumpUrl(){
		return jumpUrl;
	}
	
	/**
	 * 跳转url
	 */
	public void setJumpUrl(java.lang.String jumpUrl){
		this.jumpUrl = jumpUrl;
	}
	
	/**
	 * 有效开始时间
	 */
	public java.util.Date getBeginTime(){
		return beginTime;
	}
	
	/**
	 * 有效开始时间
	 */
	public void setBeginTime(java.util.Date beginTime){
		this.beginTime = beginTime;
	}
	
	/**
	 * 有效结束时间
	 */
	public java.util.Date getEndTime(){
		return endTime;
	}
	
	/**
	 * 有效结束时间
	 */
	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}
	
	/**
	 * 上线标志 0 没上线 1 上线
	 */
	public java.lang.Boolean getOnlineFlage(){
		return onlineFlage;
	}
	
	/**
	 * 上线标志 0 没上线 1 上线
	 */
	public void setOnlineFlage(java.lang.Boolean onlineFlage){
		this.onlineFlage = onlineFlage;
	}
	
	/**
	 * 删除标志（0 否 1 是）
	 */
	public java.lang.Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志（0 否 1 是）
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