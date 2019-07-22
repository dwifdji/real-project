package com._360pai.core.facade.assistant.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: DepositVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/6 11:54
 */
@Getter
@Setter
public class DepositVo implements Serializable {
    private String id;
    /**
     * 拍卖活动名称
     */
    private String activityName;
    /**
     * 活动结束时间
     */
    private Date endAt;
    /**
     * 拍品类型
     */
    private String categoryName;
    /**
     * 竞买人
     */
    private String buyerName;
    /**
     * 竞买人手机号
     */
    private String buyerMobile;
    /**
     * 保证金
     */
    private BigDecimal amount;
    /**
     * 开户银行
     */
    private String bankName;
    /**
     * 银行卡号
     */
    private String bankAccountNumber;
    /**
     * 开户名称
     */
    private String bankAccountName;
    /**
     * 状态
     */
    private String status;
    /**
     * 状态描述
     */
    private String statusDesc;
    /**
     * 提交时间
     */
    private Date createdAt;
    /**
     * 审核备注
     */
    private String remark;
    /**
     * 凭证
     */
    private String voucher;
    /**
     * 操作员
     */
    private String operator;

    private Integer sellerId;
    private String comeFrom;
    /**
     * 支付方式
     */
    private String payType;

    private String categoryId;

}
