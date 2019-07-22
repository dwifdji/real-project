package com._360pai.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author RuQ
 * @Title: AuctionOfflineFinaceDto
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/4/28 15:27
 */
@Getter
@Setter
public class AuctionOfflineFinaceDto implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * auction_order id
     */
    private String orderId;
    /**
     * 尾款+佣金/佣金
     */
    private String financeType;
    /**
     * 拍品名称
     */
    private String auctionName;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户手机
     */
    private String userMobile;
    /**
     * 角色类型
     */
    private String roleType;
    /**
     * 应收金额合计
     */
    private String shouldReceiveTotalAmount;
    /**
     * 实收金额合计
     */
    private String actualReceiveTotalAmount;
    /**
     * 尾款金额
     */
    private String remainAmount;
    /**
     * 我方收取/三方收取
     */
    private String receiveRemainType;
    /**
     * 尾款收取描述
     */
    private String receiveRemainRemark;
    /**
     * 应收佣金
     */
    private String shouldReceiveCommissionAmount;
    /**
     * 实收佣金
     */
    private String actualReceiveCommissionAmount;
    /**
     * 一致/线下协商
     */
    private String receiveCommissionType;
    /**
     * 已确认/未确认
     */
    private String staus;
    /**
     * 操作人id
     */
    private Integer operatorId;
    /**
     *
     */
    private Boolean deleteFlag;
    /**
     * 创建时间
     */
    private String createTime;

    private String operatorName;

    private List<OfflineEnterAccountDto> enterAccountList;

    private List<CommissionDto>  commissionList;
}
