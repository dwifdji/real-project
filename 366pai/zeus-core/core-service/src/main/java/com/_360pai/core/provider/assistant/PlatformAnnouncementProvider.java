package com._360pai.core.provider.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.condition.assistant.PlatformAnnouncementCondition;
import com._360pai.core.facade.assistant.PlatformAnnouncementFacade;
import com._360pai.core.facade.assistant.req.PlatformAnnouncementReq;
import com._360pai.core.model.assistant.PlatformAnnouncement;
import com._360pai.core.service.assistant.PlatformAnnouncementService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: PlatformAnnouncementFacadeImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/21 16:20
 */
@Component
@Service(version = "1.0.0")
public class PlatformAnnouncementProvider implements PlatformAnnouncementFacade {

    @Autowired
    private PlatformAnnouncementService platformAnnouncementService;


    @Override
    public PageInfo selectPlatformAnnouncementList(PlatformAnnouncementReq req) {
        PlatformAnnouncementCondition condition = new PlatformAnnouncementCondition();
        condition.setCategory(req.getCategory());
        return platformAnnouncementService.selectPlatformAnnouncementList(req.getPage(), req.getPerPage(), condition, req.getTypeFlag());
    }

    @Override
    public int addPlatformAnnouncement(PlatformAnnouncementReq req) {
        return platformAnnouncementService.addPlatformAnnouncement(copyPlatformAnnouncement(req));
    }

    @Override
    public int editPlatformAnnouncement(PlatformAnnouncementReq req) {
        return platformAnnouncementService.editPlatformAnnouncement(copyPlatformAnnouncement(req));
    }

    @Override
    public int deletePlatformAnnouncement(PlatformAnnouncementReq req) {
        return platformAnnouncementService.deletePlatformAnnouncement(copyPlatformAnnouncement(req));
    }

    @Override
    public Object detail(PlatformAnnouncementReq req) {
        PlatformAnnouncementCondition condition = new PlatformAnnouncementCondition();
        condition.setCategory(req.getCategory());
        return platformAnnouncementService.detail(copyPlatformAnnouncement(req), condition);
    }

    @Override
    public ResponseModel view(PlatformAnnouncementReq req) {
        return platformAnnouncementService.view(req);
    }

    private PlatformAnnouncement copyPlatformAnnouncement(PlatformAnnouncementReq req) {
        PlatformAnnouncement platformAnnouncement = new PlatformAnnouncement();
        BeanUtils.copyProperties(req, platformAnnouncement);
        return platformAnnouncement;
    }
}
