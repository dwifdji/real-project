
package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年09月18日 16时47分54秒
 */
public class THallActivityReq extends RequestModel {

    /**
     * 自增主键
     */
    private Integer id;
    /**
     * 大厅类型ID
     */
    private Integer hallId;
    /**
     * 活动ID
     */
    private Integer activityId;
    /**
     * 排序编号
     */
    private Integer orderNum;

    /**
     * 自增主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 大厅类型ID
     */
    public Integer getHallId() {
        return hallId;
    }

    /**
     * 大厅类型ID
     */
    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    /**
     * 活动ID
     */
    public Integer getActivityId() {
        return activityId;
    }

    /**
     * 活动ID
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     * 排序编号
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 排序编号
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

}
