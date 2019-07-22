package com._360pai.core.facade.asset.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: Extra
 * @ProjectName zeus-parent
 * @Description:
 * @date 04/09/2018 14:54
 */
public class Extra implements Serializable {
    private List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
