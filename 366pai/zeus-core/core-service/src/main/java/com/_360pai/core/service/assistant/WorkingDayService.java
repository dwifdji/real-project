package com._360pai.core.service.assistant;


import com._360pai.core.model.assistant.WorkingDay;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface WorkingDayService{


    PageInfo selectWorkingDayList(int page, int perPage);

    int addWorkingDay(WorkingDay params);

    int deleteWorkingDay(WorkingDay params);
}