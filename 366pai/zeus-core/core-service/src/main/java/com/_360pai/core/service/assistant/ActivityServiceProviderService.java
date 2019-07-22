package com._360pai.core.service.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.model.account.TDisposeProvider;

import java.util.List;

/**
 * @author xdrodger
 * @Title: ActivityServiceProvider
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-05-21 14:21
 */
public interface ActivityServiceProviderService {

    ResponseModel getActivityServiceProvider(ActivityReq.ActivityId req);

    ResponseModel activityServiceProviderEnroll(ActivityReq.ActivityId req);

    ResponseModel activityServiceProviderSetup(ActivityReq.ActivityServiceProviderReq req);

    ResponseModel cancelActivityServiceProvider(ActivityReq.ActivityServiceProviderReq req);

    ResponseModel updateActivityServiceProvider(ActivityReq.ActivityServiceProviderReq req);

    ResponseModel getDisposeActivityServiceProviderEnrollRecord(ActivityReq.ActivityServiceProviderReq req);

    ResponseModel getFundActivityServiceProviderEnrollRecord(ActivityReq.ActivityServiceProviderReq req);

    ResponseModel getBackgroundActivityServiceProvider(ActivityReq.ActivityServiceProviderReq req);

    List<TDisposeProvider> getDisposeProvider(Integer activityId, String activityType);

    List<TDisposeProvider> getEnrollingDisposeProvider(Integer activityId);
}
