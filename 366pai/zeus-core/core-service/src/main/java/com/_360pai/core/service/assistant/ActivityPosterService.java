package com._360pai.core.service.assistant;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.account.vo.ActivityPoster;
import com._360pai.core.facade.activity.req.ActivityReq;
import com.alibaba.fastjson.JSONObject;

/**
 * @author xdrodger
 * @Title: ActivityPosterService
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/7/1 13:30
 */
public interface ActivityPosterService {
    PageInfoResp<ActivityPoster> getBackendActivityPosterList(ActivityReq.QueryReq req);

    ActivityPoster getActivityPoster(ActivityReq.QueryReq req);

    Integer addActivityPoster(ActivityReq.ActivityPosterAddReq req);

    Integer updateActivityPoster(ActivityReq.ActivityPosterUpdateReq req);

    Integer deleteActivityPoster(ActivityReq.QueryReq req);

    Object getActivityPosterAssetCategoryList();

    ListResp<JSONObject> getActivityPosterList(ActivityReq.QueryReq req);
}
