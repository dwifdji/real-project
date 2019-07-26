package com._360pai.crawler.service;

import com._360pai.crawler.model.LawtimeCourt;

/**
 * @author xdrodger
 * @Title: LawtimeService
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/16 11:00
 */
public interface LawtimeService {
    LawtimeCourt findByName(String name);

    void save(LawtimeCourt lawtimeCourt);

    LawtimeCourt findByItemUrl(String itemUrl);
}
