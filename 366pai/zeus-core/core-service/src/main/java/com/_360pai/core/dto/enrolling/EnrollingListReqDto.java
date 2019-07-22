package com._360pai.core.dto.enrolling;

import lombok.Data;

import java.util.List;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 14:02
 */
@Data
public class EnrollingListReqDto extends PageReqDto{

    private String id;//预招商id


    private String status;//预招商活动专题

    private String info;//活动信息

    private String createdAtFrom;//送拍开始时间

    private String createdAtTo;//送拍结束时间

    private String agencyName;//送拍机构
    /**
     * 省id
     */
    private Integer provinceId;
    /**
     * 城市id
     */
    private Integer cityId;
    /**
     * 区县id
     */
    private Integer areaId;

    private String partyType;//委托人类型

    private String partyName;//委托人名称

    private String unionStatus;//是否是推广状态

    private String agencyId;//机构id

    private String type;//预招商类型

    private String partyId;//partyId

    private String todayFlag;
    private String orderBy;

    private Integer thirdPartyStatus;

    private Integer noThirdPartyStatus;

    private String orderVar1;

    private String orderVar2;

    private String visibilityLevel;

    private String accountId;

    private Integer hallId; // 招商大厅id

    private Integer focusType;


    private Integer source;

    private Integer excludeAppletHallActivity;


    private String focusInfo;

    private String branchCom;


    private String category;

    private String mortgage;

    private String busTypeName;
    /**
     * 起始价格
     */
    private String beginPrice;

    /**
     * 结束价格
     */
    private String endPrice;

    private List<String> busTypeList;
    private List<String> typeList;
    private List<String> statusList;
    private List<Integer> activityIdList;
    private Integer activityPosterProvinceId;
    private Integer activityPosterCityId;
    private Integer activityPosterAreaId;
    private Integer excludeAssetPropertyActivity;
    private Integer excludeAlbumActivity;
    private Integer frontFlag;
}
