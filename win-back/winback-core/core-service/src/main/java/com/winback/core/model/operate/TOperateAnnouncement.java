
package com.winback.core.model.operate;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月25日 17时01分48秒
 */
public class TOperateAnnouncement implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 类型1app公告
	 */
	private String type;
	/**
	 * 标题
	 */
	private String name;
	/**
	 * 描述
	 */
	private String nameDesc;
	/**
	 * 删除标识
	 */
	private Boolean deleteFlag;
	/**
	 * 开始时间
	 */
	private java.util.Date beginTime;
	/**
	 * 结束时间
	 */
	private java.util.Date endTime;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 结束时间
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
	 * 类型1app公告
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * 类型1app公告
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 标题
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 标题
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 描述
	 */
	public String getNameDesc(){
		return nameDesc;
	}
	
	/**
	 * 描述
	 */
	public void setNameDesc(String nameDesc){
		this.nameDesc = nameDesc;
	}
	
	/**
	 * 删除标识
	 */
	public Boolean getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标识
	 */
	public void setDeleteFlag(Boolean deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	
	/**
	 * 开始时间
	 */
	public java.util.Date getBeginTime(){
		return beginTime;
	}
	
	/**
	 * 开始时间
	 */
	public void setBeginTime(java.util.Date beginTime){
		this.beginTime = beginTime;
	}
	
	/**
	 * 结束时间
	 */
	public java.util.Date getEndTime(){
		return endTime;
	}
	
	/**
	 * 结束时间
	 */
	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
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
	 * 结束时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 结束时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}
