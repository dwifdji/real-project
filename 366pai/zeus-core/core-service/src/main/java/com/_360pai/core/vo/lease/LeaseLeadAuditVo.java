package com._360pai.core.vo.lease;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：领导审核信息
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 */
@Data
public class LeaseLeadAuditVo implements Serializable {

    private String imgUrl;//

    private String name;//

    private String type;

    private String typeDesc;

    private String modeDesc;

    private String mode;

    private String deposit;

    private String noticeTime;

    private String auctionTime;

    private String applyEndTime;

    private String createTime;

    private String activityId;

    private String assetId;
    
    private String status;

    private String statusDesc;


}
