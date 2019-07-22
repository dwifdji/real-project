package com._360pai.core.dto.enrolling;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 14:02
 */
public class ActivityIdReqDto extends PageReqDto{

    /**
     * 每页数量.
     */
    private String  activityId;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
