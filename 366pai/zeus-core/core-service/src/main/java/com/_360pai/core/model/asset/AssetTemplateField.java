
package com._360pai.core.model.asset;

import java.util.List;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月10日 18时47分25秒
 */
public class AssetTemplateField implements java.io.Serializable {

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
    private Integer orderNum;
    /**
     *
     */
    private Boolean selected;
    /**
     *
     */
    private List<AssetTemplateFieldOption> options;

    public List<AssetTemplateFieldOption> getOptions() {
        return options;
    }

    public void setOptions(List<AssetTemplateFieldOption> options) {
        this.options = options;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
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
