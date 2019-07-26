package com._360pai.crawler.model.caa123;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author xiaolei
 * @create 2018-11-15 17:43
 */
@Data
@Entity
@Table(name = "caa123_province")
public class Caa123Province {
    @Id
    @Column(name = "pro_id")
    private Integer proId;
    @Column(name = "pro_name")
    private String proName;
    @Transient
    private List<Caa123City> cities;
}
