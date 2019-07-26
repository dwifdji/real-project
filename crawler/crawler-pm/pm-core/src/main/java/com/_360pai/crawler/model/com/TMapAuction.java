package com._360pai.crawler.model.com;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 拍卖总表
 */
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
     * 资产名称
     */
    private String title;


    @Column(name = "item_url")
    private String itemUrl;






    /**
     * 起拍价
     */
    @Column(name = "starting_price")
    private String startingPrice;



    /**
     * 成交价
     */
     private String amount;

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
     * 资产状态
     */
    private String status;



    /**
     * 提醒人数
     */
     private String reminder;

    /**
     * 浏览人数
     */
     private String looker;


    /**
     * 当前价
     */
    @Column(name = "current_price")
    private String currentPrice;


    /**
     *
     *省份名称
     */
    @Column(name = "pro_name")
    private String proName;


    /**
     *
     *城市名称
     */
    @Column(name = "city_name")
    private String cityName;



    /**
     *
     *地区名称
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     *
     *加价幅度
     */
    @Column(name = "raise_price")
    private String raisePrice;


    /**
     *
     *保证金
     */
    @Column(name = "deposit")
    private String deposit;



    /**
     *
     *删除标志
     */
    @Column(name = "delete_flag")
    private Integer deleteFlag;
    /**
     *
     *创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     *
     *更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;


    @Column(name = "market_price")
    private String marketPrice;



    @Column(name = "type_name")
    private String typeName;


    @Column(name = "type_code")
    private String typeCode;


    /**
     *
     *评估价
     */
    @Column(name = "consult_price")
    private String consultPrice;


    /**
     * 报名人数
     */
    private String appler;





    @Column(name = "address_detail")
    private String addressDetail;


    @Column(name = "lat")
    private String lat;


    @Column(name = "lng")
    private String lng;


    @Column(name = "area")
    private String area;




    @Column(name = "sell_type")
    private String sellType;


    @Column(name = "stage")
    private String stage;



    @Column(name = "land_area")
    private String landArea;

    @Column(name = "bid_num")
    private String bidNum;


}
