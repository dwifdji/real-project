
package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年09月27日 12时52分09秒
 */
public class THallReq extends RequestModel {

    /**
     * 自增
     */
    private Integer id;
    /**
     * 大厅中的类型名称
     */
    private String hallName;
    /**
     * 1：拍卖大厅  2:预招商大厅
     */
    private Integer hallType;
    /**
     * 排序编号
     */
    private Integer orderNum;
    /**
     * 0不删除  1删除
     */
    private Integer delFlag;
    /**
     * 预招商类型 默认:0   1:抵押物 2:债权 3:物权
     */
    private Integer type;

    /**
     * 自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 大厅中的类型名称
     */
    public String getHallName() {
        return hallName;
    }

    /**
     * 大厅中的类型名称
     */
    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    /**
     * 1：拍卖大厅  2:预招商大厅
     */
    public Integer getHallType() {
        return hallType;
    }

    /**
     * 1：拍卖大厅  2:预招商大厅
     */
    public void setHallType(Integer hallType) {
        this.hallType = hallType;
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

    /**
     * 0不删除  1删除
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * 0不删除  1删除
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
