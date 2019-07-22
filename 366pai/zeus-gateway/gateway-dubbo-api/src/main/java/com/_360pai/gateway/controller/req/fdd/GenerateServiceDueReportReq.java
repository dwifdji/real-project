package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;

/**
 * 描述 尽调报告销售授权委托书 请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/06 14:30
 */
public class GenerateServiceDueReportReq implements Serializable {

    private String seller;//甲方
    private String activityName;//标的名称

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
