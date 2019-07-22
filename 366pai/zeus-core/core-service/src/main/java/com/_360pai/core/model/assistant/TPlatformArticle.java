
package com._360pai.core.model.assistant;

import java.util.Date;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月27日 15时13分45秒
 */
public class TPlatformArticle implements java.io.Serializable{
	public static final Integer online = 1;
	public static final Integer down = 0;
	/**
	 * 自增
	 */
	private Integer id;
	/**
	 * 文章类型ID
	 */
	private Integer articleTypeId;
	/**
	 * 文章类型ID
	 */
	private String articleTypeName;
	/**
	 * 文章名称
	 */
	private String articleName;
	/**
	 * 文章内容
	 */
	private String detail;
	/**
	 * 状态 0:下线 1:上线
	 */
	private Integer status;
	/**
	 * 排序编号
	 */
	private Integer orderNum;
	/**
	 * 0不删除  1删除
	 */
	private Integer delFlag;
	/**
	 * 浏览量
	 */
	private java.lang.Integer viewCount;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 发布时间
	 */
	private java.util.Date publicAt;
	
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
	 * 文章类型ID
	 */
	public Integer getArticleTypeId(){
		return articleTypeId;
	}
	
	/**
	 * 文章类型ID
	 */
	public void setArticleTypeId(Integer articleTypeId){
		this.articleTypeId = articleTypeId;
	}
	
	/**
	 * 文章名称
	 */
	public String getArticleName(){
		return articleName;
	}
	
	/**
	 * 文章名称
	 */
	public void setArticleName(String articleName){
		this.articleName = articleName;
	}
	
	/**
	 * 文章内容
	 */
	public String getDetail(){
		return detail;
	}
	
	/**
	 * 文章内容
	 */
	public void setDetail(String detail){
		this.detail = detail;
	}
	
	/**
	 * 状态 0:下线 1:上线
	 */
	public Integer getStatus(){
		return status;
	}
	
	/**
	 * 状态 0:下线 1:上线
	 */
	public void setStatus(Integer status){
		this.status = status;
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

    public String getArticleTypeName() {
        return articleTypeName;
    }

    public void setArticleTypeName(String articleTypeName) {
        this.articleTypeName = articleTypeName;
    }

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPublicAt() {
		return publicAt;
	}

	public void setPublicAt(Date publicAt) {
		this.publicAt = publicAt;
	}
}
