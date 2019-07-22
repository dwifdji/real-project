
package com.tzCloud.core.model.lawyerSearch;

import com.tzCloud.core.facade.legalEngine.resp.DocVO;
import lombok.Data;

import java.util.List;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年04月19日 09时15分09秒
 */
@Data
public class TParseLawyerInfo implements java.io.Serializable{

	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 律师名称
	 */
	private String lawyerName;
	/**
	 * 律师事务所
	 */
	private String lawFirm;
	/**
	 * 事务所名称
	 */
	private String lawFirmShort;
	/**
	 * 事务所所在城市
	 */
	private String lawFirmCity;
	/**
	 * 事务所所在省份
	 */
	private String lawFirmProvince;
	/**
	 * 工作年限
	 */
	private Integer years;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	/**
	 * 删除标识
	 */
	private Boolean delFlag;
	private String brief;
	private Integer caseCount;
	private List<DocVO> caseBrief;
	private String lawyerFirmId;
	private String ids;
}
