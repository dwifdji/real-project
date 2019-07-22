package com._360pai.core.facade.activity.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: AgencyUnionDataVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/10 15:04
 */
@Getter
@Setter
public class AgencyUnionDataVo implements Serializable {
    private String agencyName;
    private String agencyCode;
    private String logoUrl;
    private String cityName;
    private String provinceName;
    private Integer viewCount;
    private Integer participantNumber;
    private Integer bidCount;
}
