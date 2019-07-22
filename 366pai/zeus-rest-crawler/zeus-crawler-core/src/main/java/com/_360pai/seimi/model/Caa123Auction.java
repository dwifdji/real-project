package com._360pai.seimi.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-11-14 11:03
 */
@Data
@Entity
@Table(name = "caa123_auction")
public class Caa123Auction implements Serializable {
    /**
     * 三方id
     */
    @Id
    private Integer id;
    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;
    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;
    /**
     * 变卖价
     */
    @Column(name = "start_price")
    private BigDecimal startPrice;
    /**
     * 评估价
     */
    @Column(name = "assess_price")
    private BigDecimal assessPrice;
    /**
     * 拍品名称
     */
    private String name;
    /**
     * 当前价
     */
    @Column(name = "now_price")
    private BigDecimal nowPrice;
    /**
     * 延时周期
     */
    @Column(name = "delay_time")
    private String delayTime;
    /**
     * 加价幅度
     */
    @Column(name = "rate_ladder")
    private BigDecimal rateLadder;
    /**
     * 保证金
     */
    @Column(name = "cash_deposit")
    private BigDecimal cashDeposit;
    /**
     * 拍品大图
     */
    @Column(name = "pic_large")
    private String picLarge;
    /**
     * 拍品小图
     */
    @Column(name = "pic_small")
    private String picSmall;
    /**
     * 处置法院
     */
    private String court;
    /**
     * 联系人
     */
    @Column(name = "link_man")
    private String linkMan;
    /**
     * 联系电话
     */
    @Column(name = "link_tel")
    private String linkTel;
    /**
     * 联系手机
     */
    @Column(name = "link_mobile")
    private String linkMobile;
    /**
     * 拍品所在地
     */
    private String position;
    /**
     * 城市id
     */
    @Column(name = "city_id")
    private Integer cityId;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    @Setter
    @Getter
    static class Pic implements Serializable{
        String name;
        String filePath;
    }
}
