package com._360pai.crawler.service.impl;

import com._360pai.crawler.dao.LawtimeCourtDao;
import com._360pai.crawler.model.LawtimeCourt;
import com._360pai.crawler.service.LawtimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xdrodger
 * @Title: LawtimeServiceImpl
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/16 11:01
 */
@Slf4j
@Service
public class LawtimeServiceImpl implements LawtimeService {
    @Autowired
    private LawtimeCourtDao lawtimeCourtDao;

    @Override
    public LawtimeCourt findByName(String name) {
        return lawtimeCourtDao.findByName(name);
    }

    @Override
    public void save(LawtimeCourt lawtimeCourt) {
        lawtimeCourtDao.save(lawtimeCourt);
    }

    @Override
    public LawtimeCourt findByItemUrl(String itemUrl) {
        return lawtimeCourtDao.findByItemUrl(itemUrl);
    }
}
