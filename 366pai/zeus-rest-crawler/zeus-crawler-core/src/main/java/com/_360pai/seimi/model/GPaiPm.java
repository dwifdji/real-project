package com._360pai.seimi.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: 公拍网
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/14 14:07
 */
@Data
@Entity
@Table(name = "gpai_pm")
public class GPaiPm {
    @Id
    @GeneratedValue()
    private Integer id;
    /**
     * 商品ID
     */
    @Column(name = "item_id")
    private Integer itemId;
    /**
     * 商品ID
     */
    @Column(name = "item_url")
    private String itemUrl;
    /**
     * 标题
     */
    private String title;
    /**
     * 起拍价
     */
    @Column(name = "start_price")
    private BigDecimal startPrice;
    /**
     * 参考价
     */
    @Column(name = "ref_price")
    private BigDecimal refPrice;
    /**
     * 当前价
     */
    @Column(name = "current_price")
    private BigDecimal currentPrice;
    /**
     * 成交价
     */
    private BigDecimal amount;
    /**
     * 加价幅度
     */
    private BigDecimal increment;
    /**
     * 保证金
     */
    private BigDecimal deposit;
    /**
     * 延时分钟数
     */
    @Column(name = "bidding_extension")
    private Integer biddingExtension;
    /**
     * 开始时间
     */
    @Column(name = "begin_at")
    private String beginAt;
    /**
     * 结束时间
     */
    @Column(name = "end_at")
    private String endAt;
    /**
     * 联系人
     */
    @Column(name = "contact_name")
    private String contactName;
    /**
     * 联系人方式
     */
    @Column(name = "contact_phone")
    private String contactPhone;
    /**
     * 报名人数
     */
    @Column(name = "participant_number")
    private Integer participantNumber;
    /**
     * 提醒人数
     */
    @Column(name = "remind_count")
    private Integer remindCount;
    /**
     * 浏览人数
     */
    @Column(name = "view_count")
    private Integer viewCount;
    /**
     * 包含图片的JSON数据
     */
    //@Convert(converter = JpaConverterJson.class)
    private String extra;
    /**
     * 执行法院
     */
    @Column(name = "court_name")
    private String courtName;
    /**
     * 拍卖模式
     */
    @Column(name = "item_mode")
    private String itemMode;
    /**
     * 拍卖状态
     */
    private String state;
    /**
     * 拍卖状态描述
     */
    @Column(name = "state_desc")
    private String stateDesc;
    /**
     * 拍卖方式
     */
    @Column(name = "bid_mode")
    private String bidMode;
    /**
     * 标的城市
     */
    private Integer city;

    /**
     * 拍卖机构
     */
    private String agency;
    /**
     * 是否删除（0否 1是）
     */
    @Column(name = "is_delete")
    private Boolean isDelete;
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

    @Column(name = "item_type")
    private String itemType;

}
