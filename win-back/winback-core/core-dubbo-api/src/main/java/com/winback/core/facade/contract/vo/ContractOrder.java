package com.winback.core.facade.contract.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: ContractOrder
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 14:59
 */
@Data
public class ContractOrder implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     * 订单编号
     */
    private java.lang.String orderNo;
    /**
     * 支付的金额
     */
    private java.math.BigDecimal amount;
    /**
     * 支付的状态0 未支付  1 支付成功 2 支付超时，已失效 3 已取消
     */
    private Integer payStatus;
    /**
     * 支付的状态描述
     */
    private String payStatusDesc;
    /**
     * 订单来源
     */
    private String orderSource;
    /**
     * 订单来源描述
     */
    private String orderSourceDesc;
    /**
     * 支付截止时间
     */
    private java.util.Date payDeadline;
    /**
     * 创建时间
     */
    private java.util.Date createTime;

    private List<ContractOrderItem> itemList;
    /**
     * 是否已开发票（0 否 1 是）
     */
    private Boolean invoiceFlag = false;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 支付类型
     */
    private String payType;
    /**
     * 支付类型描述
     */
    private String payTypeDesc;
    /**
     * 支付时间
     */
    private java.util.Date payTime;
}
