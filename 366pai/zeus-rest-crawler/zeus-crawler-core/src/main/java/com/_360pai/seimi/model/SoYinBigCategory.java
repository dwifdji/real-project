package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "t_contract_big_type")
public class SoYinBigCategory {

    @Id
    @GeneratedValue()
    private Integer id;

    private String name;

    @Column(name = "order_num")
    private Integer orderNum;

    private Boolean display;

    @Column(name = "delete_flag")
    private Boolean deleteFlag;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;


}
