
package com._360pai.core.vo.asset;

import java.io.Serializable;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月08日 14时24分17秒
 */
public class AssetFieldVo implements Serializable {

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String groupName;
    /**
     *
     */
    private String label;
    /**
     *
     */
    private String hint;
    /**
     *
     */
    private String type;
    /**
     *
     */
    private Integer groupId;
    /**
     *
     */
    private Boolean frontShow;
    /**
     *
     */
    private Integer orderNum;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     *
     */
    public String getHint() {
        return hint;
    }

    /**
     *
     */
    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     *
     */
    public String getType() {
        return type;
    }

    /**
     *
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     *
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     *
     */
    public Boolean getFrontShow() {
        return frontShow;
    }

    /**
     *
     */
    public void setFrontShow(Boolean frontShow) {
        this.frontShow = frontShow;
    }

    /**
     *
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     *
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

}
