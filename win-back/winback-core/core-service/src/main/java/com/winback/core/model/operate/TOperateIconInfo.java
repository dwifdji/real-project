
package com.winback.core.model.operate;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月22日 10时27分34秒
 */
public class TOperateIconInfo implements java.io.Serializable{

	/**
	 * 主键
	 */
	private java.lang.Integer id;
	/**
	 * 大类类型 1 快速发布 2 优质案件 3 合同范本 4 导航栏配置
	 */
	private java.lang.Integer groupType;
	/**
	 * 类型
	 */
	private java.lang.Integer type;
	/**
	 * 图片链接
	 */
	private java.lang.String imgUrl;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * 排序
	 */
	private java.lang.Integer sort;
	/**
	 * 是否有效标志 0 无效 1 有效
	 */
	private java.lang.Boolean onlineFlag;
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
	 * 大类类型 1 快速发布 2 优质案件 3 合同范本 4 导航栏配置
	 */
	public java.lang.Integer getGroupType(){
		return groupType;
	}
	
	/**
	 * 大类类型 1 快速发布 2 优质案件 3 合同范本 4 导航栏配置
	 */
	public void setGroupType(java.lang.Integer groupType){
		this.groupType = groupType;
	}
	
	/**
	 * 类型
	 */
	public java.lang.Integer getType(){
		return type;
	}
	
	/**
	 * 类型
	 */
	public void setType(java.lang.Integer type){
		this.type = type;
	}
	
	/**
	 * 图片链接
	 */
	public java.lang.String getImgUrl(){
		return imgUrl;
	}
	
	/**
	 * 图片链接
	 */
	public void setImgUrl(java.lang.String imgUrl){
		this.imgUrl = imgUrl;
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
	
	/**
	 * 排序
	 */
	public java.lang.Integer getSort(){
		return sort;
	}
	
	/**
	 * 排序
	 */
	public void setSort(java.lang.Integer sort){
		this.sort = sort;
	}
	
	/**
	 * 是否有效标志 0 无效 1 有效
	 */
	public java.lang.Boolean getOnlineFlag(){
		return onlineFlag;
	}
	
	/**
	 * 是否有效标志 0 无效 1 有效
	 */
	public void setOnlineFlag(java.lang.Boolean onlineFlag){
		this.onlineFlag = onlineFlag;
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
