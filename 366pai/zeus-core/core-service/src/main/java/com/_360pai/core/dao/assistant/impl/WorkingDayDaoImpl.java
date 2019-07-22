
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.WorkingDayCondition;
import com._360pai.core.dao.assistant.mapper.WorkingDayMapper;
import com._360pai.core.model.assistant.WorkingDay;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.WorkingDayDao;

@Service
public class WorkingDayDaoImpl extends AbstractDaoImpl<WorkingDay, WorkingDayCondition, BaseMapper<WorkingDay,WorkingDayCondition>> implements WorkingDayDao{
	
	@Resource
	private WorkingDayMapper workingDayMapper;
	
	@Override
	protected BaseMapper<WorkingDay, WorkingDayCondition> daoSupport() {
		return workingDayMapper;
	}

	@Override
	protected WorkingDayCondition blankCondition() {
		return new WorkingDayCondition();
	}

    @Override
    public int deleteWorkingDay(Integer paramsId) {
        return workingDayMapper.deleteWorkingDay(paramsId);
    }
}
