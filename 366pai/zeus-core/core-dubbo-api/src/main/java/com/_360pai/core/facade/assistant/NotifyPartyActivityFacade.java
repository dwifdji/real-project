package com._360pai.core.facade.assistant;

import com._360pai.core.facade.assistant.req.NotifyPartyActivityReq;
import com.github.pagehelper.PageInfo;

public interface NotifyPartyActivityFacade {
    void notifyMe(NotifyPartyActivityReq.notifyReq req);

    int cancelNotifyMe(NotifyPartyActivityReq.notifyReq req);

    PageInfo myNotify(NotifyPartyActivityReq.notifyReq req);

    int cancel(NotifyPartyActivityReq.notifyReq req, Integer partyPrimaryId);
}
