package com._360pai.core.service.assistant;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.assistant.req.AppletHallActivityReq;
import com._360pai.core.facade.assistant.vo.BackAppletHallActivity;
import com._360pai.core.model.assistant.THall;
import com.github.pagehelper.PageInfo;

public interface THallService {

    PageInfo hallList(int page, int perPage, THall copy);

    int addHall(THall params);

    int editHall(THall params);

    int deleteHall(THall params);

    Object detailHall(Integer id);

    PageInfoResp<BackAppletHallActivity> getAppletHallActivityList(AppletHallActivityReq.QueryReq req);

    Integer addAppletHallActivity(AppletHallActivityReq.AddReq req);

    Integer editAppletHallActivity(AppletHallActivityReq.EditReq req);

    Integer deleteAppletHallActivity(AppletHallActivityReq.DeleteReq req);
}
