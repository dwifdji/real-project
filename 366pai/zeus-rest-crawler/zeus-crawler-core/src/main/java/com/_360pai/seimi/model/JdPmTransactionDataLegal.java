package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "jd_pm_transaction_data_legal")
public class JdPmTransactionDataLegal {

    @Id
    @GeneratedValue()
    private Integer id;

    /**
     * 类别名称
     */
    @Column(name = "category_name")
    private String categoryName;
    /**
     * 商品CODE
     */
    private String code;
    /**
     * 商品名称
     */
    private String title;
    /**
     * 标的地址
     */
    private String address;
    /**
     * 起拍价
     */
    @Column(name = "start_price")
    private String startPrice;

    /**
     * 评估价
     */
    @Column(name = "assessment_price")
    private String assessmentPrice;

    /**
     * 评估价
     */
    @Column(name = "priceLower_offset")
    private String priceLowerOffset;


    /**
     * 保证金
     */
    @Column(name = "ensure_price")
    private String ensurePrice;

    /**
     * 处置律师
     */
    @Column(name = "consult_name")
    private String consultName;

    /**
     * 处置律师电话
     */
    @Column(name = "consult_tel")
    private String consulTel;

    /**
     * 标地省份
     */
    @Column(name = "province")
    private String province;

    /**
     * 标地城市
     */
    @Column(name = "city")
    private String city;

    /**
     * 标地区域
     */
    @Column(name = "county")
    private String county;

    /**
     * 成交价
     */
    @Column(name = "current_price")
    private String currentPrice;

    /**
     * 延迟次数
     */
    @Column(name = "delayed_count")
    private String delayedCount;

    /**
     * 报名人数
     */
    @Column(name = "access_ensure_num")
    private String accessEnsureNum;

    /**
     * 处置律所名称
     */
    @Column(name = "shop_name")
    private String shopName;

    /**
     * 浏览人数
     */
    @Column(name = "access_num")
    private String accessNum;

    /**
     * 起拍时间
     */
    @Column(name = "begin_time")
    private Date beginTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;


    /**
     * 成交确定书地址
     */
    @Column(name = "confirmation_url")
    private String confirmationUrl;

    /**
     * 优先竞买人标识
     */
    @Column(name = "priority_flag")
    private String priorityFlag;

    /**
     * 拍卖次数
     */
    @Column(name = "auction_times")
    private String auctionTimes;


    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

}
