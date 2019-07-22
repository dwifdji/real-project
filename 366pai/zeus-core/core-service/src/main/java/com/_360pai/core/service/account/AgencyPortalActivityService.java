package com._360pai.core.service.account;


import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com._360pai.core.model.activity.AgencyPortalActivity;

import java.util.List;

public interface AgencyPortalActivityService {
    AgencyPortalActivity getByAgencyIdActivityId(Integer agencyId, Integer activityId);

    AgencyPortalActivity getByAgencyPortalIdActivityId(Integer agencyPortalId, Integer activityId);

    ActivityResp publishUnion(ActivityReq.ActivityId req);

    ActivityResp cancelUnion(ActivityReq.ActivityId req);

    List<AgencyPortalActivity> getByActivity(Integer activityId);

    /**
     * 描述 根据机构code以及activityId浏览量
     *
     * @param agencyCode            机构代码
     * @param activityId            活动id
     * @param incrementViewCountNum 新增数量
     * @return
     */
    int updateViewCountByAgencyCodeAndActivityId(String agencyCode, Integer activityId, Integer incrementViewCountNum);
}