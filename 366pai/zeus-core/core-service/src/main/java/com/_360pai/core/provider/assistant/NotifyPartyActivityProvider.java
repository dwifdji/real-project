package com._360pai.core.provider.assistant;

import com._360pai.core.facade.assistant.NotifyPartyActivityFacade;
import com._360pai.core.facade.assistant.req.NotifyPartyActivityReq;
import com._360pai.core.service.assistant.NotifyPartyActivityService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: NotifyPartyActivityProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/28 11:06
 */
@Component
@Service(version = "1.0.0")
public class NotifyPartyActivityProvider implements NotifyPartyActivityFacade {
    @Autowired
    private NotifyPartyActivityService notifyPartyActivityService;

    @Override
    public void notifyMe(NotifyPartyActivityReq.notifyReq req) {
        notifyPartyActivityService.notifyMe(req.getActivityId(), req.getAgencyCode(), req.getPartyId(), req.getAccountId(),req.getPathType()==null?(short) 0:req.getPathType());
    }

    @Override
    public int cancelNotifyMe(NotifyPartyActivityReq.notifyReq req) {
        return notifyPartyActivityService.cancelNotifyMe(req.getIds());
    }

    @Override
    public PageInfo myNotify(NotifyPartyActivityReq.notifyReq req) {
        return notifyPartyActivityService.myNotify(req.getPage(), req.getPerPage(), req.getPartyId(), req.getAccountId(),req.getCategoryId(),req.getName());
    }

    @Override
    public int cancel(NotifyPartyActivityReq.notifyReq req, Integer partyPrimaryId) {
        return notifyPartyActivityService.cancel(req.getActivityId(), partyPrimaryId, req.getAccountId());
    }
}
