package com._360pai.core.vo.lease;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 17:00
 */
@Data
public class LeaseAuditVo implements Serializable {

    private String imgUrl;//

    private String name;//

    private String type;

    private String typeDesc;

    private String modeDesc;

    private String mode;

    private String deposit;

    private String noticeTime;

    private String auctionTime;

    private String auditNum;

    private Integer activityId;

    private String assetId;


}
