package com._360pai.core.facade.account.vo;

import com._360pai.core.facade.activity.vo.FileInfo;
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
public class AcctDetailVo implements Serializable {

    /**
     *
     */
    private String id;
    /**
     * 类型 充值、其他、开店推荐费、成交佣金、提现
     */
    private String type;

    private String typeDesc;
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
     * 备份金额,用于红冲、取消出款
     */
    private java.math.BigDecimal backupAmt;
    /**
     * 待初审、初审成功/失败、已签合同、已提供发票、发票审核成功/失败、终审成功/失败 、已出款、已标记红冲、红冲
     */
    private String status;
    private String statusDesc;
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
     * 发票审核拒绝原因
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
     * 记录描述
     */
    private String desc;
    /**
     * 涉及账户
     */
    private String relatedMobile;
    /**
     * 充值渠道
     */
    private String rechargeChannel;
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
     * 提现合同
     */
    private FileInfo withdrawAgreement;
    /**
     * 发票
     */
    private InvoiceVo invoice;
    /**
     * 拒绝原因
     */
    private String refuseReason;
}
