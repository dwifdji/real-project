package com._360pai.core.facade.account.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: AcctDetailVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/12/5 16:06
 */
@Data
public class WithdrawAcctDetailVo implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 金额
     */
    private java.math.BigDecimal amount;
    /**
     * 总金额
     */
    private java.math.BigDecimal totalAmt;
    /**
     * 锁定金额
     */
    private java.math.BigDecimal lockAmt;
    /**
     * 余额
     */
    private java.math.BigDecimal availAmt;
    /**
     * 待初审、初审成功/失败、已签合同、已提供发票、发票审核成功/失败、终审成功/失败 、已出款、已标记红冲、红冲
     */
    private String status;

    private String statusDesc;
    /**
     * 用户类型
     */
    private String type;
    /**
     * 用户类型描述
     */
    private String typeDesc;
    /**
     * 初审人员
     */
    private String firstVerifyOperator;
    /**
     * 初审拒绝原因
     */
    private String firstVerifyRefuseReason;
    /**
     * 初审时间
     */
    private java.util.Date firstVerifyTime;
    /**
     * 发票审核人员
     */
    private String invoiceVerifyOperator;
    /**
     * 发票拒绝原因
     */
    private String invoiceVerifyRefuseReason;
    /**
     * 发票审核时间
     */
    private java.util.Date invoiceVerifyTime;
    /**
     *
     */
    private java.util.Date createTime;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 卡号
     */
    private String bankAccountNo;
    /**
     * 开户人
     */
    private String bankAccountName;
    /**
     * 支行名称
     */
    private java.lang.String subBankName;
    /**
     * 名称
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 发票
     */
    private InvoiceVo invoice;
    @JSONField(serialize = false)
    private Integer invoiceId;
    /**
     * 审批单号
     */
    private String batchId;
}
