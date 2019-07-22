package com._360pai.core.facade.fastway.vo;

import com._360pai.core.facade.assistant.vo.CityVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaolei
 * @create 2018-12-06 16:34
 */
@Data
public class FundBasisVO implements Serializable {

    private Integer partyId;

    /**
     * 用户类型
     */
    private java.lang.String fundType;

    private static final long serialVersionUID = 1606802041162452441L;
    /**
     * 配资区域
     */
    private List<CityVo> providerAreas;
    /**
     * 最小配资额度
     */
    private java.math.BigDecimal providerMinAmount;
    /**
     * 最大配资额度
     */
    private java.math.BigDecimal providerMaxAmount;
    /**
     * 配资级别
     */
    private com.alibaba.fastjson.JSONObject providerLevel;
    /**
     * 最小配资成本
     */
    private java.math.BigDecimal providerMinCost;
    /**
     * 最大配资成本
     */
    private java.math.BigDecimal providerMaxCost;
    /**
     * 服务费用
     */
    private java.math.BigDecimal providerFee;
    /**
     * 配资最短期限
     */
    private java.math.BigDecimal providerMinMonth;
    /**
     * 配资最长期限
     */
    private java.math.BigDecimal providerMaxMonth;
    /**
     * 配资模式
     */
    private java.lang.String providerPattern;
    /**
     * 资产包要求
     */
    private java.lang.String assetRequire;
    /**
     * 描述
     */
    private java.lang.String descrip;
    /**
     * 配资比例
     */
    private java.math.BigDecimal scale;
    /**
     * 联系方式（电话）
     */
    private java.lang.String contactPhone;
    /**
     * 联系人
     */
    private java.lang.String contactPerson;
    /**
     * 单笔配资额度开始
     */
    private java.math.BigDecimal singleMinAmount;
    /**
     * 单笔配资额度结束
     */
    private java.math.BigDecimal singleMaxAmount;
    /**
     * 年化收益率开始
     */
    private java.math.BigDecimal annuaReturnMin;
    /**
     * 年化收益率结束
     */
    private java.math.BigDecimal annuaReturnMax;
    /**
     * 其他费用
     */
    private java.lang.String otherFee;
    /**
     * 标的类型
     */
    private java.lang.String assetType;

    private String reason;

}
