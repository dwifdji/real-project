package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuhaolei
 * @since 2018-11-12
 */
@Data
@Entity
@Table(name = "t_service_elite")
public class TServiceElite {

    @Id
    @GeneratedValue()
    private Integer id;
    private String name;
    @Column(name = "law_firm")
    private String lawFirm;
    private String position;
    private String city;
    private String career;
    @Column(name = "work_years")
    private String workYears;
    private String education;
    @Column(name = "license_number")
    private String licenseNumber;
    @Column(name = "social_duties")
    private String socialDuties;
    @Column(name = "personal_desc")
    private String personalDesc;
    @Column(name = "persion_image")
    private String persionImage;
    @Column(name = "personal_detail")
    private String personalDetail;
    @Column(name = "server_type")
    private String serverType;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "good_server")
    private String goodServer;
}
