
package com._360pai.core.dao.enrolling.impl;

import java.util.List;

import javax.annotation.Resource;

import com._360pai.core.model.enrolling.EnrollingActivity;
import org.springframework.stereotype.Service;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.enrolling.NotifyPartyEnrollingActivityCondition;
import com._360pai.core.dao.enrolling.NotifyPartyEnrollingActivityDao;
import com._360pai.core.dao.enrolling.mapper.NotifyPartyEnrollingActivityMapper;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.model.enrolling.NotifyPartyEnrollingActivity;
import com._360pai.core.vo.enrolling.EnrollingInfoVo;

@Service
public class NotifyPartyEnrollingActivityDaoImpl extends AbstractDaoImpl<NotifyPartyEnrollingActivity, NotifyPartyEnrollingActivityCondition, BaseMapper<NotifyPartyEnrollingActivity,NotifyPartyEnrollingActivityCondition>> implements NotifyPartyEnrollingActivityDao{
	
	@Resource
	private NotifyPartyEnrollingActivityMapper notifyPartyEnrollingActivityMapper;
	
	@Override
	protected BaseMapper<NotifyPartyEnrollingActivity, NotifyPartyEnrollingActivityCondition> daoSupport() {
		return notifyPartyEnrollingActivityMapper;
	}

	@Override
	protected NotifyPartyEnrollingActivityCondition blankCondition() {
		return new NotifyPartyEnrollingActivityCondition();
	}

	@Override
	public List<EnrollingInfoVo> getNotifyList(ActivityIdReqDto dto) {
		return notifyPartyEnrollingActivityMapper.getNotifyList(dto);
	}

	@Override
	public Integer deleteModel(String id) {
		return notifyPartyEnrollingActivityMapper.deleteModel(id);
	}

	@Override
	public List<EnrollingActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId) {
		return notifyPartyEnrollingActivityMapper.getBeginIn5MinuteList(accountId, partyId);
	}

	@Override
	public List<EnrollingActivity> getEndIn5MinuteList(Integer accountId, Integer partyId) {
		return notifyPartyEnrollingActivityMapper.getEndIn5MinuteList(accountId, partyId);
	}
}
