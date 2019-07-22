
package com.tzCloud.core.condition.legalEngine;

import com.tzCloud.arch.core.abs.DaoCondition;
import lombok.Data;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年04月19日 15时41分11秒
 */
@Data
public class TCaseCondition implements DaoCondition{

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 文书ID
	 */
	private String docId;
	/**
	 * 案件类型（1 刑事案件 2 民事案件 3 行政案件 4 赔偿案件 5 执行案件），对应cpwsw_item表ajlx
	 */
	private String type;
	/**
	 * 文书性质（1 判决书 2 裁定书 3 调解书 4 决定书 5 通知书 6 批复 7 答复 8 涵 9 令 10 其他）
	 */
	private String judgementType;
	/**
	 * 裁判日期，对应cpwsw_item表cprq
	 */
	private java.util.Date judgementDate;
	/**
	 * 案件名称，对应cpwsw_item表ajmc
	 */
	private String title;
	/**
	 * 裁判要旨段原文，对应cpwsw_item表cpyzdyw
	 */
	private String courtOpinion;
	/**
	 * 审判程序，对应cpwsw_item表spcx
	 */
	private String trialRound;
	/**
	 * 案号，对应cpwsw_item表ah
	 */
	private String caseNumber;
	/**
	 * 法院名称，对应cpwsw_item表fymc
	 */
	private String courtName;
	/**
	 * 案由，对应cpwsw_item表brief,关联查询t_tree_content的id，field包含案由
	 */
	private Integer briefId;
	/**
	 * 删除标志
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
}