package com._360pai.core.facade.assistant.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: BackAppletHallActivity
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/2/28 19:12
 */
@Data
public class BackAppletHallActivity implements Serializable {
    /**
     * 自增主键
     */
    private Integer id;
    /**
     * 活动类型 0 拍卖 1 招商
     */
    private String type;
    /**
     * 活动ID
     */
    private Integer activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 类型名称
     */
    private String categoryName;
    /**
     * 排序编号
     */
    private Integer orderNum;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
}
