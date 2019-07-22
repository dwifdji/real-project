package com._360pai.core.service.disposal;

import com._360pai.core.model.disposal.TDisposeShow;
import com.github.pagehelper.PageInfo;

/**
 * @author xiaolei
 * @create 2018-10-30 10:08
 */
public interface DisposeShowService {
    PageInfo getShowProvider(Integer activityId, int pageNum, int pageSize);
    PageInfo getFirstLevelShowProvider(Integer cityId, Integer activityId, int pageNum, int pageSize);
    TDisposeShow getShowByProviderIdAndActivityId(Integer providerId, Integer activityId);
    int addProviderShow(Integer providerId, Integer activityId);
}
