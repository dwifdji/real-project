package com._360pai.core.facade;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.req.THallActivityReq;
import com._360pai.core.facade.assistant.req.THallEnrollingActivityReq;
import com.github.pagehelper.PageInfo;

public interface THallActivityFacade {

    Object getTHallActivityList(THallActivityReq req);

    Object getAssetType();

    Object getEnrollingType();

	ResponseModel getHallEnrollingActivities(Integer hallId);

    PageInfo tHallActivityList(THallActivityReq req);

    int addTHallActivity(THallActivityReq req);

    int editTHallActivityList(THallActivityReq req);

    int deleteTHallActivityList(THallActivityReq req);

    PageInfo tEnrollingHallActivityList(THallEnrollingActivityReq req);

    int addEnrollingHallActivity(THallEnrollingActivityReq req);

    int editEnrollingHallActivity(THallEnrollingActivityReq req);

    int deleteEnrollingHallActivity(THallEnrollingActivityReq req);

    Object detailTHallActivityList(THallActivityReq req);

    Object detailEnrollingTHallActivity(THallEnrollingActivityReq req);

    Object getAssetLeaseType();
}
