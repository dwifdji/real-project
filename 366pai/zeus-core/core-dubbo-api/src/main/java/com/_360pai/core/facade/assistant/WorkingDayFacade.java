package com._360pai.core.facade.assistant;

import com._360pai.core.facade.assistant.req.WorkingDayReq;
import com.github.pagehelper.PageInfo;

public interface WorkingDayFacade {
    PageInfo selectWorkingDayList(WorkingDayReq req);

    int addWorkingDay(WorkingDayReq req);

    int deleteWorkingDay(WorkingDayReq req);
}
