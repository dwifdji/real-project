package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.assistant.WorkingDayCondition;
import com._360pai.core.dao.assistant.WorkingDayDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.assistant.WorkingDay;
import com._360pai.core.service.assistant.WorkingDayService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkingDayServiceImpl implements WorkingDayService {

    @Autowired
    private WorkingDayDao workingDayDao;


    @Override
    public PageInfo selectWorkingDayList(int page, int perPage) {
        PageHelper.startPage(page, perPage);
        List<WorkingDay> workingDays = workingDayDao.selectAll();
        return new PageInfo<>(workingDays);
    }

    @Override
    public int addWorkingDay(WorkingDay params) {
        return workingDayDao.insert(params);
    }

    @Override
    public int deleteWorkingDay(WorkingDay params) {
        WorkingDay workingDay = findWorkingDay(params);
        if (workingDay == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "删除的日期不存在");
        }
        return workingDayDao.deleteWorkingDay(params.getId());
    }

    private WorkingDay findWorkingDay(WorkingDay params) {
        WorkingDayCondition condition = new WorkingDayCondition();
        condition.setId(params.getId());
        return workingDayDao.selectOneResult(condition);
    }
}