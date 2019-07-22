
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.vo.enrolling.EnrollingAuditVo;
import com._360pai.core.vo.enrolling.EnrollingDepositListVo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.EnrollingDepositCondition;
import com._360pai.core.dao.enrolling.mapper.EnrollingDepositMapper;
import com._360pai.core.model.enrolling.EnrollingDeposit;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.enrolling.EnrollingDepositDao;

import java.util.List;

@Service
public class EnrollingDepositDaoImpl extends AbstractDaoImpl<EnrollingDeposit, EnrollingDepositCondition, BaseMapper<EnrollingDeposit,EnrollingDepositCondition>> implements EnrollingDepositDao{
	
	@Resource
	private EnrollingDepositMapper enrollingDepositMapper;
	
	@Override
	protected BaseMapper<EnrollingDeposit, EnrollingDepositCondition> daoSupport() {
		return enrollingDepositMapper;
	}

	@Override
	protected EnrollingDepositCondition blankCondition() {
		return new EnrollingDepositCondition();
	}

	@Override
	public List<EnrollingDepositListVo> getEnrollingDepositList(ActivityIdReqDto dto) {
		return enrollingDepositMapper.getEnrollingDepositList(dto);
	}

	@Override
	public List<EnrollingAuditVo> getAuditList(EnrollingListReqDto req) {
		return enrollingDepositMapper.getAuditList(req);
	}

	@Override
	public List<EnrollingActivity> getBeginIn5MinuteList(Integer partyId) {
		return enrollingDepositMapper.getBeginIn5MinuteList(partyId);
	}

	@Override
	public List<EnrollingActivity> getEndIn5MinuteList(Integer partyId) {
		return enrollingDepositMapper.getEndIn5MinuteList(partyId);
	}
}
