
package com.tzCloud.core.model.caseMatching;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年03月07日 16时22分18秒
 */
@Data
public class TTreeContent implements java.io.Serializable{

	/**
	 * 主键自增
	 */
	private Integer id;
	/**
	 * 关键字
	 */
	private String keyWord;
	/**
	 * 父集ID
	 */
	private Integer parentId;
	/**
	 * 字段
	 */
	private String field;


}
