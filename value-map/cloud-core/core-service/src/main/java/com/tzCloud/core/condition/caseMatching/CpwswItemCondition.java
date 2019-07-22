
package com.tzCloud.core.condition.caseMatching;

import com.tzCloud.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年03月07日 16时22分18秒
 */
@Data
public class CpwswItemCondition implements DaoCondition{

	/**
	 * 自增ID
	 */
	private Long id;
	/**
	 * 文书ID
	 */
	private String docId;
	/**
	 * 裁判要旨段原文
	 */
	private String cpyzdyw;
	/**
	 * 不公开理由
	 */
	private String bgkly;
	/**
	 * 案件类型
	 */
	private String ajlx;
	/**
	 * 裁判日期
	 */
	private java.util.Date cprq;
	/**
	 * 案件名称
	 */
	private String ajmc;
	/**
	 * 审判程序
	 */
	private String spcx;
	/**
	 * 案号
	 */
	private String ah;
	/**
	 * 法院名称
	 */
	private String fymc;
	/**
	 * 请求参数ID
	 */
	private Integer requestId;
	/**
	 * 请求法院ID
	 */
	private Integer courtId;
	/**
	 * 是否解析过 0未解析: 1解析
	 */
	private Integer parseStatus;

	/**
	 * 案由
	 */
	private String brief;
	/**
	 * 创建时间
	 */
	private java.util.Date createdAt;


	/**
	 * 事务所名称
	 */
	private String lawFirm;

	private String lawyer;
}