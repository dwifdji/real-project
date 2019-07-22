
package com._360pai.core.dao.activity.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.activity.ActivityRejectRecordCondition;
import com._360pai.core.dao.activity.mapper.ActivityRejectRecordMapper;
import com._360pai.core.model.activity.ActivityRejectRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.activity.ActivityRejectRecordDao;

import java.util.Date;

@Service
public class ActivityRejectRecordDaoImpl extends AbstractDaoImpl<ActivityRejectRecord, ActivityRejectRecordCondition, BaseMapper<ActivityRejectRecord,ActivityRejectRecordCondition>> implements ActivityRejectRecordDao{
	
	@Resource
	private ActivityRejectRecordMapper activityRejectRecordMapper;
	
	@Override
	protected BaseMapper<ActivityRejectRecord, ActivityRejectRecordCondition> daoSupport() {
		return activityRejectRecordMapper;
	}

	@Override
	protected ActivityRejectRecordCondition blankCondition() {
		return new ActivityRejectRecordCondition();
	}

	@Override
	public int save(Integer activityId, String reason) {
		ActivityRejectRecord model = new ActivityRejectRecord();
		model.setActivityId(activityId);
		model.setReason(reason);
		model.setCreatedAt(new Date());
		return activityRejectRecordMapper.insert(model);
	}
}
