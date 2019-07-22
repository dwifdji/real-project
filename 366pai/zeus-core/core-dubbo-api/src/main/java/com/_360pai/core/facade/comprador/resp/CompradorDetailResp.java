package com._360pai.core.facade.comprador.resp;


import lombok.*;

import java.io.Serializable;

/**
 * 描述 CompradorDetailResp
 *
 * @author : whisky_vip
 * @date : 2018/8/30 11:07
 */
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CompradorDetailResp implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 需求名称
     */
    private String               requirementName;
    /**
     * 需求单号
     */
    private String               requirementNo;
    /**
     * 建筑类型
     */
    private String               buildingType;
    /**
     * 佣金 %
     */
    private java.math.BigDecimal commissionPercent;
    /**
     * 状态 10 等待平台审核 20置办中 30已完成 撮合成功
     */
    private String               requirementStatus;
    /**
     * 价格区间 开始价格
     */
    private String               startPrice;
    private String               startPriceShow;
    /**
     * 价格区间 结束价格
     */
    private String               endPrice;
    private String               endPriceShow;
    /**
     * 面积区间 开始面积
     */
    private String               startArea;
    private String               startAreaShow;
    /**
     * 面积区间 结束面积
     */
    private String               endArea;
    private String               endAreaShow;
    /**
     * 区域
     */
    private Integer              cityId;
    /**
     * 交易方式 10 债权 20 物权 30 股权
     */
    private String               transactionMode;
    /**
     * 拟收购项目标准
     */
    private String               proposedAcquisition;
    /**
     * 其他描述
     */
    private String               description;
    /**
     * 生成时间
     */
    private java.util.Date       createdTime;

    private String  remark;
    private String  requirementStatusDesc;
    private Integer recommenderNum;
    private Integer viewNum;
    private Integer followNum;
    private boolean recommendFlag;

}
