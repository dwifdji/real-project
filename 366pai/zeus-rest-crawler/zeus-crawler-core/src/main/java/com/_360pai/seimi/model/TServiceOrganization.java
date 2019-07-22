package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;

/**
 * create by liuhaolei on 2018/11/13
 * a资产服务机构实体
 */
@Data
@Entity
@Table(name = "t_service_agency")
public class TServiceOrganization {

    /**
     * 主键
     */
    @Id
    @GeneratedValue()
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 服务类型
     */
    @Column(name = "server_type")
    private String serverType;
    /**
     * 头像
     */
    private String avatar;

    /**
     * 机构主站地址
     */
    @Column(name = "agency_url")
    private String agencyUrl;
    /**
     * 城市
     */
    private String city;
    /**
     * 成立日期
     */
    @Column(name = "established_date")
    private String establishedDate;
    /**
     * 机构类型
     */
    @Column(name = "agency_type")
    private String agencyType;
    /**
     * 机构编码
     */
    @Column(name = "organization_code")
    private String organizationCode;
    /**
     * 规模
     */
    private String scale;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 联系邮箱
     */
    private String email;
    /**
     * 简介图片
     */
    @Column(name = "desc_image")
    private String descImage;
    /**
     * 公司简介
     */
    @Column(name = "company_desc")
    private String companyDesc;
    /**
     * 机构成员
     */
    private String members;
    /**
     * 业务范围
     */
    @Column(name = "business_scope")
    private String businessScope;

}
