package com._360pai.core.facade.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.req.PlatformAnnouncementReq;
import com.github.pagehelper.PageInfo;

public interface PlatformAnnouncementFacade {

    PageInfo selectPlatformAnnouncementList(PlatformAnnouncementReq req);

    int addPlatformAnnouncement(PlatformAnnouncementReq req);

    int editPlatformAnnouncement(PlatformAnnouncementReq req);

    int deletePlatformAnnouncement(PlatformAnnouncementReq req);

    Object detail(PlatformAnnouncementReq req);

    ResponseModel view(PlatformAnnouncementReq req);
}
