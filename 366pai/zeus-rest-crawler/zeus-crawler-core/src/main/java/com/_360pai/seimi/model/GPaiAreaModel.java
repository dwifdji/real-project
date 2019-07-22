package com._360pai.seimi.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "gpai_pm_area")
public class GPaiAreaModel {

    @Id
    @GeneratedValue()
    private Integer id;

    private String province;

    @Column(name = "province_id")
    private Integer provinceId;

    private String city;

    @Column(name = "city_id")
    private Integer cityId;

    private String area;

    @Column(name = "area_id")
    private Integer areaId;

    @Column(name = "create_time")
    private Date createTime;
}
