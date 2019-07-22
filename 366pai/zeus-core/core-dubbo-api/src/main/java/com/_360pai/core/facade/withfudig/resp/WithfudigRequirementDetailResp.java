package com._360pai.core.facade.withfudig.resp;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/7 16:00
 */
@Data
public class WithfudigRequirementDetailResp implements Serializable {
    /**
     *
     */
    private Integer              id;
    /**
     * 需求名称
     */
    private String               requirementName;
    /**
     * 配资需求单号
     */
    private String               requirementNo;
    /**
     * 标的类型 10 20 30
     */
    private String               assetType;
    /**
     * 交易对价
     */
    private String               assetPrice;
    private String               assetPriceShow;
    /**
     * 融资金额 元
     */
    private String               requirementAmount;
    private String               requirementAmountShow;
    /**
     * 融资配比
     */
    private JSONObject           requirementMatchPercent;
    /**
     * 融资利息 开始利息 %
     */
    private java.math.BigDecimal requirementInterestPercentStart;
    /**
     * 融资利息 结束利息 %
     */
    private java.math.BigDecimal requirementInterestPercentEnd;
    /**
     * 融资期限 月
     */
    private java.math.BigDecimal requirementTerm;
    /**
     * 状态 0 未支付 10 等待平台审核 11审核不通过  12审核通过置办中 20已完成 30撮合成功
     */
    private String               requirementStatus;
    /**
     * 融资方企业介绍
     */
    private String               companyDescription;
    /**
     * 项目介绍
     */
    private String               itemDescription;
    /**
     * 还款来源介绍
     */
    private String               repaymentDescription;
    /**
     * 担保措施介绍
     */
    private String               guaranteeDescription;
    /**
     * 处置方介绍
     */
    private String               disposalPartyDescription;
    /**
     * 处置方案介绍
     */
    private String               disposalPlanDescription;
    /**
     * 关联的asset 的 id
     */
    private Integer              assetId;
    /**
     * 标的来源是否平台（0 否 1 是）
     */
    private Boolean              isPlatform;
    /**
     * 描述信息
     */
    private String               description;
    /**
     * 备注
     */
    private String               remark;

    /**
     * 生成时间
     */
    private java.util.Date createdTime;
    /**
     * 更新时间
     */
    private java.util.Date updateTime;

    private Integer followNum;
    private Integer viewNum;
    private String  requirementStatusDesc;
    private Boolean followFlag;
    private Integer investNum;
    private String  specialNotice;

    private List<String> assetImages;

    private boolean investFlag;
}
