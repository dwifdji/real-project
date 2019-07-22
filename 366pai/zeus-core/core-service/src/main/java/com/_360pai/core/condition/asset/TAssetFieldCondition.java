
package com._360pai.core.condition.asset;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月11日 16时04分21秒
 */
@Getter
@Setter
public class TAssetFieldCondition implements DaoCondition{

	/**
	 * 自增id
	 */
	private Integer id;
	/**
	 * 字段名
	 */
	private String name;
	/**
	 * 字段中文名称
	 */
	private String label;
	/**
	 * 提示内容
	 */
	private String hint;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 类型 TEXT:文本类型，FILE:文件类型，TEXTAREA:文本域，SEDATE:起止时间，DATE:时间
	 */
	private String type;
	/**
	 * 字段分组ID
	 */
	private Integer fieldGroupId;
	/**
	 * 是否在前端显示
	 */
	private Boolean frontShow;
	/**
	 * 排序编号
	 */
	private Integer orderNum;
	/**
	 * json数据（select类型的）
	 */
	private com.alibaba.fastjson.JSONObject jsonData;

	/**
	 * 是否格式化数字 0不格式化  1格式化
	 */
	private Integer fmNum;
	


}