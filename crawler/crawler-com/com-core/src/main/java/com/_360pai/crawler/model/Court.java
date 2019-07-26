package com._360pai.crawler.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: Court
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/10 11:20
 */
@Data
@Entity
@Table(name = "t_court")
public class Court {
    @Id
    @GeneratedValue()
    private Integer id;
    /**
     * 法院名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 法院类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 是否删除（0否 1是）
     */
    @Column(name = "delete_flag")
    private Boolean deleteFlag;
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