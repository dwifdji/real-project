package com._360pai.crawler.model.caa123;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "caa123_transaction_data_bid_record")
public class Caa123TransactionDataBidRecord {

    @Id
    @GeneratedValue()
    private Integer id;

    /**
     * 出价时间
     */
    @Column(name = "bid_time")
    private Date bidTime;

    /**
     * 出价编号
     */
    @Column(name = "bid_num")
    private String bidNum;


    /**
     * 拍品编码
     */
    @Column(name = "lot_id")
    private Integer lotId;

    /**
     * 出价价格
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * 用户名
     */
    @Column(name = "bidder_name")
    private String bidderName;

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
