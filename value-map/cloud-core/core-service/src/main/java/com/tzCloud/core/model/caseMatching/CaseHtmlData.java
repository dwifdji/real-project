
package com.tzCloud.core.model.caseMatching;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年03月05日 11时07分13秒
 */
@Data
public class CaseHtmlData implements java.io.Serializable{

	/**
	 * 自增ID
	 */
	private Integer id;
	/**
	 * 文书ID
	 */
	private String docId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 公示时间
	 */
	private String pubDate;
	/**
	 * 正文
	 */
	private String html;
	/**
	 * 去除样式的正文
	 */
	private String removeHtml;
	/**
	 * 创建时间
	 */
	private java.util.Date createdAt;
	/**
	 * 修改时间
	 */
	private java.util.Date updatedAt;

	private String spcx;

	private String lawyerDocId;

	private String caseDocId;


}
