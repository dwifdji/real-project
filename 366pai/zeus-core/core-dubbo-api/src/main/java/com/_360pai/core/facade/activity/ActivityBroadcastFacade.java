package com._360pai.core.facade.activity;

import com._360pai.core.facade.activity.req.PlatformBroadcastReq;
import com.github.pagehelper.PageInfo;

public interface ActivityBroadcastFacade {
    PageInfo getBroadcastList(PlatformBroadcastReq req);
}
