package com._360pai.core.facade.assistant;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.assistant.req.ServiceFollowReq;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/17 10:17
 */
public interface ServiceFollowFacade {
    int serviceFollowAdd(ServiceFollowReq.Add req);

    int serviceFollowRemove(ServiceFollowReq.Remove req);

    PageUtils.Page serviceFollowList(ServiceFollowReq.List req);

    int serviceFollowRemoveList(ServiceFollowReq.Remove req);
}
