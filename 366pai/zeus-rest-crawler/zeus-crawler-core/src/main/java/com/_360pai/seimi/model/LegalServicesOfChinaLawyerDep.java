package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 中国法律服务网-律所
 */
@Data
@Entity
@Table(name = "zgfv_12348_lawerdep2")
public class LegalServicesOfChinaLawyerDep {
    @Id
    @GeneratedValue()
    private Long id;

    /**
     * id
     */
    private String lsswsbs;
    /**
     * 成立时间
     */
    private String pzslsj;

    /**
     * 名称
     */
    private String lsswsmc;
    /**
     *
     */
    private String jd;
    /**
     * 联系地址
     */
    private String zsd;
    /**
     * 联系电话
     */
    private String zsdh;
    /**
     * 执业证号
     */
    private String zyzh;
    /**
     * 所属地区
     */
    private String district;
    /**
     * 图片地址
     */
    private String imgurl;
    /**
     * 省code
     */
    private String provcode;
    /**
     * 城市code
     */
    private String citycode;
    /**
     * 区域code
     */
    private String xzqh;
    /**
     *
     */
    private String zt;
    /**
     *
     */
    private String wd;
    /**
     * 团队规模
     */
    private String nums;
    /**
     * 简介
     */
    private String jj;
    /**
     * 执业证号
     */
    private String tyshxydm;
    /**
     *
     */
    private String yezc;
    /**
     *
     */
    private String ywzclist;
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
