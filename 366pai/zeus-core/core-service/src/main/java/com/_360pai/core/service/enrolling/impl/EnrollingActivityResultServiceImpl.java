package com._360pai.core.service.enrolling.impl;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.condition.enrolling.EnrollingActivityResultCondition;
import com._360pai.core.dao.enrolling.EnrollingActivityResultDao;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.model.enrolling.EnrollingActivityResult;
import com._360pai.core.service.enrolling.EnrollingActivityResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollingActivityResultServiceImpl	implements EnrollingActivityResultService{

	@Autowired
	private EnrollingActivityResultDao enrollingActivityResultDao;


	@Override
	public EnrollingActivityResult getResult(ActivityIdReqDto req) {

		EnrollingActivityResultCondition condition = new EnrollingActivityResultCondition();

		condition.setActivityId(Integer.valueOf(req.getActivityId()));

		EnrollingActivityResult result =enrollingActivityResultDao.selectFirst(condition);
		return result;
	}

	@Override
	public ResponseModel saveResult(EnrollingActivityResult req) {
		return ResponseModel.succ(enrollingActivityResultDao.insert(req));
	}

	@Override
	public void updateResult(EnrollingActivityResult req) {
		enrollingActivityResultDao.updateById(req);
	}


}