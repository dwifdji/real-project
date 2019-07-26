package com._360pai.crawler.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: Court
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/10 11:20
 */
@Data
@Entity
@Table(name = "t_lawtime_court")
public class LawtimeCourt {
    @Id
    @GeneratedValue()
    private Integer id;
    /**
     * 详情链接
     */
    @Column(name = "item_url")
    private String itemUrl;
    /**
     * 法院类型
     */
    @Column(name = "type")
    private String type;
    /**
     * 法院名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 法院类型
     */
    @Column(name = "address")
    private String address;
    /**
     * 电话
     */
    @Column(name = "phone")
    private String phone;
    /**
     * 其他联系方式
     */
    @Column(name = "other_phone")
    private String otherPhone;
    /**
     * 工作时间
     */
    @Column(name = "work_time")
    private String workTime;
    /**
     * 描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 图片链接
     */
    @Column(name = "img_url")
    private String imgUrl;
    /**
     * 省
     */
    @Column(name = "province")
    private String province;
    /**
     * 市
     */
    @Column(name = "city")
    private String city;
    /**
     * 是否删除（0否 1是）
     */
    @Column(name = "delete_flag")
    private Boolean deleteFlag;
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
}