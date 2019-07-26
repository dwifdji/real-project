package com._360pai.crawler.model.alipm;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * 阿里司法拍卖 url
 */
@Data
@Entity
@Table(name = "t_base_ali_pm_sf_url")
public class TAliPmSfUrl {

    /**
     * 主键
     */
    @Id
    @GeneratedValue()
    private Integer id;


    /**
     *  省份
     */
    @Column(name = "pro_name")
    private String proName;
    /**
     * 城市
     */
    @Column(name = "city_name")
    private String cityName;

    /**
     * 地区
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 请求url
     */
    @Column(name = "req_url")
    private String reqUrl;
    /**
     * 拍品数量
     */
    @Column(name = "num")
    private String num;
    /**
     * 删除标志
     */
    @Column(name = "delete_flag")
    private Integer deleteFlag;
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

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "category_code")
    private String categoryCode;


    @Column(name = "category_name")
    private String categoryName;


    @Column(name = "status")
    private String status;



}
