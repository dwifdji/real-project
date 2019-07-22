package com._360pai.core.facade.payment.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: ShopAuctionOrderVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/4 11:10
 */
@Data
public class ShopAuctionOrderVo implements Serializable {
    /**
     * 订单编号
     */
    private String orderId;
    /**
     * 渠道
     */
    private String onlined;
    /**
     * 成交人
     */
    private String buyerName;
    /**
     * 注册手机号
     */
    private String buyerMobile;
    /**
     * 所属店铺推荐码
     */
    private String belongShopCode;
    /**
     * 所属店铺分润金额
     */
    private BigDecimal belongShopCommission = BigDecimal.ZERO;
    /**
     * 上级店铺推荐码
     */
    private String parentShopCode;
    /**
     * 上级店铺分润金额
     */
    private BigDecimal parentShopCommission = BigDecimal.ZERO;
    /**
     * 上级机构推荐码
     */
    private String parentAgencyCode;
    /**
     * 上级机构分润金额
     */
    private BigDecimal parentAgencyCommission = BigDecimal.ZERO;
    /**
     * 上级店铺推荐码
     */
    @JSONField(serialize = false)
    private String parentCode;
    /**
     * 上级店铺分润金额
     */
    @JSONField(serialize = false)
    private BigDecimal parentCommission = BigDecimal.ZERO;

    /**
     * 送拍分润金额
     */
    private BigDecimal sellerAgencyCommission = BigDecimal.ZERO;
    /**
     * 联拍分润金额
     */
    @JSONField(serialize = false)
    private BigDecimal buyerAgencyCommission = BigDecimal.ZERO;
    /**
     * 平台分润金额
     */
    private BigDecimal platformCommission = BigDecimal.ZERO;

    /**
     * 成交时间
     */
    private Date createTime;
    /**
     *
     */
    @JSONField(serialize = false)
    private java.lang.Integer belongAcctId;
    /**
     *
     */
    @JSONField(serialize = false)
    private java.lang.Integer parentAcctId;
}
