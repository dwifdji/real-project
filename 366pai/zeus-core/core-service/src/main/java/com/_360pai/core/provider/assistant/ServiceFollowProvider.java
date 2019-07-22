package com._360pai.core.provider.assistant;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.assistant.ServiceFollowFacade;
import com._360pai.core.facade.assistant.req.ServiceFollowReq;
import com._360pai.core.service.assistant.TServiceFollowService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/17 10:18
 */
@Component
@Service(version = "1.0.0")
public class ServiceFollowProvider implements ServiceFollowFacade {
    @Autowired
    private TServiceFollowService tServiceFollowService;

    @Override
    public int serviceFollowAdd(ServiceFollowReq.Add req) {
        return tServiceFollowService.add(req);
    }

    @Override
    public int serviceFollowRemove(ServiceFollowReq.Remove req) {

        return tServiceFollowService.remove(req);
    }

    @Override
    public PageUtils.Page serviceFollowList(ServiceFollowReq.List req) {
        return tServiceFollowService.list(req);
    }

    @Override
    public int serviceFollowRemoveList(ServiceFollowReq.Remove req) {
        return tServiceFollowService.removeList(req);
    }
}
