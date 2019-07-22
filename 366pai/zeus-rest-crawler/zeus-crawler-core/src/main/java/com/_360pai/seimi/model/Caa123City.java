package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author xiaolei
 * @create 2018-11-15 17:44
 */
@Data
@Entity
@Table(name = "caa123_city")
public class Caa123City {
    @Id
    @Column(name = "city_id")
    private Integer cityId;
    @Column(name = "pro_id")
    private Integer proId;
    @Column(name = "city_name")
    private String cityName;

    private String proName;
}
