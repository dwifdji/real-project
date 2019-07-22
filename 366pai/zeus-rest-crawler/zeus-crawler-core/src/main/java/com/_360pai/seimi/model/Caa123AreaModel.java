package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 刘好磊
 * @create 2018-11-15 17:44
 */
@Data
@Entity
@Table(name = "caa123_area")
public class Caa123AreaModel {
    @Id
    @GeneratedValue()
    private Integer id;

    private String province;

    @Column(name = "province_id")
    private Integer provinceId;

    private String city;

    @Column(name = "city_id")
    private Integer cityId;


    @Column(name = "create_time")
    private Date createTime;
}
