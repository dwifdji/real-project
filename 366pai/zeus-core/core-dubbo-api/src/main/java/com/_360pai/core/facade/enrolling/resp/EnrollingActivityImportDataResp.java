
package com._360pai.core.facade.enrolling.resp;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 * @author Generator
 * @date 2019年02月14日 13时27分22秒
 */
@Data
public class EnrollingActivityImportDataResp implements java.io.Serializable{

	/**
	 *
	 */
	private Integer id;
	/**
	 *
	 */
	private Integer activityId;
	/**
	 * 招商会名称
	 */
	private String name;
	/**
	 * 分公司名称
	 */
	private String branchComName;
	/**
	 * 债务人
	 */
	private String debtor;
	/**
	 * 债务人行业
	 */
	private String debtorBus;
	/**
	 * 经营状况
	 */
	private String busStates;
	/**
	 * 省份
	 */
	private String debtorPro;
	/**
	 * 城市
	 */
	private String debtorCity;


	private String debtorProId;
	/**
	 * 城市
	 */
	private String debtorCityId;
	/**
	 * 城市
	 */
	private String debtorAreaId;

	/**
	 * 地区
	 */
	private String debtorArea;
	/**
	 * 所在地
	 */
	private String debtLocation;
	/**
	 * 抵押物所在地
	 */
	private String pawnLocation;
	/**
	 * 省份
	 */
	private String pawnPro;
	/**
	 * 城市
	 */
	private String pawnCity;


	private String pawnProId;
	 

	private String pawnCityId;

	private String pawnAreaId;

	/**
	 * 地区
	 */
	private String pawnArea;
	/**
	 * 基准日
	 */
	private String baseDate;
	/**
	 * 未偿本金
	 */
	private String outstandingPrincipal;
	/**
	 * 未偿利息
	 */
	private String outstandingInterest;
	/**
	 * 违约金
	 */
	private String dedit;
	/**
	 * 其他
	 */
	private String otherInfo;
	/**
	 * 债权总额
	 */
	private String totalDebt;
	/**
	 * 资产包户数
	 */
	private String assetNum;
	/**
	 * 资产包来源
	 */
	private String assetSource;
	/**
	 * 评估值
	 */
	private String assetValue;
	/**
	 * 评估基准日
	 */
	private String assetBaseDate;
	/**
	 * 评估报告出具日
	 */
	private String reportDate;
	/**
	 * 担保方式
	 */
	private String assureMeans;
	/**
	 * 保证人名称
	 */
	private String assureName;
	/**
	 * 具体担保措施
	 */
	private String specificAssureMeans;
	/**
	 * 房地产性质
	 */
	private String realtyCharacter;
	/**
	 * 土地面积
	 */
	private String landArea;
	/**
	 * 房屋建筑面积
	 */
	private String buildingArea;
	/**
	 * 抵质押顺位
	 */
	private String pledgeSequence;
	/**
	 * 其他担保方式情况说明
	 */
	private String guaranteeThat;
	/**
	 * 所处诉讼环节
	 */
	private String litigationLink;
	/**
	 * 是否采取司法保全
	 */
	private String hasGuarantee;
	/**
	 * 如已采取司法保全
	 */
	private String ifGuarantee;
	/**
	 * 项目亮点
	 */
	private String projectWindow;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 资产描述
	 */
	private String assetDesc;
	/**
	 * 项目联系人
	 */
	private String contactPerson;
	/**
	 * 联系电话
	 */
	private String contactPhone;
	/**
	 * 处置服务商
	 */
	private String disposalService;
	/**
	 * 资金服务商
	 */
	private String fundProvider;

	private Integer operatorId;

	private String disposalPhone;

	private String fundPhone;






}
