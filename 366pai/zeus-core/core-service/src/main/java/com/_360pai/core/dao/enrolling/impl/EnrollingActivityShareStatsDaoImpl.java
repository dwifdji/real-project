
package com._360pai.core.dao.enrolling.impl;

import javax.annotation.Resource;

import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.vo.enrolling.EnrollingInfoVo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.enrolling.EnrollingActivityShareStatsCondition;
import com._360pai.core.dao.enrolling.mapper.EnrollingActivityShareStatsMapper;
import com._360pai.core.model.enrolling.EnrollingActivityShareStats;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.enrolling.EnrollingActivityShareStatsDao;

import java.util.List;

@Service
public class EnrollingActivityShareStatsDaoImpl extends AbstractDaoImpl<EnrollingActivityShareStats, EnrollingActivityShareStatsCondition, BaseMapper<EnrollingActivityShareStats,EnrollingActivityShareStatsCondition>> implements EnrollingActivityShareStatsDao{
	
	@Resource
	private EnrollingActivityShareStatsMapper enrollingActivityShareStatsMapper;
	
	@Override
	protected BaseMapper<EnrollingActivityShareStats, EnrollingActivityShareStatsCondition> daoSupport() {
		return enrollingActivityShareStatsMapper;
	}

	@Override
	protected EnrollingActivityShareStatsCondition blankCondition() {
		return new EnrollingActivityShareStatsCondition();
	}

	@Override
	public List<EnrollingInfoVo> getShareList(ActivityIdReqDto dto) {
		return enrollingActivityShareStatsMapper.getShareList(dto);
	}
}
