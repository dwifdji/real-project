package com._360pai.core.service.enrolling.impl;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.condition.enrolling.EnrollingActivityProgressCondition;
import com._360pai.core.dao.enrolling.EnrollingActivityProgressDao;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.model.enrolling.EnrollingActivityProgress;
import com._360pai.core.service.enrolling.EnrollingActivityProgressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollingActivityProgressServiceImpl	implements EnrollingActivityProgressService{

	@Autowired
	private EnrollingActivityProgressDao enrollingActivityProgressDao;


	@Override
	public PageInfo getProgressList(ActivityIdReqDto req) {

		PageHelper.startPage(req.getPage(), req.getPerPage());
		EnrollingActivityProgressCondition condition = new EnrollingActivityProgressCondition();

		condition.setActivityId(Integer.valueOf(req.getActivityId()));

		List<EnrollingActivityProgress> list = enrollingActivityProgressDao.selectList(condition);

		return new PageInfo<>(list);
	}

	@Override
	public ResponseModel saveProgress(EnrollingActivityProgress req) {

		return ResponseModel.succ(enrollingActivityProgressDao.insert(req));
	}
}