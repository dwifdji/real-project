package com._360pai.core.service.assistant;


import com._360pai.arch.common.ResponseModel;
import com._360pai.core.condition.assistant.PlatformAnnouncementCondition;
import com._360pai.core.facade.assistant.req.PlatformAnnouncementReq;
import com._360pai.core.model.assistant.PlatformAnnouncement;
import com.github.pagehelper.PageInfo;

public interface PlatformAnnouncementService{


    int addPlatformAnnouncement(PlatformAnnouncement params);

    PageInfo selectPlatformAnnouncementList(int page, int perPage, PlatformAnnouncementCondition condition, String typeFlag);

    int editPlatformAnnouncement(PlatformAnnouncement params);

    int deletePlatformAnnouncement(PlatformAnnouncement params);

    Object detail(PlatformAnnouncement params, PlatformAnnouncementCondition condition);

    PlatformAnnouncement getDetailById(PlatformAnnouncementCondition condition);


    ResponseModel view(PlatformAnnouncementReq req);
}