package com._360pai.core.facade.asset.vo;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.facade.account.vo.AgencyVo;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xdrodger
 * @Title: AssetVo
 * @ProjectName zeus-parent
 * @Description:
 * @date 04/09/2018 14:53
 */
@Getter
@Setter
public class AssetVo implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String status;
    /**
     *
     */
    private String statusDesc;
    /**
     *
     */
    private Date createdAt;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private Integer cityId;

    /**
     *
     */
    private Integer quantity;
    /**
     *
     */
    private Integer remain;
    /**
     *
     */
    private String assetType;
    /**
     *
     */
    private String remark;
    /**
     *
     */
    private AgencyVo agency;
    /**
     *
     */
    private String subStatus;
    /**
     *
     */
    private String descriptionDoc;
    /**
     *
     */
    private String detail;
    /**
     *
     */
    private String code;
    /**
     *
     */
    private String expectedMode;
    /**
     *
     */
    private String expectedModeDesc;
    /**
     *
     */
    private Integer propertyId;

    private String propertyName;
    /**
     *
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date beginAt;
    /**
     *
     */
    private java.util.Date endAt;
    /**
     *
     */
    private java.math.BigDecimal refPrice;
    /**
     *
     */
    private java.math.BigDecimal startingPrice;
    /**
     *
     */
    private Integer categoryId;

    private String categoryName;
    /**
     *
     */
    private String options;
    /**
     *
     */
    private Integer handoverDays;
    /**
     *
     */
    private Integer payDays;
    /**
     *
     */
    private String specialDetail;

    /**
     * 包含图片的JSON数据
     */
    private com.alibaba.fastjson.JSONObject extra;

    private PartyAccount contactPerson;

    private PartyAccount seller;

    private List<AssetDataVo> assetDatas;

    private Integer activityId;

    /**
     * 债权本金
     */
    private java.math.BigDecimal debtPrincipal;
    /**
     * 债权利息
     */
    private java.math.BigDecimal debtInterest;
    /**
     * 城市名称
     */
    private String cityName;

    private Integer partyId;

    private Integer busType;
    /**
     * 线上 线下操作 0:线下操作 1:线上操作
     */
    private java.lang.Integer onlined;
}
