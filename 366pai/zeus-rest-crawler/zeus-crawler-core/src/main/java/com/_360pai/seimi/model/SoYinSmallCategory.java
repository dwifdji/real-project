package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "t_contract_type")
public class SoYinSmallCategory {

    @Id
    @GeneratedValue()
    private Integer id;

    private String name;

    @Column(name = "big_type_id")
    private Integer bigTypeId;

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
