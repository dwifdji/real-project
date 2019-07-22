package com.winback.core.facade.contract.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.winback.arch.common.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: InvoiceContractOrderItem
 * @ProjectName winback
 * @Description:
 * @date 2019/2/22 13:53
 */
@Data
public class InvoiceContractOrderDetail implements Serializable {
    /**
     * 订单id
     */
    private Integer orderId;
    /**
     * 订单编号
     */
    private java.lang.String orderNo;
    /**
     * 支付的金额
     */
    private java.math.BigDecimal amount;
    /**
     * 创建时间
     */
    @JSONField(format = DateUtil.NORM_DATE_PATTERN)
    private java.util.Date createTime;
}
