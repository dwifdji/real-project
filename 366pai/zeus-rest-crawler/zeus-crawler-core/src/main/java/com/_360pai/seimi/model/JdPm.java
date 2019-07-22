package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zxiao
 * @Title: JdPm
 * @ProjectName 345V1
 * @Description:
 * @date 2018/11/9 14:32
 */
@Data
@Entity
@Table(name = "jd_pm")
public class JdPm {

    @Id
    @GeneratedValue()
    private Integer id;

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
    private BigDecimal startPrice;
    /**
     * 送拍机构
     */
    private String agency;
    /**
     * 当前价
     */
    @Column(name = "current_price")
    private BigDecimal currentPrice;
    /**
     * 联系方式
     */
    @Column(name = "person_phone")
    private String personPhone;
    /**
     * 关注人数
     */
    @Column(name = "remind_count")
    private String remindCount;
    /**
     * 报名人数
     */
    @Column(name = "access_ensure_num")
    private String accessEnsureNum;
    /**
     * 浏览人数
     */
    @Column(name = "access_num")
    private String accessNum;
    /**
     * 起拍时间
     */
    @Column(name = "start_time")
    private Date startTime;
    /**
     * 起拍时间
     */
    @Column(name = "end_time")
    private Date endTime;
    /**
     * 成交价
     */
    private BigDecimal amount;
    /**
     * 状态
     */
    private String status;
    /**
     * 状态
     */
    @Column(name = "display_status")
    private String displayStatus;
    /**
     * 商品列表返回数据
     */
    @Column(name = "resp_data")
    private String respData;
    /**
     * 报名和浏览数据
     */
    @Column(name = "query_data")
    private String queryData;
    /**
     * 商品基础数据
     */
    @Column(name = "product_basic_info")
    private String productBasicInfo;


}
