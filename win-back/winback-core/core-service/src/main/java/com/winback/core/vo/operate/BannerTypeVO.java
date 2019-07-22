package com.winback.core.vo.operate;

import java.io.Serializable;
import java.util.List;

public class BannerTypeVO implements Serializable {
    private String type;

    private String typeDesc;

    private List<HomePageBannerVO> homePageBannerVOS;

    public List<HomePageBannerVO> getHomePageBannerVOS() {
        return homePageBannerVOS;
    }

    public void setHomePageBannerVOS(List<HomePageBannerVO> homePageBannerVOS) {
        this.homePageBannerVOS = homePageBannerVOS;
    }

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

    public BannerTypeVO(String type, String typeDesc) {
        this.type = type;
        this.typeDesc = typeDesc;
    }
}
