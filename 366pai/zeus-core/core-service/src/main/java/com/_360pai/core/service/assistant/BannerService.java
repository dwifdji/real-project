package com._360pai.core.service.assistant;

import com._360pai.core.condition.assistant.BannerCondition;
import com._360pai.core.model.assistant.Banner;
import com.github.pagehelper.PageInfo;

public interface BannerService {


    PageInfo selectBanner(int page, int perPage, BannerCondition condition, String order_num);

    int addBanner(Banner params);

    int editBanner(Banner params);

    int deleteBanner(Banner params);
}