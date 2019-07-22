
package com.winback.core.model.assistant;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月18日 10时01分28秒
 */
@Data
public class TAppMessage implements java.io.Serializable{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 账户id
	 */
	private Integer accountId;
	/**
	 * 消息类型
	 */
	private String type;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;

	/**
	 * 参数信息
	 */
	private String param;

}
