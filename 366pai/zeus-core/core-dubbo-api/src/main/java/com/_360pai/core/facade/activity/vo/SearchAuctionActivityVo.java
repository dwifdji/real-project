package com._360pai.core.facade.activity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: ActivityVo
 * @ProjectName zeus-parent
 * @Description:
 * @date 04/09/2018 15:52
 */
@Getter
@Setter
public class SearchAuctionActivityVo implements Serializable {
    private Integer activityId;
    private Date beginAt;
    private Date endAt;
    private Date previewAt;
    private String activityName;
    private Integer viewCount = 0;
    private String status;
    private String deposit;
    private String modeStr;
    private String statusStr;
    private String mode;
    private String categoryName;
    private String propertyName;
    private String cityName;
    private String imageUrl;
    private Integer depositCount;
    private String startingPrice;
    @JSONField(serialize = false)
    private String provinceId;
    @JSONField(serialize = false)
    private String areaName;
    @JSONField(serialize = false)
    private String provinceName;

    private String categoryId;

}
