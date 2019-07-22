package com._360pai.core.service.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.req.THallActivityReq;
import com._360pai.core.facade.assistant.req.THallEnrollingActivityReq;
import com._360pai.core.model.assistant.THallActivity;
import com._360pai.core.model.assistant.THallEnrollingActivity;
import com.github.pagehelper.PageInfo;

public interface THallActivityService {
    Object getTHallActivityList(Integer id);

    Object getAssetType();

    Object getEnrollingType();

	ResponseModel getHallEnrollingActivities(Integer hallId);

    PageInfo tHallActivityList(THallActivityReq req);

    int addTHallActivity(THallActivity params);

    int editTHallActivityList(THallActivity params);

    int deleteTHallActivityList(THallActivity params);

    PageInfo tEnrollingHallActivityList(THallEnrollingActivityReq req);

    int addEnrollingHallActivity(THallEnrollingActivity params);

    int editEnrollingHallActivity(THallEnrollingActivity params);

    int deleteEnrollingHallActivity(THallEnrollingActivity params);

    Object detailTHallActivity(Integer id);

    Object detailEnrollingTHallActivity(Integer id);

    Object getAssetLeaseType();
}
