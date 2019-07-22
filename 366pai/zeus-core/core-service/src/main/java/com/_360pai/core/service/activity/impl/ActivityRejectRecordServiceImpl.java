package com._360pai.core.service.activity.impl;

import com._360pai.core.dao.activity.ActivityRejectRecordDao;
import com._360pai.core.model.activity.ActivityRejectRecord;
import com._360pai.core.service.activity.ActivityRejectRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ActivityRejectRecordServiceImpl	implements ActivityRejectRecordService{

	@Autowired
	private ActivityRejectRecordDao activityRejectRecordDao;


	@Override
	public boolean saveActivityRejectRecord(Integer activityId, String reason) {
		ActivityRejectRecord model = new ActivityRejectRecord();
		model.setActivityId(activityId);
		model.setReason(reason);
		model.setCreatedAt(new Date());
		return activityRejectRecordDao.insert(model) > 0 ? true : false;
	}
}