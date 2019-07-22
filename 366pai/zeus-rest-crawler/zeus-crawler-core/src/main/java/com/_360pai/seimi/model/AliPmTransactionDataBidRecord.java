package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "ali_pm_transaction_data_bid_record")
public class AliPmTransactionDataBidRecord {

    @Id
    @GeneratedValue()
    private Integer id;

    /**
     * 出价时间
     */
    @Column(name = "bid_time")
    private Date bidTime;
    /**
     * 是否具有优势
     */
    @Column(name = "prior_flag")
    private String priorFlag;

    /**
     * 是否为自己
     */
    @Column(name = "self_flag")
    private String selfFlag;

    /**
     * 拍品编码
     */
    @Column(name = "auction_code")
    private String auctionCode;

    /**
     * 出价价格
     */
    @Column(name = "price")
    private String price;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

}
