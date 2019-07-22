
package com._360pai.core.model.order;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年09月06日 18时43分20秒
 */
@Data
public class TServiceOrder implements java.io.Serializable {

    /**
     * 主键id
     */
    private java.lang.Integer    id;
    /**
     * 支付订单号
     */
    private java.lang.String     orderNum;
    /**
     * 订单类型 10 资产大买办 20 配资乐 30 处置服务
     */
    private java.lang.Integer    orderType;
    /**
     * 用户id
     */
    private java.lang.Long       accountId;
    private java.lang.Long       partyId;
    /**
     * 业务关键字
     */
    private java.lang.String     busId;
    /**
     * 支付类型
     */
    private java.lang.String     payType;
    /**
     * 支付的业务码
     */
    private java.lang.String     payBusCode;
    /**
     * 支付金额
     */
    private java.math.BigDecimal amount;
    /**
     * 支付状态
     */
    private java.lang.String     payStatus;
    /**
     * 支付提示
     */
    private java.lang.String     msg;
    /**
     * 请求时间
     */
    private java.util.Date       createTime;
    /**
     * 更新时间
     */
    private java.util.Date       updateTime;

    private String payUrl;
}
