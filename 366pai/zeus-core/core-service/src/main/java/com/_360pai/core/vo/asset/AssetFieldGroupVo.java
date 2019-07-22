
package com._360pai.core.vo.asset;


import com._360pai.core.model.asset.AssetField;
import com._360pai.core.model.asset.AssetFieldItem;

import java.util.List;

public class AssetFieldGroupVo implements java.io.Serializable {

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
    private List<AssetField> fields;
    /**
     *
     */
    private List<AssetFieldItem> items;

    public List<AssetField> getFields() {
        return fields;
    }

    public void setFields(List<AssetField> fields) {
        this.fields = fields;
    }

    public List<AssetFieldItem> getItems() {
        return items;
    }

    public void setItems(List<AssetFieldItem> items) {
        this.items = items;
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
