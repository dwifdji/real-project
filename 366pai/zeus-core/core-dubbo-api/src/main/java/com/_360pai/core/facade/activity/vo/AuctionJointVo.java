package com._360pai.core.facade.activity.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 机构后台连拍列表
 */
@Getter
@Setter
public class AuctionJointVo implements Serializable {

    private Integer id;
    private String assetName;//拍品名称
    private String categoryType;//资产类型
    private String auctionType;//拍卖类型
    private String cityName;//所在地
    private String startingPrice;//起拍价
    private String previewAt;//预展时间
    private String beginAt;//拍卖开始时间
    private String statusDesc;//状态描述
    private String jointStatusDesc;//连拍状态
    private String jointStatus;//连拍状态
    private String status;
    private String jointId;
    private String cityId;
    private String assetCategoryGroupId;
    private Integer assetId;
    private String partyId;
    private String comeFrom;



}
