package com.winback.core.vo.operate;

import java.io.Serializable;
import java.util.List;

public class HomeIconCategoryGroupTypeVO implements Serializable {

    private String type;

    private String typeDesc;

    private List<HomeIconCategoryVO> homeIconCategoryVOS;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public List<HomeIconCategoryVO> getHomeIconCategoryVOS() {
        return homeIconCategoryVOS;
    }

    public void setHomeIconCategoryVOS(List<HomeIconCategoryVO> homeIconCategoryVOS) {
        this.homeIconCategoryVOS = homeIconCategoryVOS;
    }

    public HomeIconCategoryGroupTypeVO(String type, String typeDesc) {
        this.type = type;
        this.typeDesc = typeDesc;
    }
}
