package com._360pai.core.service.enrolling.impl;

import com._360pai.core.condition.enrolling.EnrollingDepositCondition;
import com._360pai.core.dao.enrolling.EnrollingDepositDao;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.facade.enrolling.req.EnrollingReq.activityIdTypeReq;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingDeposit;
import com._360pai.core.service.enrolling.EnrollingDepositService;
import com._360pai.core.vo.enrolling.EnrollingAuditVo;
import com._360pai.core.vo.enrolling.EnrollingDepositListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollingDepositServiceImpl implements EnrollingDepositService{

	@Autowired
	private EnrollingDepositDao enrollingDepositDao;


	@Override
	public PageInfo getEnrollingDepositList(ActivityIdReqDto req) {

		PageHelper.startPage(req.getPage(), req.getPerPage());


		List<EnrollingDepositListVo> list = enrollingDepositDao.getEnrollingDepositList(req);

		return new PageInfo<>(list);
 	}

	@Override
	public PageInfo getAuditList(EnrollingListReqDto req) {

		PageHelper.startPage(req.getPage(), req.getPerPage());


		List<EnrollingAuditVo> list = enrollingDepositDao.getAuditList(req);

		return new PageInfo<>(list);
 	}

	@Override
	public Integer saveEnrollingDeposit(EnrollingDeposit enrollingDeposit) {
		return enrollingDepositDao.insert(enrollingDeposit);
	}

	@Override
	public EnrollingDeposit getFilterModel(activityIdTypeReq req) {
		EnrollingDepositCondition enrollingDepositCondition = new EnrollingDepositCondition();
		enrollingDepositCondition.setActivityId(Integer.parseInt(req.getActivityId()));
		enrollingDepositCondition.setPartyId(Integer.parseInt(req.getPartyId()));
		return enrollingDepositDao.selectOneResult(enrollingDepositCondition);
	}

	@Override
	public EnrollingDeposit getEnrollingDepositById(String id) {
		EnrollingDepositCondition enrollingDepositCondition = new EnrollingDepositCondition();
		enrollingDepositCondition.setId(Long.valueOf(id));
		return enrollingDepositDao.selectOneResult(enrollingDepositCondition);
	}

	@Override
	public int updateEnrollingDeposit(EnrollingDeposit deposit) {
		return enrollingDepositDao.updateById(deposit);
	}

	@Override
	public List<EnrollingDeposit> getEnrollingDepositList(EnrollingDepositCondition condition) {
		return enrollingDepositDao.selectList(condition);
	}

	@Override
	public List<EnrollingActivity> getBeginIn5MinuteList(Integer partyId) {
		return enrollingDepositDao.getBeginIn5MinuteList(partyId);
	}

	@Override
	public List<EnrollingActivity> getEndIn5MinuteList(Integer partyId) {
		return enrollingDepositDao.getEndIn5MinuteList(partyId);
	}


}