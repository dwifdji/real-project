package com._360pai.core.service.enrolling.impl;

import com._360pai.core.condition.enrolling.EnrollingActivityShareStatsCondition;
import com._360pai.core.dao.enrolling.EnrollingActivityShareStatsDao;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.model.enrolling.EnrollingActivityShareStats;
import com._360pai.core.service.enrolling.EnrollingActivityShareStatsService;
import com._360pai.core.vo.enrolling.EnrollingInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollingActivityShareStatsServiceImpl	implements EnrollingActivityShareStatsService{

	@Autowired
	private EnrollingActivityShareStatsDao enrollingActivityShareStatsDao;


	@Override
	public PageInfo getShareList(ActivityIdReqDto req) {
		PageHelper.startPage(req.getPage(), req.getPerPage());

		List<EnrollingInfoVo> list = enrollingActivityShareStatsDao.getShareList(req);
		return new PageInfo<>(list);
	}

	@Override
	public void saveEnrollingActivityShare(EnrollingActivityShareStats req) {

		//查询是否已经有分享的数据了
		EnrollingActivityShareStatsCondition condition = new EnrollingActivityShareStatsCondition();
		condition.setAccountId(req.getAccountId());
		condition.setActivityId(req.getActivityId());

		//未分享过 添加数据
		if(enrollingActivityShareStatsDao.selectFirst(condition)==null){

			enrollingActivityShareStatsDao.insert(req);

		}

	}


}