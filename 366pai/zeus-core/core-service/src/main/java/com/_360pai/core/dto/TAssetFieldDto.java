
package com._360pai.core.dto;

import com._360pai.arch.common.RequestModel;

import java.io.Serializable;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public class TAssetFieldDto implements Serializable {

    /**
     * 自增id
     */
    private Integer id;
    /**
     * 字段名
     */
    private String name;
    /**
     * 字段中文名称
     */
    private String label;
    /**
     * 提示内容
     */
    private String hint;
    /**
     * 类型
     */
    private String type;
    /**
     * 字段分组ID
     */
    private Integer fieldGroupId;
    /**
     * 是否在前端显示
     */
    private Boolean frontShow;
    /**
     * 排序编号
     */
    private Integer orderNum;

    /**
     * 自增id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 字段名
     */
    public String getName() {
        return name;
    }

    /**
     * 字段名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 字段中文名称
     */
    public String getLabel() {
        return label;
    }

    /**
     * 字段中文名称
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 提示内容
     */
    public String getHint() {
        return hint;
    }

    /**
     * 提示内容
     */
    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     * 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 字段分组ID
     */
    public Integer getFieldGroupId() {
        return fieldGroupId;
    }

    /**
     * 字段分组ID
     */
    public void setFieldGroupId(Integer fieldGroupId) {
        this.fieldGroupId = fieldGroupId;
    }

    /**
     * 是否在前端显示
     */
    public Boolean getFrontShow() {
        return frontShow;
    }

    /**
     * 是否在前端显示
     */
    public void setFrontShow(Boolean frontShow) {
        this.frontShow = frontShow;
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
