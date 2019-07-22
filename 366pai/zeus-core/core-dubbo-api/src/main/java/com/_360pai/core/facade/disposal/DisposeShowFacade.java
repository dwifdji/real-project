package com._360pai.core.facade.disposal;

import com._360pai.arch.common.PageInfoResp;

/**
 * @author xiaolei
 * @create 2018-10-30 13:18
 */
public interface DisposeShowFacade {
    /**
     * 获取推荐的服务商
     * @return
     */
    PageInfoResp getRecommendProvider(Integer activityId, int pageNum, int pageSize);
}
