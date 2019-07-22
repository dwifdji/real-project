package com._360pai.core.vo.applet;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/26 13:23
 */
@Data
public class EnrollingAppletDetailVo implements Serializable {
    private String id;
    private String name;
    private String code;
    private String applyNumber;
    private String focusNumber;
    private String browseNumber;
    private String reminderNumber;
    private String image;
    private String status;
    private String statusDesc;
    private String type;
    private String typeDesc;
    private String amount;
    private String deposit;
    private String cityName;
    private String mortgage;
    private String beginAt;
    private String endAt;
    private String source;
    private String releaseAt;
    private Boolean applyFlag;
    private Boolean focusFlag;
    private Boolean reminderFlag;
    private String detail;
    private String phone;

    private Boolean hasExist;

    private String agencyName;

    private Long beginTimestamp;
    private Long endTimestamp;
    private Long currentTimestamp;

    private String competeUrl;



}
