package com._360pai.web.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.activity.ActivityBroadcastFacade;
import com._360pai.core.facade.activity.req.PlatformBroadcastReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: ActivityBroadcastController  首页竞拍公告
 * @ProjectName zeus-rest-web
 * @Description:
 * @date 2018/8/27 10:50
 */
@RestController
public class ActivityBroadcastController {

    @Reference(version = "1.0.0")
    private ActivityBroadcastFacade activityBroadcastFacade;

    @GetMapping(value = "/open/activity_broadcasts")
    public ResponseModel getBroadcastList(PlatformBroadcastReq req) {
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        PageInfo pageInfo = activityBroadcastFacade.getBroadcastList(req);
        model.setContent(pageInfo);
        return model;
    }
}
