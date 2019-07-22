
package com._360pai.core.condition.asset;


import com._360pai.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 *
 * @author Generator
 * @date 2018年08月22日 18时25分08秒
 */
public class AssetFieldCondition implements DaoCondition{

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
    /**
     *
     */
    private Integer tempOptionId;

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

    /**
     *
     */
    public Integer getTempOptionId() {
        return tempOptionId;
    }

    /**
     *
     */
    public void setTempOptionId(Integer tempOptionId) {
        this.tempOptionId = tempOptionId;
    }

}