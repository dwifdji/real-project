package com._360pai.core.service.assistant;

import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.enrolling.EnrollingActivity;

import java.util.Date;
import java.util.List;

/**
 * @author xdrodger
 * @Title: AssistantService
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/7/4 10:05
 */
public interface AssistantService {

    void setActivityExpireTime(AuctionActivity activity);

    void setEnrollingActivityExpireTime(EnrollingActivity activity);

    void batchEnrollingActivityExpireTime(List<String> list, Date endAt);

    void setActivityExpireTime();

    void removeActivityExpireKeyInRedisEndAtOver2Days();
}
