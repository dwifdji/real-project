package com._360pai.crawler.model.auction;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "t_map_auction")
public class TMapAuction {

    @Id
    @GeneratedValue()
    private Integer id;

    /**
     * 数据来源
     */
    @Column(name = "source")
    private String source;
    /**
     * 关联id
     */
    @Column(name = "relevance_id")
    private String relevanceId;
    /**
     * 关联id
     */
    @Column(name = "item_url")
    private String itemUrl;
    /**
     * 类别名称
     */
    @Column(name = "type_name")
    private String typeName;
    /**
     * 类别名称
     */
    @Column(name = "type_code")
    private String typeCode;
    /**
     * 加价幅度
     */
    @Column(name = "raise_price")
    private String raisePrice;
    /**
     * 商品名称
     */
    private String title;
    /**
     * 标的地址
     */
    @Column(name = "address_detail")
    private String addressDetail;
    /**
     * 起拍价
     */
    @Column(name = "starting_price")
    private String startingPrice;
    /**
     * 评估价
     */
    @Column(name = "market_price")
    private String marketPrice;
    /**
     * 保证金
     */
    @Column(name = "deposit")
    private String deposit;
    /**
     * 标地省份
     */
    @Column(name = "pro_name")
    private String proName;
    /**
     * 评估价
     */
    @Column(name = "consult_price")
    private String consultPrice;
    /**
     * 标地城市
     */
    @Column(name = "city_name")
    private String cityName;
    /**
     * 标地区域
     */
    @Column(name = "area_name")
    private String areaName;
    /**
     * 成交价
     */
    @Column(name = "current_price")
    private String currentPrice;
    /**
     * 成交价
     */
    @Column(name = "amount")
    private String amount;
    /**
     * 报名人数
     */
    @Column(name = "appler")
    private String appler;
    /**
     * 浏览人数
     */
    @Column(name = "looker")
    private String looker;

    /**
     * 起拍时间
     */
    @Column(name = "start_date")
    private String startDate;

    /**
     * 结束时间
     */
    @Column(name = "end_date")
    private String endDate;
    /**
     * 拍卖状态
     */
    @Column(name = "status")
    private String status;
    /**
     *  出价次数
     */
    @Column(name = "bid_num")
    private String bidNum;
    /**
     *  纬度
     */
    @Column(name = "lat")
    private BigDecimal lat;
    /**
     *  经度
     */
    @Column(name = "lng")
    private BigDecimal lng;
    /**
     * 建筑面积
     */
    @Column(name = "area")
    private String area;
    /**
     * 建筑面积
     */
    @Column(name = "land_area")
    private String landArea;
    /**
     * 出让类型
     */
    @Column(name = "sell_type")
    private String sellType;
    /**
     * 拍卖阶段
     */
    @Column(name = "stage")
    private String stage;
    /**
     * 拍卖阶段
     */
    @Column(name = "delete_flag")
    private Integer deleteFlag;

    /**
     * 拍卖阶段
     */
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * 拍卖阶段
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 提醒人数
     */
    @Column(name = "reminder")
    private String reminder;

}
