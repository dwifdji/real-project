package com._360pai.core.facade.assistant;

import com._360pai.core.facade.assistant.req.BannerReq;
import com.github.pagehelper.PageInfo;

public interface BannerFacade {

    PageInfo selectBanner(BannerReq req);

    int addBanner(BannerReq req);

    int editBanner(BannerReq req);

    int deleteBanner(BannerReq req);
}
