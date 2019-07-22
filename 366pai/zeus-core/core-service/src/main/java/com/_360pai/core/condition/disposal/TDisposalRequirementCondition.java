
package com._360pai.core.condition.disposal;

import com._360pai.arch.core.abs.DaoCondition;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2018年09月14日 10时33分09秒
 */
@Data
public class TDisposalRequirementCondition implements DaoCondition{

	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 处置类型: 10：尽调 11：评估 12：司法处置
13：执行处置 14：清房 15：催收  16：查找财产线索
	 */
	private String disposalType;
	/**
	 * 发布处置需求用户id
	 */
	private Integer accountId;
	private Integer partyId;
	/**
	 * 处置单号
	 */
	private String disposalNo;
	/**
	 * 平台单号
	 */
	private String platformNo;
	/**
	 * 需求名称
	 */
	private String disposalName;
	/**
	 * 案件描述
	 */
	private String caseDescription;
	/**
	 * 需求描述
	 */
	private String requireDescription;
	/**
	 * 处理周期
	 */
	private Double period;
	/**
	 * 处置费用
	 */
	private String cost;
	/**
	 * 1、等待平台审核
2、审核不通过
3、处置中
4、撮合成功
5、已完成
	 */
	private String disposalStatus;
	/**
	 * 商品id
	 */
	private Integer assetId;
	/**
	 * 发布时间
	 */
	private java.util.Date publishTime;
	/**
	 * 平台标识  0：非平台 1：平台
	 */
	private Integer isPlatform;
	/**
	 * 观看人数
	 */
	private Integer viewNum;
	/**
	 * 关注人数
	 */
	private Integer followNum;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	/**
	 * 删除标识  0：未删除 1：删除
	 */
	private Integer isDel;

	/**
	 * 需求id
	 */
	private Integer disposalId;
	/**
	 * 省id
	 */
	private java.lang.String provinceId;
	/**
	 * 城市
	 */
	private String cityId;
	/**
	 * 区县id
	 */
	private java.lang.String areaId;
	/**
	 * 排序字段
	 * cost_asc、period_asc
	 */
	private String orderBy;

	/**
	 * 排序参数1
	 */
	private String orderVar1;
	/**
	 * 排序参数2
	 */
	private String orderVar2;

	/**
	 * 投标人数
	 */
	private Integer biddingNum;

	/**
	 * 审核备注
	 */
	private String verifyContent;

	/**
	 * 招标须知上传操作员id
	 */
	private Integer operatorNoticeId;
	/**
	 * 手动完成操作员id
	 */
	private Integer operatorFinishId;
	/**
	 * 审核操作员id
	 */
	private Integer operatorVerifyId;

	private String biddingNotice;

	private Date deadline;

	private String providerAreas;
	/**
	 * 上拍来源：0 平台 1 机构
	 */
	private java.lang.String comeFrom;
}