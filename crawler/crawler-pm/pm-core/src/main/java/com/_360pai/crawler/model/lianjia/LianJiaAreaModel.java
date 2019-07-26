package com._360pai.crawler.model.lianjia;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 刘好磊
 * @create 2018-04-03
 */
@Data
@Entity
@Table(name = "t_map_house_area")
public class LianJiaAreaModel {
    @Id
    @GeneratedValue()
    private Integer id;

    private String province;

    private String city;

    private String area;

    @Column(name = "area_url")
    private String areaUrl;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "transaction_num")
    private Integer transactionNum;

    @Column(name = "sell_num")
    private Integer sellNum;
}
