
package com._360pai.core.model.assistant;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2018年09月27日 15时13分45秒
 */
@Data
public class TPlatformArticleType implements java.io.Serializable{

	public static final Integer online = 1;
	public static final Integer down = 0;

	public static final Integer jrtt = 1;
	public static final Integer zcdmb = 2;

	/**
	 * 自增
	 */
	private Integer id;
	/**
	 * 标题
	 */
	private String articleTitle;
	/**
	 * 图片
	 */
	private String image;
	/**
	 * 状态  1:上线  0:下线
	 */
	private Integer status;
	/**
	 * 类别  1:首页今日头条 2:资产大买办
	 */
	private Integer type;
	/**
	 * 0不删除 1删除
	 */
	private Integer delFlag;
	/**
	 * 0 在新闻中心展示 1不在新闻中心
	 */
	private Integer showNews;
	/**
	 * 排序字段
	 */
	private Integer orderNum;
	


}
