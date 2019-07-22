package com._360pai.core.facade.assistant;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.assistant.req.AppletHallActivityReq;
import com._360pai.core.facade.assistant.req.THallReq;
import com._360pai.core.facade.assistant.vo.BackAppletHallActivity;
import com.github.pagehelper.PageInfo;

public interface THallFacade {
    PageInfo hallList(THallReq req);

    int addHall(THallReq req);

    int editHall(THallReq req);

    int deleteHall(THallReq req);

    Object detailHall(THallReq req);

    PageInfoResp<BackAppletHallActivity> getAppletHallActivityList(AppletHallActivityReq.QueryReq req);

    Integer addAppletHallActivity(AppletHallActivityReq.AddReq req);

    Integer editAppletHallActivity(AppletHallActivityReq.EditReq req);

    Integer deleteAppletHallActivity(AppletHallActivityReq.DeleteReq req);
}
