package com._360pai.core.provider.activity;

import com._360pai.core.facade.activity.ActivityBroadcastFacade;
import com._360pai.core.facade.activity.req.PlatformBroadcastReq;
import com._360pai.core.service.activity.PlatformBroadcastService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: ActivityBroadcastProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/27 10:52
 */
@Component
@Service(version = "1.0.0")
public class ActivityBroadcastProvider implements ActivityBroadcastFacade {

    @Autowired
    private PlatformBroadcastService platformBroadcastService;

    @Override
    public PageInfo getBroadcastList(PlatformBroadcastReq req) {
        return platformBroadcastService.getBroadcastList(req.getPage(), req.getPerPage());
    }
}
