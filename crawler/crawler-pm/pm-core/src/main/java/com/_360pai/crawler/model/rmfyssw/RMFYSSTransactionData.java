package com._360pai.crawler.model.rmfyssw;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "rmfyss_transaction_data")
public class RMFYSSTransactionData {

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
     * 成交时间
     */
    @Column(name = "transaction_time")
    private String transactionTime;

    /**
     * 评估价
     */
    @Column(name = "assessment_price")
    private String assessmentPrice;

    /**
     * 评估价
     */
    @Column(name = "shop_phone")
    private String shopPhone;


    /**
     * 开始时间
     */
    @Column(name = "begin_time")
    private String beginTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private String endTime;

    /**
     * 保证金
     */
    @Column(name = "ensure_price")
    private String ensurePrice;

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
     * 报名人数
     */
    @Column(name = "access_ensure_num")
    private String accessEnsureNum;


    /**
     * 报名人数
     */
    @Column(name = "collection_num")
    private String collectionNum;


    /**
     * 处置律所名称
     */
    @Column(name = "shop_name")
    private String shopName;


    /**
     * 处置联系人
     */
    @Column(name = "shop_people")
    private String shopPeople;

    /**
     * 浏览人数
     */
    @Column(name = "access_num")
    private String accessNum;

    /**
     * 浏览人数
     */
    @Column(name = "message_time")
    private String messageTime;



    /**
     *  images
     */
    @Column(name = "img_url")
    private String imgUrl;

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
