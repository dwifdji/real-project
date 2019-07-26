package com._360pai.crawler.model.alipm;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 阿里S司法
 */
@Data
@Entity
@Table(name = "t_base_ali_pm_sf")
public class TAliPmSf {

    @Id
    @GeneratedValue()
    private Integer id;


    @Column(name = "data_detail")
    private String dataDetail;

    @Column(name = "bid_num")
    private String bidNum;



    @Column(name = "code")
    private String code;

    @Column(name = "item_url")
    private String itemUrl;
    /**
     * 资产名称
     */
    private String title;


    /**
     * 处置单位
     */
     private String disposition;

    /**
     * 联系人
     */
     private String person;

    /**
     * 联系电话
     */
    @Column(name = "person_phone")
    private String personPhone;

    /**
     * 起拍价
     */
    @Column(name = "staring_price")
    private String staringPrice;



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
     * 报名人数
     */
     private String appler;

    /**
     * 提醒人数
     */
     private String reminder;

    /**
     * 浏览人数
     */
     private String looker;

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
     *省份名称
     */
    @Column(name = "pic_url")
    private String picUrl;
    /**
     *
     *加价幅度
     */
    @Column(name = "raise_price")
    private String raisePrice;
    /**
     *
     *运费
     */
    @Column(name = "freight")
    private String freight;
    /**
     *
     *竞价周期
     */
    @Column(name = "cycle")
    private String cycle;
    /**
     *
     *优先购买人
     */
    @Column(name = "preemptor")
    private String preemptor;
    /**
     *
     *保证金
     */
    @Column(name = "deposit")
    private String deposit;


    /**
     *
     *延迟周期
     */
    @Column(name = "delay_period")
    private String delayPeriod;
    /**
     *
     *保留价
     */
    @Column(name = "reserve_price")
    private String reservePrice;
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


    @Column(name = "consult_price")
    private String consultPrice;




    @Column(name = "start_stamp")
    private String startStamp;


    @Column(name = "end_stamp")
    private String endStamp;




    @Column(name = "address_detail")
    private String addressDetail;


    @Column(name = "lat")
    private String lat;


    @Column(name = "lng")
    private String lng;


    @Column(name = "area")
    private String area;



    @Column(name = "notice_url")
    private String noticeUrl;


    @Column(name = "instruction_url")
    private String instructionUrl;


    @Column(name = "detail_url")
    private String detailUrl;

    @Column(name = "sell_type")
    private String sellType;


    @Column(name = "stage")
    private String stage;




}
