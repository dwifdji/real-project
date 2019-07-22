package com._360pai.seimi.model;

import cn.wanghaomiao.seimi.annotation.Xpath;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zxiao
 * @Title: AliPmContent
 * @ProjectName 345V1
 * @Description:
 * @date 2018/11/8 9:07
 */
@Data
@Entity
@Table(name = "ali_pm")
public class AliPm {

    @Id
    @GeneratedValue()
    private Integer id;

    private String code;

    @Column(name = "item_url")
    private String itemUrl;
    /**
     * 资产名称
     */
    private String title;

    /**
     * 资产区域
     */
    private String area;

    /**
     * 处置单位
     */
    @Xpath("//*[@id=\"page\"]/div[4]/div/div/div[3]/div[2]/p/span[2]/a/text()")
    private String disposition;

    /**
     * 联系人
     */
    @Xpath("//*[@id=\"page\"]/div[4]/div/div/div[3]/div[2]/div/div/em/text()")
    private String person;

    /**
     * 联系电话
     */
    @Xpath("//*[@id=\"page\"]/div[4]/div/div/div[3]/div[2]/div/div/div/div/p[1]/allText()")
    @Column(name = "person_phone")
    private String personPhone;

    /**
     * 起拍价
     */
    @Xpath("//*[@id=\"J_HoverShow\"]/tr[1]/td[1]/span[2]/text()")
    @Column(name = "staring_price")
    private String staringPrice;

    /**
     * 评估价
     */
    @Xpath("//*[@id=\"J_HoverShow\"]/tr[3]/td[1]/span[2]/text()")
    @Column(name = "pay_price")
    private String payPrice;

    /**
     * 成交价
     */
    @Xpath("//*[@id=\"sf-price\"]/div/p[1]/span/em/text()")
    private String amount;

    /**
     * 起拍时间
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 结束时间
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 资产状态
     */
    private String status;

    /**
     * 报名人数
     */
    @Xpath("//*[@id=\"page\"]/div[4]/div/div/div[2]/div[3]/span[1]/em/text()")
    private String appler;

    /**
     * 提醒人数
     */
    @Xpath("//*[@id=\"page\"]/div[4]/div/div/div[2]/div[3]/span[3]/em/text()")
    private String reminder;

    /**
     * 浏览人数
     */
    @Xpath("//*[@id=\"J_Looker\"]/text()")
    private String looker;

    @Column(name = "resp_date")
    private String respDate;

    @Column(name = "current_price")
    private String currentPrice;

    @Column(name = "pay_type")
    private String payType;
}
