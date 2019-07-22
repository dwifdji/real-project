package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * create by liuhaolei on 2018/02/11
 * a资产服务机构实体
 */
@Data
@Entity
@Table(name = "t_winning_bid")
public class WinningBid {

    /**
     * 主键
     */
    @Id
    @GeneratedValue()
    private Integer id;

    /**
     * 编码
     */
    private String code;

    /**
     *  可贷款比例
     */
    @Column(name = "public_time")
    private String publicTime;
    /**
     * 利率
     */
    @Column(name = "lessor")
    private String lessor;

    /**
     * 其他费用
     */
    @Column(name = "rental")
    private String rental;

    /**
     * 其他费用
     */
    @Column(name = "transfer_method")
    private String transferMethod;
    /**
     * 拍品名称
     */
    @Column(name = "bidder_qualification")
    private String bidderQualification;
    /**
     * 拍品城市
     */
    @Column(name = "bidding_party")
    private String biddingParty;
    /**
     * 拍品当前价格
     */
    @Column(name = "bidding_price")
    private String biddingPrice;

    /**
     * 拍品当前价格
     */
    @Column(name = "bidding_time")
    private String biddingTime;

    /**
     * 拍品当前价格
     */
    @Column(name = "winning_bidder")
    private String winningBidder;

    /**
     * 省份
     */
    @Column(name = "filing_time")
    private String filingTime;

    /**
     * 区域
     */
    @Column(name = "winning_price")
    private String winningPrice;


    @Column(name = "hit_time")
    private String hitTime;

    @Column(name = "street_town")
    private String streetTown;

    /**
     * 类别
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "year_time")
    private String yearTime;

}
