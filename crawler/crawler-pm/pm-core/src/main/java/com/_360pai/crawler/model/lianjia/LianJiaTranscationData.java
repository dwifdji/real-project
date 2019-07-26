package com._360pai.crawler.model.lianjia;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *  刘好磊
 */
@Data
@Entity
@Table(name = "t_map_house_transaction_data")
public class LianJiaTranscationData implements Serializable {
    /**
     * 三方id
     */
    @Id
    @GeneratedValue()
    private Integer id;
    /**
     * 编码
     */
    private String code;
    /**
     * 拍品名称
     */
    private String title;
    /**
     * 当前价
     */
    @Column(name = "current_price")
    private String currentPrice;
    /**
     * 单位价格
     */
    @Column(name = "unit_price")
    private String unitPrice;
    /**
     * 挂牌价格
     */
    @Column(name = "listing_price")
    private String listingPrice;
    /**
     * 成交周期
     */
    @Column(name = "transaction_cycle")
    private Integer transactionCycle;
    /**
     * 调价次数
     */
    @Column(name = "price_adjustment")
    private Integer priceAdjustment;
    /**
     * 带看次数
     */
    @Column(name = "look_times")
    private Integer lookTimes;
    /**
     * 关注数
     */
    @Column(name = "attention_num")
    private Integer attentionNum;
    /**
     * 浏览次数
     */
    @Column(name = "view_num")
    private String viewNum;
    /**
     * 房屋类型
     */
    @Column(name = "house_type")
    private String houseType;
    /**
     * 楼层
     */
    @Column(name = "house_floor")
    private String houseFloor;
    /**
     * 建筑面积
     */
    @Column(name = "construction_area")
    private String constructionArea;
    /**
     * 户型结构
     */
    @Column(name = "house_structure")
    private String houseStructure;
    /**
     * 使用面积
     */
    @Column(name = "inner_area")
    private String innerGrea;
    /**
     * 建筑类型
     */
    @Column(name = "building_type")
    private String buildingType;
    /**
     * 房子方位
     */
    @Column(name = "house_orientation")
    private String houseOrientation;
    /**
     * 建成年代
     */
    @Column(name = "built_era")
    private String builtEra;
    /**
     * 装修情况
     */
    @Column(name = "renovation_condition")
    private String renovationCondition;
    /**
     * 建筑结构
     */
    @Column(name = "building_structure")
    private String buildingStructure;
    /**
     * 供暖方式
     */
    @Column(name = "heating_method")
    private String heatingMethod;

    /**
     * 梯户比例
     */
    @Column(name = "ladder_ratio")
    private String ladderRatio;

    /**
     * 产权年限
     */
    @Column(name = "year_limit")
    private String yearLimit;

    /**
     * 是否有电梯
     */
    @Column(name = "elevator_flag")
    private String elevatorFlag;
    /**
     * 编码
     */
    @Column(name = "trading_authority")
    private String tradingAuthority;
    /**
     * 交易属性
     */
    @Column(name = "listing_time")
    private String listingTime;
    /**
     * 挂牌时间
     */
    @Column(name = "use_type")
    private String useType;
    /**
     * 房屋用途
     */
    @Column(name = "house_limit")
    private String houseLimit;
    /**
     * 区域id
     */
    @Column(name = "area_id")
    private Integer areaId;
    /**
     * 房产归属
     */
    @Column(name = "house_belong")
    private String houseBelong;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 创建时间
     */
    @Column(name = "end_time")
    private String endTime;

    /**
     * 刪除标识
     */
    @Column(name = "delete_flag")
    private Integer deleteFlag;

    /**
     * 结束月份
     */
    @Column(name = "end_month")
    private String endMonth;

    /**
     * 经度
     */
    private String lng;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 纬度
     */
    private String address;

    /**
     * 最新路径
     */
    @Column(name = "item_url")
    private String itemUrl;
}
