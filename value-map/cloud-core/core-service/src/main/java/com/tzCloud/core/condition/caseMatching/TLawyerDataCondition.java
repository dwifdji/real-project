
package com.tzCloud.core.condition.caseMatching;

import com.tzCloud.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年03月05日 09时26分19秒
 */
@Data
public class TLawyerDataCondition implements DaoCondition {

	/**
	 *
	 */
	private java.lang.Integer id;
	/**
	 * 文章ID
	 */
	private java.lang.String docid;
	/**
	 * 律师
	 */
	private java.lang.String lawyer;
	/**
	 * 律师事务所
	 */
	private java.lang.String lawFirm;
	/**
	 * 其他描述信息
	 */
	private java.lang.String detail;
	/**
	 * 更新日期
	 */
	private java.util.Date updatedAt;
	/**
	 * 创建日期
	 */
	private java.util.Date createdAt;
	/**
	 * 律师身份（上诉人律师；被上诉人律师）
	 */
	private java.lang.String identity;
	/**
	 * win 胜;lose 负;draw 平
	 */
	private java.lang.String winFlag;
	/**
	 * t_parse_lawyer_info   id
	 */
	private java.lang.Integer lawyerId;
}