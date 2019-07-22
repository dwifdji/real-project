package com._360pai.core.service.activity.impl;

import com._360pai.core.dao.activity.PlatformBroadcastDao;
import com._360pai.core.model.activity.PlatformBroadcast;
import com._360pai.core.service.activity.PlatformBroadcastService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformBroadcastServiceImpl	implements PlatformBroadcastService {

	@Autowired
	private PlatformBroadcastDao platformBroadcastDao;


    @Override
    public PageInfo getBroadcastList(int page, int perPage) {
        PageHelper.startPage(page,perPage);
        List<PlatformBroadcast> platformBroadcasts = platformBroadcastDao.getBroadcastList();
        return new PageInfo<>(platformBroadcasts);
    }
}