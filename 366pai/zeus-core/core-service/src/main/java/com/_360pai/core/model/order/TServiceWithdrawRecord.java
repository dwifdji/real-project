
package com._360pai.core.model.order;

import lombok.Data;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年10月09日 18时17分51秒
 */
@Data
public class TServiceWithdrawRecord implements java.io.Serializable {

    /**
     * 主键
     */
    private Integer              id;
    /**
     * 提现订单号
     */
    private String               withdrawNo;
    /**
     * 提现银行id
     */
    private Integer              bankId;
    /**
     * 银行卡号
     */
    private String               bankNo;
    /**
     * 用户id
     */
    private Integer              accountId;
    private Integer              partyId;
    /**
     * 用户类型  "user","company"
     */
    private String               accountType;
    /**
     * 用户名称
     */
    private String               accountName;
    /**
     * 提现金额
     */
    private java.math.BigDecimal withdrawAmount;
    /**
     * 审核时间
     */
    private java.util.Date       verifyTime;
    /**
     * 审核状态 10:待转账 20：已拒绝 30：已提现
     */
    private String               verifyStatus;
    /**
     * 审核人id
     */
    private Integer              verifyOperator;
    /**
     * 审核原因
     */
    private String               verifyContent;
    /**
     * 额外信息
     */
    private String               additional;
    /**
     * 创建时间
     */
    private java.util.Date       createTime;
    /**
     * 修改时间
     */
    private java.util.Date       updateTime;
    /**
     * 删除标识 0:未删除 1：删除
     */
    private Boolean              delFlag;

}
