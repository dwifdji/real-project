
package com.tzCloud.core.model.caseMatching;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年03月12日 11时53分17秒
 */
@Data
public class TLawyerInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private Long id;
	/**
	 * 律所id
	 */
	private String lsswsbs;
	/**
	 * id
	 */
	private String lsbs;
	/**
	 * 头像地址
	 */
	private String imgUrl;
	/**
	 * 姓名
	 */
	private String xm;
	/**
	 * 工作年限
	 */
	private Integer years;
	/**
	 * 律所名称
	 */
	private String lsswsmc;
	/**
	 * 专长
	 */
	private String ywzc;
	/**
	 * 
	 */
	private java.util.Date createTime;

	private Long lawFirmId;


}
