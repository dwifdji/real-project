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
@Table(name = "lj_sell_data")
public class LianJiaSellData implements Serializable {
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
     * 上次交易时间
     */
    @Column(name = "last_time")
    private String lastTime;
    /**
     * 抵押情况
     */
    @Column(name = "mortgage_information")
    private String mortgageInformation;
    /**
     * 房本备件
     */
    @Column(name = "room_preparation")
    private String roomPreparation;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;


}
