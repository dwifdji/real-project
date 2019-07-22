package com.winback.core.facade.contract.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: Contract
 * @ProjectName winback
 * @Description:
 * @date 2019/1/31 14:43
 */
@Data
public class Contract implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 金额
     */
    private java.math.BigDecimal amount;
    /**
     * 第一张图片
     */
    private String firstImage;
    /**
     * 图片，逗号分隔
     */
    private String images;
    /**
     * 使用说明
     */
    private String manual;
    /**
     * 提示
     */
    private String hint;
    /**
     * 篇幅
     */
    private Integer length;
    /**
     * 下载量
     */
    private Integer downloadCount;
    /**
     * 收藏量
     */
    private Integer favoriteCount;
    /**
     * 购买量
     */
    private Integer purchaseCount;
    /**
     * 推荐标志（0 否 1 是）
     */
    private Boolean recommendFlag = false;
    /**
     * 是否上架（0 否 1 是）
     */
    private java.lang.Boolean saleFlag = false;
    /**
     * 创建时间
     */
    private java.util.Date createTime;

    /**
     * 是否在购物车内
     */
    private java.lang.Boolean shoppingCartFlag = false;
    /**
     * 是否已收藏
     */
    private java.lang.Boolean favoriteFlag = false;
    /**
     * 是否已购买
     */
    private java.lang.Boolean purchasedFlag = false;
    /**
     * 合同类型
     */
    private String contractType;
    /**
     * 合同类型id
     */
    private Integer contractTypeId;
    /**
     * 合同大类类型id
     */
    private Integer contractBigTypeId;
}
