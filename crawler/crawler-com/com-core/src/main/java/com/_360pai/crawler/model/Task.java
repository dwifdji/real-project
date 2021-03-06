package com._360pai.crawler.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: Task
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/10 14:19
 */
@Data
@Entity
@Table(name = "t_task")
public class Task {
    @Id
    @GeneratedValue()
    private Integer id;
    /**
     * 任务类型
     */
    @Column(name = "type")
    private String type;
    /**
     * 任务状态（0 新建，1 进行中 2 完成，3 失败）
     */
    @Column(name = "status")
    private String status;
    /**
     * 任务备注
     */
    @Column(name = "memo")
    private String memo;
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