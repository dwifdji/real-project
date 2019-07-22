
package com.winback.core.model.applet;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年01月18日 10时01分28秒
 */
@Data
public class TAppletMessage implements java.io.Serializable{

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 账户外部关系绑定表id
	 */
	private Integer extBindId;
	/**
	 * 消息类型 备用字段
	 */
	private String type;
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 参数，json
	 */
	private java.lang.String param;
	/**
	 * 删除标志（0 否 1 是）
	 */
	private Boolean deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;

}
