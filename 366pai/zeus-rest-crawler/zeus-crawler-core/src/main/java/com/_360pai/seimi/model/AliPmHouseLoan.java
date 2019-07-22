package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;

/**
 * create by liuhaolei on 2018/12/07
 * a资产服务机构实体
 */
@Data
@Entity
@Table(name = "ali_pm_house_loan")
public class AliPmHouseLoan {

    /**
     * 主键
     */
    @Id
    @GeneratedValue()
    private Integer id;

    /**
     * 机构
     */
    private String agency;

    /**
     *  可贷款比例
     */
    @Column(name = "loan_ratio")
    private String loanRatio;
    /**
     * 利率
     */
    @Column(name = "interest_rate")
    private String interestRate;

    /**
     * 其他费用
     */
    @Column(name = "other_fee")
    private String otherFee;

    /**
     * 其他费用
     */
    @Column(name = "city_item")
    private String cityItem;
    /**
     * 拍品名称
     */
    @Column(name = "activity_name")
    private String activityName;
    /**
     * 拍品城市
     */
    @Column(name = "activity_city")
    private String activityCity;
    /**
     * 拍品当前价格
     */
    @Column(name = "activity_price")
    private String activityPrice;

    /**
     * 拍品当前价格
     */
    @Column(name = "pic_url")
    private String picUrl;

    /**
     * 拍品当前价格
     */
    @Column(name = "type_group")
    private String typeGroup;

    /**
     * 省份
     */
    @Column(name = "province")
    private String province;

    /**
     * 区域
     */
    @Column(name = "area")
    private String area;

    /**
     * 类别
     */
    @Column(name = "house_type")
    private String houseType;

}
