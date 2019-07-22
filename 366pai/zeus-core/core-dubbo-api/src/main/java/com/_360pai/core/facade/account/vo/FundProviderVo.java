package com._360pai.core.facade.account.vo;

import com._360pai.core.facade.assistant.vo.CityVo;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xdrodger
 * @Title: FundProviderVo
 * @ProjectName zeus
 * @Description:
 * @date 18/09/2018 18:53
 */
@Getter
@Setter
public class FundProviderVo implements Serializable {
    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     *
     */
    private java.lang.Integer accountId;
    /**
     * 注册账号
     */
    private java.lang.String mobile;
    /**
     * 公司名称
     */
    private java.lang.String companyName;
    /**
     * 公司类型
     */
    private java.lang.String companyType;
    private String companyTypeDesc;
    /**
     * 注册地址
     */
    private java.lang.String registerAddress;
    /**
     * 注册资本
     */
    private java.math.BigDecimal registerCapital;
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
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 更新时间
     */
    private java.util.Date updateTime;

    private java.lang.String fundType;
    /**
     * 开户人员
     */
    private java.lang.Integer openAccountOperatorId;
    /**
     * 业务对接人
     */
    private java.lang.Integer businessOperatorId;
    /**
     * 开户人员
     */
    private java.lang.String openAccountOperator;
    /**
     * 业务对接人
     */
    private java.lang.String businessOperator;
}
