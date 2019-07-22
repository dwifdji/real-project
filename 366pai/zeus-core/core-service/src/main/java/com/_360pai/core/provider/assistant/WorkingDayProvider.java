package com._360pai.core.provider.assistant;

import com._360pai.core.facade.assistant.WorkingDayFacade;
import com._360pai.core.facade.assistant.req.WorkingDayReq;
import com._360pai.core.model.assistant.WorkingDay;
import com._360pai.core.service.assistant.WorkingDayService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: WorkingDayProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/22 19:23
 */
@Component
@Service(version = "1.0.0")
public class WorkingDayProvider implements WorkingDayFacade {

    @Autowired
    private WorkingDayService workingDayService;

    @Override
    public PageInfo selectWorkingDayList(WorkingDayReq req) {
        return workingDayService.selectWorkingDayList(req.getPage(), req.getPerPage());
    }

    @Override
    public int addWorkingDay(WorkingDayReq req) {
        return workingDayService.addWorkingDay(copyWorkingDay(req));
    }

    @Override
    public int deleteWorkingDay(WorkingDayReq req) {
        return workingDayService.deleteWorkingDay(copyWorkingDay(req));
    }

    private WorkingDay copyWorkingDay(WorkingDayReq req) {
        WorkingDay workingDay = new WorkingDay();
        BeanUtils.copyProperties(req, workingDay);
        return workingDay;
    }
}
