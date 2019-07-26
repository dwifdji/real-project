package com._360pai.crawler.model.jdPm;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "jd_pm_transaction_data_bank_legal")
public class JdPmTransactionDataBankLegal {

    @Id
    @GeneratedValue()
    private Integer id;

    /**
     *  银行名称
     */
    @Column(name = "bank_name")
    private String bankName;
    /**
     * 银行标识
     */
    @Column(name = "bank_logo")
    private String bankLogo;

    /**
     * 贷款利率
     */
    @Column(name = "loan_rate")
    private String loanRate;
    /**
     * 最高利率上浮
     */
    @Column(name = "max_loan_ratio")
    private String maxLoanRatio;

    /**
     * 其他费用
     */
    @Column(name = "other_expenses")
    private String otherExpenses;

    /**
     * 拍品编码
     */
    @Column(name = "auction_code")
    private String auctionCode;


    /**
     * 申请数量
     */
    @Column(name = "apply_count")
    private String applyCount;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

}
