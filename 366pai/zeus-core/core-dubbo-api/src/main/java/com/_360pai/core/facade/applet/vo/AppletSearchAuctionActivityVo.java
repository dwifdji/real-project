package com._360pai.core.facade.applet.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 小程序查询类表返回
 *
 */
@Data
public class AppletSearchAuctionActivityVo implements Serializable {
    private Integer activityId;
    private String beginAt;
    private String endAt;
    private String previewAt;
    private String activityName;
    private String status;
    private String deposit;
    private String modeStr;
    private String statusStr;
    private String mode;
    private String cityName;
    private String imageUrl;
    private String startingPrice;
    private String cityId;
    private String assetCategoryGroupId;
    private Long beginTimestamp;
    private Long endTimestamp;
    private String pushFlag;
    private String categoryName;
    private String choseFlag;
    private String incrementAt;
    private Long incrementTimestamp;
    private long currentTimestamp;
    private String reservePrice;

    private String bigType;

    public String getBigType() {
        return "1";
    }
}
