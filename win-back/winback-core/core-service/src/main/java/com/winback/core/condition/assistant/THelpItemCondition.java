
package com.winback.core.condition.assistant;

import com.winback.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年01月18日 16时48分41秒
 */
public class THelpItemCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 链接url
	 */
	private String link;
	/**
	 * 是否展示（0 否 1 是）
	 */
	private Boolean display;
	/**
	 * 排序号
	 */
	private Integer orderNum;
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
	 * 标题
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * 标题
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * 内容
	 */
	public String getContent(){
		return content;
	}
	
	/**
	 * 内容
	 */
	public void setContent(String content){
		this.content = content;
	}
	
	/**
	 * 链接url
	 */
	public String getLink(){
		return link;
	}
	
	/**
	 * 链接url
	 */
	public void setLink(String link){
		this.link = link;
	}
	
	/**
	 * 是否展示（0 否 1 是）
	 */
	public Boolean getDisplay(){
		return display;
	}
	
	/**
	 * 是否展示（0 否 1 是）
	 */
	public void setDisplay(Boolean display){
		this.display = display;
	}
	
	/**
	 * 排序号
	 */
	public Integer getOrderNum(){
		return orderNum;
	}
	
	/**
	 * 排序号
	 */
	public void setOrderNum(Integer orderNum){
		this.orderNum = orderNum;
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