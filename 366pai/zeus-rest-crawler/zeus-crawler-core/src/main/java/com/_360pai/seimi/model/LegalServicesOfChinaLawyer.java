package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 中国法律服务网-律师
 */
@Data
@Entity
@Table(name = "zgfv_12348_lawer2")
public class LegalServicesOfChinaLawyer {
    @Id
    @GeneratedValue()
    private Long id;

    /**
     * 律所id
     */
    @Column(name = "lsswsbs")
    private String lsswsbs;
    /**
     * id
     */
    private String lsbs;
    /**
     * 头像地址
     */
    @Column(name = "img_url")
    private String imgUrl;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 工作年限
     */
    private String years;
    /**
     * 律所名称
     */
    private String lsswsmc;
    /**
     * 专长
     */
    private String ywzc;
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
